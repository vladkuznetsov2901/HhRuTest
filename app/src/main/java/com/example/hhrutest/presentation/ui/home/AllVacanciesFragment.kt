package com.example.hhrutest.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hhrutest.R
import com.example.hhrutest.adapters.VacancyAdapter
import com.example.hhrutest.databinding.FragmentAllVacanciesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllVacanciesFragment : Fragment() {

    private var _binding: FragmentAllVacanciesBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    private val viewModel: HomeViewModel by viewModels { homeViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentAllVacanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancyAdapter = VacancyAdapter(parentFragmentManager)

        binding.vacanciesRecycler.layoutManager = GridLayoutManager(context, 1)

        binding.vacanciesRecycler.adapter = vacancyAdapter
        lifecycleScope.launch {
            viewModel.getVacancies()
            viewModel.vacanciesInfo.collect { vacancy ->
                vacancyAdapter.submitList(vacancy)
                binding.vacanciesCountText.text =
                    vacancy.size.toString() + viewModel.declensionOfTheWordVacancies(vacancy.size)
            }
        }

        vacancyAdapter.setOnItemClickListener(object : VacancyAdapter.OnItemClickListener {
            override fun onItemClick(id: String) {
                val bundle = Bundle()
                bundle.putString(OBJECT_KEY, id)
                findNavController().navigate(
                    R.id.action_allVacanciesFragment_to_vacancyPageFragment,
                    bundle
                )
            }
        })


        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val OBJECT_KEY = "object_id"
    }
}