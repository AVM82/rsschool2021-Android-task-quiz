package com.rsschool.quiz.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rsschool.quiz.QuizFragment
import com.rsschool.quiz.storage.makeQuestionList
import com.rsschool.quiz.utils.GsonParser

class QuizAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val questionList = makeQuestionList()

    override fun getItemCount(): Int = questionList.size

    override fun createFragment(position: Int): Fragment {
        return QuizFragment.newInstance().apply {
            arguments =
                bundleOf("options" to GsonParser.getInstance().toJson(questionList[position]))
        }
    }
}
