package com.rsschool.quiz.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonParser {

    fun getInstance(): Gson = GsonBuilder().create()

}