package com.dlopez.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    /*private val quizViewModel1: QuizViewModel by lazy {
         ViewModelProviders.of(this).get(QuizViewModel::class.java)
     }*/

    private val quizViewModel by viewModels<QuizViewModel>()

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var prevImgButton: ImageButton
    private lateinit var nextImgButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var cheatCount: TextView

    private var score = 0.0
    private var lockedQuestions = mutableListOf<Int>()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d(TAG, "onCreate(Bundle? called")
        setContentView(R.layout.activity_main)

        Log.i(TAG, "onRestoreInstanceState")
        val thisCurrentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = thisCurrentIndex
        Log.i(TAG, "index ${quizViewModel.currentIndex} restored")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)
        prevImgButton = findViewById(R.id.prev)
        nextImgButton = findViewById(R.id.next)
        cheatButton = findViewById(R.id.cheat_button)
        cheatCount = findViewById(R.id.cheat_textview)

        //applies for SDK <= 28
        trueButton.setOnClickListener {
            lockedQuestions.add(quizViewModel.currentIndex)
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            lockedQuestions.add(quizViewModel.currentIndex)
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        nextImgButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        previousButton.setOnClickListener {
            /*if (currentIndex == 0)
                currentIndex = 6*/
            quizViewModel.moveToPrevious()
            updateQuestion()
        }

        prevImgButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            // Start CheatActivity
            // val intent = Intent(this, CheatActivity::class.java)
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            //startActivityForResult(intent, REQUEST_CODE_CHEAT)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val options = ActivityOptions
                    .makeClipRevealAnimation(it, 0, 0, it.width, it.height)
                startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
            } else {
                startActivityForResult(intent, REQUEST_CODE_CHEAT)
            }
            //startActivity(intent)
        }

        updateQuestion()
        setCheatingCounter()
    }

    private fun setCheatingCounter() {
        cheatCount.text = quizViewModel.cheatCount.toString()
        if (quizViewModel.cheatCount == 3)
            cheatButton.isEnabled = false
    }

    private fun updateQuestion() {
        //Log.d(TAG, "Updating question text", Exception())
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        manageAnswerButtons()
        Log.d(TAG, "index: ${quizViewModel.currentIndex}")

        /*val questionTextResId = quizViewModel.currentQuestionText
        quizViewModel.index.observe(this, {
            questionTextView.setText(questionTextResId)
        })
        manageAnswerButtons()
        Log.d(TAG, "index: ${quizViewModel.currentIndex}")*/
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId: Int
        when {
            quizViewModel.questionBank[quizViewModel.currentIndex].isCheated && userAnswer == correctAnswer -> {
                messageResId = R.string.judgment_toast
            }
            userAnswer == correctAnswer -> {
                messageResId = R.string.correct_toast
                score++
            }
            else -> {
                messageResId = R.string.incorrect_toast
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        answered()
        manageAnswerButtons()
    }

    private fun answered() {
        quizViewModel.questionBank[quizViewModel.currentIndex].isAnswered = true

        if (quizViewModel.questionBank.size == lockedQuestions.size) {
            val handler = Handler()
            handler.postDelayed({
                Toast.makeText(
                    this,
                    "You scored ${(score / quizViewModel.questionBank.size * 100).toInt()} over 100 points",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "${(score / quizViewModel.questionBank.size * 100).toInt()}")
            }, 2500)
        }
    }

    private fun manageAnswerButtons() {
        if (quizViewModel.questionBank[quizViewModel.currentIndex].isAnswered) {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        } else {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.questionBank[quizViewModel.currentIndex].isCheated =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            if (quizViewModel.questionBank[quizViewModel.currentIndex].isCheated) {
                quizViewModel.cheatCount++
                setCheatingCounter()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        Log.i(TAG, "index ${quizViewModel.currentIndex} stored")
    }

    /*override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
        val thisCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
        quizViewModel.currentIndex = thisCurrentIndex
        Log.i(TAG, "index ${quizViewModel.currentIndex} restored")
       // quizViewModel.currentIndex(currentIndex)
    }*/

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}
