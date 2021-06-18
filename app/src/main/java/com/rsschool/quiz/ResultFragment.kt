package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.utils.EMAIL_TEXT
import com.rsschool.quiz.utils.RESULT

class ResultFragment : Fragment() {
    private var quizListener: QuizListener? = null
    private var _binding: FragmentResultBinding? = null
    private val binding
        get() = requireNotNull(_binding)
    private var result: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = getString(R.string.result, "${arguments?.getInt(RESULT)} %")
        binding.result.text = result
        setListeners()
    }

    private fun setListeners() {

        binding.backButton.setOnClickListener {
            quizListener?.onRestartQuizButtonClick()
        }

        binding.closeButton.setOnClickListener {
            activity?.finish()
        }

        binding.shareButton.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
                putExtra(Intent.EXTRA_TEXT, "$result\n\n ${arguments?.getString(EMAIL_TEXT)}")
            }
            startActivity(emailIntent)
        }
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}