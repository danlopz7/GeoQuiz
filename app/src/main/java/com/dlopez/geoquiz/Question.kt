package com.dlopez.geoquiz

import androidx.annotation.StringRes

//two members variable and constructor
//question text and question answer
//class meant to hold data
data class Question(@StringRes val textResId: Int, val answer: Boolean, var isAnswered: Boolean = false, var isCheated: Boolean = false)

//@StringRes helps the code inspector built into AS verify at compile time that usages of the
// constructor provide a valid string resource ID.

//Challenge: Preventing Repeat Answers
//Once a user provides an answer for a particular question, disable the buttons for that question to
//prevent multiple answers being entered.

//queremos ahora solamente una respuesta por pregunta, para eso quiero desabilitar los botones para
//cada pregunta respondida