package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.utils.RESULT

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        binding.result.text = getString(R.string.result, "${arguments?.getInt(RESULT)} %")
        return binding.root
    }
}