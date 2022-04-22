package com.dlopez.geoquiz

import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    var currentIndex = 0
    var cheatCount = 0
    val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex = if (currentIndex == 0) {
            5
        } else {
            (currentIndex - 1) % questionBank.size
        }
    }

    //check whether the data is fresh before doing the work
    fun currentIndex(index: Int) {
        if (index != currentIndex) {
            currentIndex = index
        }
    }
}

/*state: SaveInstanceState
    var currentIndex = state.getLiveData("count", 0)
    val index: LiveData<Int> get() = currentIndex*/

/*private val _index = MutableLiveData(0)
val index: LiveData<Int> get() = _index*/

/*private val _questionAnswer = MutableLiveData(false)
    val questionAnswer: LiveData<Boolean> get() = _questionAnswer*/

//_index.value = (_index.value!! + 1) % questionBank.size

/*
fun moveToPrevious() {
    _index.value = if(_index.value!! == 0){
        5
    } else{
        (_index.value!! - 1) % questionBank.size
    }
}*/
