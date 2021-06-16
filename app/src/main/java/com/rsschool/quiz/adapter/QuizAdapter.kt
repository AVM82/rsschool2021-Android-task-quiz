package com.rsschool.quiz.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rsschool.quiz.QuizFragment
import com.rsschool.quiz.model.Answer
import com.rsschool.quiz.storage.makeQuestionList
import com.rsschool.quiz.utils.ANSWER
import com.rsschool.quiz.utils.GsonParser
import com.rsschool.quiz.utils.OPTIONS
import com.rsschool.quiz.utils.POSITION

class QuizAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity),
    QuizFragment.RadioButtonListener {

    private val questionList = makeQuestionList()
    private var answerList = mutableMapOf<Int, Answer>()


    override fun getItemCount(): Int = questionList.size

    override fun createFragment(position: Int): Fragment {
        return QuizFragment(this).apply {
            val question = questionList[position]
            arguments =
                bundleOf(
                    OPTIONS to GsonParser.getInstance().toJson(question),
                    POSITION to position,
                    ANSWER to answerList[question.id]?.id
                )
        }
    }

    override fun onClickRadioButton(questionId: Int, answer: Answer) {
        answerList[questionId] = answer
    }
}
