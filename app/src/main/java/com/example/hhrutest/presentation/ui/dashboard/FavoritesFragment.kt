package com.example.hhrutest.presentation.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hhrutest.adapters.VacancyAdapter
import com.example.hhrutest.databinding.FragmentFavoritesBinding
import com.example.hhrutest.presentation.ui.home.HomeViewModel
import com.example.hhrutest.presentation.ui.home.HomeViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var favoritesViewModelFactory: FavoritesViewModelFactory

    private val viewModel: FavoritesViewModel by viewModels { favoritesViewModelFactory }

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels { homeViewModelFactory }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val vacancyAdapter = VacancyAdapter(parentFragmentManager)

        binding.vacanciesRecycler.layoutManager = GridLayoutManager(context, 1)

        binding.vacanciesRecycler.adapter = vacancyAdapter

        lifecycleScope.launch {
            viewModel.getFavoriteVacancies()
            viewModel.vacanciesFromDBInfo.collect { vacanciesFromDBInfo ->
                vacancyAdapter.submitList(vacanciesFromDBInfo)
            }
        }

        vacancyAdapter.setOnFavoriteButtonClickListener(object :
            VacancyAdapter.OnFavoriteButtonClickListener {
            override fun onFavoriteBtnClick(id: String) {
                homeViewModel.insertOrDeleteVacanceDB(id)
                viewModel.getFavoriteVacancies()
                lifecycleScope.launch {
                    viewModel.vacanciesFromDBInfo.collect { vacanciesFromDBInfo ->
                        vacancyAdapter.submitList(vacanciesFromDBInfo)
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}