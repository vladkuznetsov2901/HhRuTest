package com.example.hhrutest.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hhrutest.data.entities.Vacancy
import com.example.hhrutest.domain.GetVacanciesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getVacanciesUseCase: GetVacanciesUseCase): ViewModel() {

    private val _vacanciesInfo = MutableStateFlow<List<Vacancy>>(emptyList())
    val vacanciesInfo = _vacanciesInfo.asStateFlow()

    suspend fun getVacancies() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getVacanciesUseCase.getAllVacancies()
            }.fold(
                onSuccess = { _vacanciesInfo.value = it },
                onFailure = { Log.d("HomeViewModelVacancies", it.message ?: "") }
            )
        }
    }

    fun declensionOfTheWordVacancies(count: Int): String {
        return when (count) {
            1 -> " вакансия"
            2, 3, 4 -> " вакансии"
            else -> " вакансий"
        }
    }
}