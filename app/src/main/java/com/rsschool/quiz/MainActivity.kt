package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rsschool.quiz.adapter.QuizAdapter
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), QuizListener {

    private lateinit var binding: ActivityMainBinding
    private var answer: Map<String, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val quizAdapter = QuizAdapter(this)
        binding.viewPager2.adapter = quizAdapter
        binding.viewPager2.isUserInputEnabled = false
        setContentView(binding.root)
    }

    override fun onNextButtonClick() {
        binding.viewPager2.apply {
            setCurrentItem(currentItem + 1, false)
        }
    }

    override fun onPreviousButton() {
        binding.viewPager2.apply {
            setCurrentItem(currentItem - 1, false)
        }
    }

    override fun onBackToQuizButton() {
        binding.viewPager2.adapter?.notifyDataSetChanged()
        onPreviousButton()
    }
}
