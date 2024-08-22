package com.example.hhrutest.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.hhrutest.databinding.FragmentResponseBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResponseBottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: FragmentResponseBottomSheetDialogBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentResponseBottomSheetDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            binding.addAccompanying.visibility = View.GONE
            binding.editTextText.visibility = View.VISIBLE
            binding.editTextText.setText(arguments?.getString("question"))
        }

        binding.addAccompanying.setOnClickListener {
            binding.addAccompanying.visibility = View.GONE
            binding.editTextText.visibility = View.VISIBLE
            binding.editTextText.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.editTextText, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.responseButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(question: String): ResponseBottomSheetDialog {
            val args = Bundle()
            args.putString("question", question)

            val fragment = ResponseBottomSheetDialog()
            fragment.arguments = args
            return fragment
        }
    }

}