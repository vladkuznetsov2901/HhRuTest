package com.example.hhrutest.presentation.ui.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hhrutest.R
import com.example.hhrutest.databinding.FragmentEnterCodeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnterCodeFragment : Fragment() {

    private var _binding: FragmentEnterCodeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var signInViewModelFactory: SignInViewModelFactory

    private val viewModel: SignInViewModel by viewModels { signInViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEnterCodeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accessButton = binding.buttonAccess
        accessButton.isEnabled = false
        binding.sendCodeToEmailTextView.text = getString(
            R.string.send_code_to_email_text, arguments?.getString(
                EMAIL_KEY
            )
        )

        val editTexts = listOf(
            binding.editTextNumber,
            binding.editTextNumber2,
            binding.editTextNumber3,
            binding.editTextNumber4
        )

        viewModel.requestFocusOnEditText(
            binding.editTextNumber,
            binding.editTextNumber2,
            accessButton,
            editTexts
        )
        viewModel.requestFocusOnEditText(
            binding.editTextNumber2,
            binding.editTextNumber3,
            accessButton,
            editTexts
        )
        viewModel.requestFocusOnEditText(
            binding.editTextNumber3,
            binding.editTextNumber4,
            accessButton,
            editTexts
        )
        viewModel.requestFocusOnEditText(
            binding.editTextNumber4,
            null,
            accessButton,
            editTexts
        )

        binding.buttonAccess.setOnClickListener {
            findNavController().navigate(R.id.action_enterCodeFragment_to_navigation_search)
            viewModel.isSignIn.value = true
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