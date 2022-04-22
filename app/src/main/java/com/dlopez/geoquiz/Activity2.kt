/*
package com.dlopez.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"

class Activity2 : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var prevImgButton: ImageButton
    private lateinit var nextImgButton: ImageButton
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle? called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)
        prevImgButton = findViewById(R.id.prev)
        nextImgButton = findViewById(R.id.next)

        //applies for SDK <= 28
        trueButton.setOnClickListener {
            */
/*Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).apply {
                setGravity(TOP, 0, 0)
            }.show()*//*

            checkAnswer(true)
            isAnswered()
        }

        falseButton.setOnClickListener { view: View ->
            */
/*Snackbar.make(view, R.string.incorrect_toast, Snackbar.LENGTH_LONG)
                .setAction("Show Toast") {
                    val toast =
                        Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT)
                    toast.setGravity(TOP, 0, 0)
                    toast.show()
                }
                .show()*//*

            checkAnswer(false)
            isAnswered()
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            refreshButtons()
            updateQuestion()
        }

        nextImgButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            refreshButtons()
            updateQuestion()
        }

        previousButton.setOnClickListener {
            if (currentIndex == 0)
                currentIndex = 6
            currentIndex = (currentIndex - 1) % questionBank.size
            refreshButtons()
            updateQuestion()
        }

        prevImgButton.setOnClickListener {
            if (currentIndex == 0)
                currentIndex = 6
            currentIndex = (currentIndex - 1) % questionBank.size
            refreshButtons()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            refreshButtons()
            updateQuestion()
        }

        updateQuestion()
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

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        //questionTextView.text = questionTextResId.toString()

        // Log a message at DEBUG log level
        Log.d(TAG, "Current question index: $currentIndex")
        try {
            val question = questionBank[currentIndex]
        } catch (ex: ArrayIndexOutOfBoundsException) {
            // Log a message at ERROR log level, along with an exception stack trace
            Log.e(TAG, "Index was out of bounds", ex)
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun isAnswered() {
        falseButton.isEnabled = false
        trueButton.isEnabled = false
    }

    private fun refreshButtons() {
        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }

    */
/*private fun isAnswered(index: Int){
        val isQuestionAnswered = questionBank[index].answered
        trueButton.isEnabled = !isQuestionAnswered
        falseButton.isEnabled = !isQuestionAnswered
    }*//*

}*/
