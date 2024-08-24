package com.example.hhrutest.presentation.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hhrutest.R
import com.example.hhrutest.adapters.OffersAdapter
import com.example.hhrutest.adapters.VacancyAdapter
import com.example.hhrutest.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    private val viewModel: HomeViewModel by viewModels { homeViewModelFactory }

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
        val vacancyAdapter = VacancyAdapter(parentFragmentManager)
        val offersAdapter = OffersAdapter()
        binding.buttonAllVacancies.visibility = View.INVISIBLE
        binding.vacanciesRecycler.layoutManager = GridLayoutManager(context, 1)
        binding.offersRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.vacanciesRecycler.adapter = vacancyAdapter
        binding.offersRecycler.adapter = offersAdapter

        lifecycleScope.launch {
            viewModel.getOffers()
            viewModel.offersInfo.collect { offers ->
                offersAdapter.submitList(offers)
            }
        }

        offersAdapter.setOnItemClickListener(object : OffersAdapter.OnItemClickListener {
            override fun onItemClick(link: String) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            }
        })

        lifecycleScope.launch {
            viewModel.getVacancies()
            viewModel.vacanciesInfo.collect { vacancy ->
                vacancyAdapter.submitList(vacancy.take(3))
                binding.buttonAllVacancies.text =
                    vacancy.size.toString() + viewModel.declensionOfTheWordVacancies(vacancy.size)
                binding.buttonAllVacancies.visibility = View.VISIBLE

            }
        }

        vacancyAdapter.setOnItemClickListener(object : VacancyAdapter.OnItemClickListener {
            override fun onItemClick(id: String) {
                val bundle = Bundle()
                bundle.putString(OBJECT_KEY, id)
                findNavController().navigate(
                    R.id.action_navigation_search_to_vacancyPageFragment,
                    bundle
                )
            }
        })

        vacancyAdapter.setOnFavoriteButtonClickListener(object :
            VacancyAdapter.OnFavoriteButtonClickListener {
            override fun onFavoriteBtnClick(id: String) {
                vacancyAdapter.currentList.find { it.id == id }!!.isFavorite =
                    !vacancyAdapter.currentList.find { it.id == id }!!.isFavorite
                viewModel.insertOrDeleteVacanceDB(id)
                lifecycleScope.launch {
                    viewModel.updateBubbleCount()
                }
            }
        })

        binding.buttonAllVacancies.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_search_to_allVacanciesFragment)
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