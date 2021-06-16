package com.rsschool.quiz.model

data class Question(val id: Int, val text: String, val options: Map<String, Boolean>)