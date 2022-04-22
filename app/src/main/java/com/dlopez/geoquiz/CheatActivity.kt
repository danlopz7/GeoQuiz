package com.dlopez.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

//key for the extra
private const val EXTRA_ANSWER_IS_TRUE = "com.dlopez.geoquiz.answer_is_true"

//constant for the extra’s key || PARA RETORNAR A MAINACTIVITY
const val EXTRA_ANSWER_SHOWN = "com.dlopez.geoquiz.answer_shown"
private const val TAG = "CheatActivity"

//saved instance state
private const val KEY_WAS_CHEATED = "was_cheated"

class CheatActivity : AppCompatActivity() {

    private var answerIsTrue = false
    private var isCheating = false
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var apiLevelTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        isCheating = savedInstanceState?.getBoolean(KEY_WAS_CHEATED, false) ?: false

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        apiLevelTextView = findViewById(R.id.api_level_text_view)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        val buildNumber = Build.VERSION.SDK_INT.toString()
        apiLevelTextView.text = getString(R.string.api_level, buildNumber)

        showAnswerButton.setOnClickListener {
            isCheating = true
            fillTextIfCheated()

        }
        fillTextIfCheated()
    }

    private fun fillTextIfCheated() {

        if (isCheating) {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }
    }

    /**
     * create an Intent, put an extra on it, and then call Activity.setResult(Int, Intent) to
     * get that data into MainActivity’s hands.
     */
    //para retornar
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_WAS_CHEATED, isCheating)
    }

    /**
     * This function allows you to create an Intent properly configured with the extras CheatActivity will
     * need. The answerIsTrue argument, a Boolean, is put into the intent with a private name using the
     * EXTRA_ANSWER_IS_TRUE constant.
     */
    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}