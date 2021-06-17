package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.model.Answer
import com.rsschool.quiz.model.Question
import com.rsschool.quiz.utils.*

class QuizFragment(private var onRadioButtonListener: RadioButtonListener?) : Fragment() {

    interface RadioButtonListener {
        fun onClickRadioButton(questionId: Int, answer: Answer)
    }

    private var quizListener: QuizListener? = null
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
                .fromJson(it.getString(OPTIONS), Question::class.java)
        }
        val answerId = arguments?.getInt(ANSWER) ?: 0
        showQuestion(question = question, answerId = answerId)
        val position = arguments?.getInt(POSITION) ?: 0
        if (position == 0) {
            binding.previousButton.isEnabled = false
            binding.toolbar.navigationIcon = null
        }
        if (arguments?.getBoolean(SUBMIT) == true) {
            binding.nextButton.text = getString(R.string.submit)
        }

        binding.toolbar.setNavigationOnClickListener {
            quizListener?.onPreviousButton()
        }
        binding.toolbar.title = getString(R.string.question, position + 1)
        binding.nextButton.setOnClickListener { quizListener?.onNextButtonClick() }
        binding.previousButton.setOnClickListener { quizListener?.onPreviousButton() }


        binding.radioGroup.setOnCheckedChangeListener { radioGroup, index ->
            run {
                val radioButton = radioGroup.getChildAt(index - 1) as RadioButton
                binding.nextButton.isEnabled = true
                onRadioButtonListener?.onClickRadioButton(
                    questionId = position + 1,
                    answer = Answer(id = index, text = radioButton.text.toString())
                )
            }
        }
        return binding.root
    }

    private fun showQuestion(question: Question?, answerId: Int) {
        binding.question.text = question?.text ?: "error"
        var index = 1
        question?.options?.forEach { (key, _) ->
            run {
                val radioButton = RadioButton(context)
                radioButton.text = key
                radioButton.height = 150
                if (index == answerId) radioButton.isChecked = true
                radioButton.id = index++
                binding.radioGroup.addView(radioButton)
            }
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
        onRadioButtonListener = null
        super.onDestroyView()
    }
}