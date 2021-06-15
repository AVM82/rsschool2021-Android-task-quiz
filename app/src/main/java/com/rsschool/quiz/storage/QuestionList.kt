package com.rsschool.quiz.storage

import com.rsschool.quiz.model.Question

fun makeQuestionList() =
    listOf(
        Question(
            "Вопрос1", mapOf(
                "ответ1" to false,
                "ответ2" to false,
                "ответ3" to false,
                "ответ4" to true,
                "ответ5" to false,
            )
        ),
        Question(
            "Вопрос2", mapOf(
                "ответ1" to false,
                "ответ3" to false,
                "ответ4" to true,
                "ответ5" to false,
            )
        ),
        Question(
            "Вопрос3", mapOf(
                "ответ1" to false,
                "ответ2" to false,
                "ответ3" to false,
                "ответ4" to true,
                "ответ5" to false,
            )
        ), Question(
            "Вопрос4", mapOf(
                "ответ1" to false,
                "ответ3" to false,
                "ответ4" to true,
                "ответ5" to false,
            )
        ), Question(
            "Вопрос5", mapOf(
                "ответ4" to true,
                "ответ5" to false,
            )
        )
    )
