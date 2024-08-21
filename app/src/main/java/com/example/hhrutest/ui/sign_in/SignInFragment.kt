package com.example.hhrutest.ui.sign_in

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hhrutest.R
import com.example.hhrutest.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextTextEmailAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (viewModel.isEmailValid(s.toString())) {
                    binding.buttonContinue.isEnabled = true
                    binding.buttonContinue.isClickable = true
                } else {
                    binding.buttonContinue.isEnabled = false
                    binding.buttonContinue.isClickable = false
                }
            }
        })

        binding.buttonContinue.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(EMAIL_KEY, binding.editTextTextEmailAddress.text.toString())
            findNavController().navigate(R.id.action_signInFragment_to_enterCodeFragment, bundle)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val EMAIL_KEY = "email_adress"
    }


}