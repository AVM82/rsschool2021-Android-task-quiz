package com.rsschool.quiz.storage

import com.rsschool.quiz.model.Question

fun makeQuestionList() =
    listOf(
        Question(
            "What does the !! operator do?", mapOf(
                "It converts any value to non-null type and throw an exception if the value is in fact null" to true,
                "It returns the left-hand operand if the operand is not null; otherwise it return the right hand operand" to false,
                "It`s the modulo operator in Kotlin, similar to Java`s%" to false,
                "It compares two values for identity rather than equals" to false,
                "Create a pending intent to call 911" to false
            )
        ),
        Question(
            "What is to in the example below: val test = 33 to 42", mapOf(
                "A syntax error" to false,
                "An infix extension function creating a Pair(33, 42)" to true,
                "A Kotlin keyword to create a Pair(33, 42)" to false,
                "A Kotlin keyword to create a Range from 33 to 42" to false,
                "A Kotlin keyword to create a list of int  values from 33 to 42" to false
            )
        ),
        Question(
            "What is the correct way to declare a variable of integer type in Kotlin", mapOf(
                "int i = 42" to false,
                "var i : int = 42" to false,
                "let i = 42" to false,
                "var i : Int = 42" to true,
                "var int i = 42" to false
            )
        ), Question(
            "Under which license is Kotlin available?", mapOf(
                "MIT" to false,
                "Apache 2" to true,
                "GPL" to false,
                "UFO" to false,
                "Kotlin is closed source, commercial software" to false
            )
        ), Question(
            "What are Kotlin coroutines?", mapOf(
                "These are functions which accept other functions as arguments or return them" to false,
                "It`s Kotlin`s term for class method" to false,
                "They provide asynchronous code without thread blocking" to true,
                "That`s now the automatically generate method hashCode() and equals() in data classes are called" to false,
                "This is the name of a croissant" to false
            )
        )
    )
