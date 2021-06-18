package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rsschool.quiz.adapter.QuizAdapter
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), QuizListener {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewPager2.adapter = QuizAdapter(this)
        binding.viewPager2.isUserInputEnabled = false
        setContentView(binding.root)
    }

    override fun onNextButtonClick() {
        binding.viewPager2.apply {
            setCurrentItem(currentItem + 1, false)
        }
    }

    override fun onPreviousButtonClick() {
        binding.viewPager2.apply {
            setCurrentItem(currentItem - 1, false)
        }
    }

    override fun onRestartQuizButtonClick() {
        binding.viewPager2.apply {
            adapter = QuizAdapter(this@MainActivity)
            setCurrentItem(0, false)
        }
    }
}
