package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rsschool.quiz.adapter.QuizAdapter
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), QuizFragment.QuizFragmentListener {

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

    override fun onNextButtonClick(pos: Int) {
        binding.viewPager2.setCurrentItem(pos + 1, false)
    }

    override fun onPreviousButton(pos: Int) {
        if (pos > 0) {
            binding.viewPager2.setCurrentItem(pos - 1, false)
        }
    }
}
