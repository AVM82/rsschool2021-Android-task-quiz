package com.rsschool.quiz.adapter

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rsschool.quiz.QuizFragment
import com.rsschool.quiz.ResultFragment
import com.rsschool.quiz.model.Answer
import com.rsschool.quiz.storage.makeQuestionList
import com.rsschool.quiz.utils.*

class QuizAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity),
    QuizFragment.RadioButtonListener {

    private val questionList = makeQuestionList()
    private var answerList = mutableMapOf<Int, Answer>()

    override fun getItemCount(): Int = questionList.size + 1

    override fun createFragment(position: Int): Fragment =
        if (position < questionList.size) createQuizFragment(position) else createResultFragment()

    private fun createQuizFragment(position: Int): QuizFragment {
        return QuizFragment.newInstance(this).apply {
            val question = questionList[position]
            arguments =
                bundleOf(
                    OPTIONS to GsonParser.getInstance().toJson(question),
                    POSITION to position,
                    ANSWER to answerList[question.id]?.id,
                    SUBMIT to (position == questionList.size - 1)
                )
        }
    }

    private fun createResultFragment(): ResultFragment {
        return ResultFragment().apply {
            arguments = bundleOf(
                RESULT to calculateResult(),
                EMAIL_TEXT to makeEmailText()
            )
        }
    }

    private fun makeEmailText(): String {
        var text = ""
        answerList.forEach { (key, value) ->
            run {
                text += "${key}) ${questionList[key - 1].text} \n\n You answer: ${value.text}\n\n"
            }
        }
        return text
    }

    private fun calculateResult(): Int {
        var correctAnswerCount = 0
        answerList.forEach { (key, value) ->
            run {
                correctAnswerCount += questionList.filter { it.id == key && it.options[value.text] == true }.size
            }
        }
        return correctAnswerCount * 100 / questionList.size
    }

    override fun onClickRadioButton(questionId: Int, answer: Answer) {
        answerList[questionId] = answer
        Log.d("ANSWER", answerList.toString())
    }
}
