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
import com.example.hhrutest.adapters.QuestionsAdapter
import com.example.hhrutest.databinding.FragmentVacancyPageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VacancyPageFragment : Fragment() {

    private var _binding: FragmentVacancyPageBinding? = null

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
        _binding = FragmentVacancyPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentObjectId = arguments?.getString(OBJECT_KEY)

        val questionsAdapter = QuestionsAdapter(emptyList())

        var isInDB = false

        binding.recyclerQuestions.layoutManager = GridLayoutManager(context, 1)

        binding.recyclerQuestions.adapter = questionsAdapter
        lifecycleScope.launch {
            viewModel.getVacancies()
            viewModel.vacanciesInfo.collect { vacancies ->
                val selectedVacancy = vacancies.find { it.id == currentObjectId }
                if (selectedVacancy != null) {
                    binding.vacancyTitleText.text = selectedVacancy.title

                    binding.vacancySalaryText.text = selectedVacancy.salary.full

                    binding.vacancyExperienceText.text =
                        "Требуемый опыт: ${selectedVacancy.experience.text}"

                    binding.vacancyBusynessText.text =
                        "${selectedVacancy.schedules.first()}, ${selectedVacancy.schedules.last()}"

                    if (selectedVacancy.appliedNumber == 0) binding.alreadyResponsedContainer.visibility =
                        View.GONE
                    else binding.alreadyResponesed.text =
                        "${selectedVacancy.appliedNumber} человек уже откликнулись"

                    if (selectedVacancy.lookingNumber == 0) binding.nowWatchingContainer.visibility =
                        View.GONE
                    else binding.nowWatching.text =
                        "${selectedVacancy.lookingNumber} человек сейчас смотрят"

                    binding.companyTitle.text = selectedVacancy.company

                    binding.companyDescriptionText.text = selectedVacancy.description

                    binding.yourTasksListText.text = selectedVacancy.responsibilities

                    binding.companyAdress.text =
                        "${selectedVacancy.address.town}, ${selectedVacancy.address.street}, ${selectedVacancy.address.house}"
                    questionsAdapter.updateQuestions(selectedVacancy.questions)
                    if (selectedVacancy.isFavorite) binding.favoriteButton.setImageResource(R.drawable.favorites_true_ic)
                    else binding.favoriteButton.setImageResource(R.drawable.favorites_ic)
                    if (viewModel.isVacancyInDB(currentObjectId!!)) binding.favoriteButton.setImageResource(R.drawable.favorites_true_ic)
                    else binding.favoriteButton.setImageResource(R.drawable.favorites_ic)
                }
            }
        }

        questionsAdapter.setOnItemClickListener(object : QuestionsAdapter.OnItemClickListener {
            override fun onItemClick(question: String) {
                val bottomSheetDialog = ResponseBottomSheetDialog.newInstance(question)
                bottomSheetDialog.show(parentFragmentManager, bottomSheetDialog.tag)
            }
        })
        binding.vacancyButton.setOnClickListener {
            val bottomSheetDialog = ResponseBottomSheetDialog()
            bottomSheetDialog.show(parentFragmentManager, bottomSheetDialog.tag)
        }

        binding.favoriteButton.setOnClickListener {
            isInDB = !isInDB
            if (currentObjectId != null) {
                viewModel.insertOrDeleteVacanceDB(currentObjectId)
                if (isInDB) binding.favoriteButton.setImageResource(R.drawable.favorites_true_ic)
                else binding.favoriteButton.setImageResource(R.drawable.favorites_ic)
                lifecycleScope.launch {
                    viewModel.updateBubbleCount()
                }
            }
        }

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