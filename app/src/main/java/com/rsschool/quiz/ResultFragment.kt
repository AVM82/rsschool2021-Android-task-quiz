package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.utils.RESULT

class ResultFragment : Fragment() {
    private var quizListener: QuizListener? = null
    private var _binding: FragmentResultBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        binding.result.text = getString(R.string.result, "${arguments?.getInt(RESULT)} %")
        binding.backButton.setOnClickListener {
            quizListener?.onRestartQuizButton()
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is QuizListener) {
            quizListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        quizListener = null
    }
}