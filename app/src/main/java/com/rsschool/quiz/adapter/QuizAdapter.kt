package com.rsschool.quiz.adapter

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

    override fun createFragment(position: Int): Fragment {
        return if (position < questionList.size) {
            QuizFragment(this).apply {
                val question = questionList[position]
                arguments =
                    bundleOf(
                        OPTIONS to GsonParser.getInstance().toJson(question),
                        POSITION to position,
                        ANSWER to answerList[question.id]?.id
                    )
            }
        } else {
            ResultFragment().apply {
                arguments = bundleOf(
                    RESULT to calculateResult()
                )
            }
        }
    }

    private fun calculateResult(): Int {
        var result = 0
        answerList.forEach { (key, value) ->
            run {
                result += questionList.filter { it.id == key && it.options[value.text] == true }.size
            }
        }
        return result * 100 / questionList.size
    }

    override fun onClickRadioButton(questionId: Int, answer: Answer) {
        answerList[questionId] = answer
    }
}
