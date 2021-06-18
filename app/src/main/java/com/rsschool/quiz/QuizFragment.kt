package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.model.Answer
import com.rsschool.quiz.model.Question
import com.rsschool.quiz.utils.*
import kotlin.math.abs


class QuizFragment : Fragment() {

    interface RadioButtonListener {
        fun onClickRadioButton(questionId: Int, answer: Answer)
    }

    private var position: Int = 0
    private var question: Question? = null
    private var onRadioButtonListener: RadioButtonListener? = null
    private var quizListener: QuizListener? = null
    private var _binding: FragmentQuizBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        position = arguments?.getInt(POSITION) ?: 0
        when (if (position > 4) abs((position % 10) - 5) else position) {
            0 -> setTheme(R.style.Theme_Quiz_First)
            1 -> setTheme(R.style.Theme_Quiz_Second)
            2 -> setTheme(R.style.Theme_Quiz_Third)
            3 -> setTheme(R.style.Theme_Quiz_Fourth)
            4 -> setTheme(R.style.Theme_Quiz_Fifth)
            else -> setTheme(R.style.Theme_Quiz)
        }

        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setTheme(@StyleRes theme: Int) {
        activity?.setTheme(theme)
        val typedValue = TypedValue()
        context?.theme?.resolveAttribute(android.R.attr.statusBarColor, typedValue, true)
        val window = activity?.window
        window?.statusBarColor = typedValue.data
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        question = arguments?.let {
            GsonParser.getInstance()
                .fromJson(it.getString(OPTIONS), Question::class.java)
        }
        val answerId = arguments?.getInt(ANSWER) ?: 0
        if (answerId > 0) binding.nextButton.isEnabled = true
        showQuestion(question = question, answerId = answerId)
        if (position == 0) {
            binding.previousButton.isEnabled = false
            binding.toolbar.navigationIcon = null
        }
        if (arguments?.getBoolean(SUBMIT) == true) {
            binding.nextButton.text = getString(R.string.submit)
        }

        binding.toolbar.title = getString(R.string.question, position + 1)
        setListeners()
    }

    private fun setListeners() {

        binding.toolbar.setNavigationOnClickListener { quizListener?.onPreviousButtonClick() }

        binding.nextButton.setOnClickListener { quizListener?.onNextButtonClick() }

        binding.previousButton.setOnClickListener { quizListener?.onPreviousButtonClick() }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, index ->
            run {
                val radioButton = radioGroup.getChildAt(index - 1) as RadioButton
                binding.nextButton.isEnabled = true
                question?.let {
                    onRadioButtonListener?.onClickRadioButton(
                        questionId = it.id,
                        answer = Answer(id = index, text = radioButton.text.toString())
                    )
                }
            }
        }
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

    companion object {
        fun newInstance(radioButtonListener: RadioButtonListener): QuizFragment {
            val fragment = QuizFragment()
            fragment.onRadioButtonListener = radioButtonListener
            return fragment
        }
    }
}