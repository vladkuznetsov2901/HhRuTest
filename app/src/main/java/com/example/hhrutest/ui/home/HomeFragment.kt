package com.example.hhrutest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hhrutest.adapters.VacancyAdapter
import com.example.hhrutest.databinding.FragmentHomeBinding
import com.example.hhrutest.ui.sign_in.SignInViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancyAdapter = VacancyAdapter()

        binding.vacanciesRecycler.layoutManager = GridLayoutManager(context, 1)

        binding.vacanciesRecycler.adapter = vacancyAdapter
        lifecycleScope.launch {
            viewModel.getVacancies()
            viewModel.vacanciesInfo.collect { vacancy ->
                vacancyAdapter.submitList(vacancy)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}