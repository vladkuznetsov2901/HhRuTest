package com.example.hhrutest.presentation.ui.sign_in

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hhrutest.R
import com.example.hhrutest.databinding.FragmentSignInBinding
import com.example.hhrutest.presentation.ui.home.HomeViewModel
import com.example.hhrutest.presentation.ui.home.HomeViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
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
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextTextEmailAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length > 0) binding.clearTextButton.visibility = View.VISIBLE
                else binding.clearTextButton.visibility = View.GONE
            }
        })

        binding.clearTextButton.setOnClickListener {
            binding.editTextTextEmailAddress.text?.clear()
            binding.clearTextButton.visibility = View.GONE
        }


        binding.buttonContinue.setOnClickListener {
            if (viewModel.isValidEmail(binding.editTextTextEmailAddress.text.toString())) {
                binding.warning.visibility = View.GONE
                val bundle = Bundle()
                bundle.putString(EMAIL_KEY, binding.editTextTextEmailAddress.text.toString())
                findNavController().navigate(
                    R.id.action_signInFragment_to_enterCodeFragment,
                    bundle
                )
            } else {
                binding.warning.visibility = View.VISIBLE
            }

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