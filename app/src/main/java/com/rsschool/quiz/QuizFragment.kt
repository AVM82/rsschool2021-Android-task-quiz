package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.model.Question
import com.rsschool.quiz.utils.GsonParser

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding
        get() = requireNotNull(_binding)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val quiz = arguments?.let {
            GsonParser.getInstance()
                .fromJson(it.getString("options"), Question::class.java)
        }
        binding.question.text = quiz?.text ?: "error"
        quiz?.options?.forEach { (key, _) ->
            run {
                val rb = RadioButton(context)
                rb.text = key
                rb.height = 150
                rb.id = View.generateViewId()
                binding.radioGroup.addView(rb)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): QuizFragment = QuizFragment()
    }
}