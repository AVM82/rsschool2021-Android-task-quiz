package com.rsschool.quiz

import android.content.Context
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

    interface QuizFragmentListener {
        fun onNextButtonClick(pos: Int)
        fun onPreviousButton(pos: Int)
    }

    private var quizFragmentListener: QuizFragmentListener? = null
    private var _binding: FragmentQuizBinding? = null
    private val binding
        get() = requireNotNull(_binding)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val question = arguments?.let {
            GsonParser.getInstance()
                .fromJson(it.getString("options"), Question::class.java)
        }
        showQuestion(question)
        val position = arguments?.getInt("position") ?: 0
        if (position == 0) binding.previousButton.isEnabled = false


        binding.toolbar.title = getString(R.string.question, position + 1)
        binding.nextButton.setOnClickListener { quizFragmentListener?.onNextButtonClick(position) }
        binding.previousButton.setOnClickListener { quizFragmentListener?.onPreviousButton(position) }

        binding.radioGroup.setOnCheckedChangeListener { rg, i ->
            run {
                val r = rg.getChildAt(i % 10) as RadioButton
//                Toast.makeText(activity, r.text, Toast.LENGTH_LONG).show()
//                quizFragmentListener?.onNextButtonClick(position)
            }
        }
        return binding.root
    }

    private fun showQuestion(question: Question?) {
        binding.question.text = question?.text ?: "error"
        var index = 0
        question?.options?.forEach { (key, _) ->
            run {
                val radioButton = RadioButton(context)
                radioButton.text = key
//                radioButton.text = (question.id * 10 + (++index)).toString()
                radioButton.height = 150
                if (index == 3) radioButton.isChecked = true
                radioButton.id = question.id * 10 + (index++)
                binding.radioGroup.addView(radioButton)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is QuizFragmentListener) {
            quizFragmentListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        quizFragmentListener = null
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): QuizFragment = QuizFragment()
    }
}