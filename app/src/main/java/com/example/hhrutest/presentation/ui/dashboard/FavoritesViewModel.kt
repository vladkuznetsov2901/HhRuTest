package com.example.hhrutest.presentation.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hhrutest.data.entities.Vacancy
import com.example.hhrutest.data.room.FavoritesVacanciesDB
import com.example.hhrutest.domain.DeleteVacancyUseCase
import com.example.hhrutest.domain.GetAllFavoriteVacanciesUseCase
import com.example.hhrutest.domain.GetOffersUseCase
import com.example.hhrutest.domain.GetVacanciesUseCase
import com.example.hhrutest.domain.InsertVacancyInDBUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val getAllFavoriteVacancies: GetAllFavoriteVacanciesUseCase,
    private val deleteVacancyUseCase: DeleteVacancyUseCase
) : ViewModel() {

    private val _vacanciesInfo = MutableStateFlow<List<Vacancy>>(emptyList())
    val vacanciesInfo = _vacanciesInfo.asStateFlow()

    private val _vacanciesFromDBInfo = MutableStateFlow<List<Vacancy>>(emptyList())
    val vacanciesFromDBInfo = _vacanciesFromDBInfo.asStateFlow()

    fun getFavoriteVacancies() {
        viewModelScope.launch(Dispatchers.IO) {
            val allVacanciesResult = kotlin.runCatching {
                getVacanciesUseCase.getAllVacancies()
            }

            val favoriteVacanciesResult = kotlin.runCatching {
                getAllFavoriteVacancies.getAllFavoriteVacancies()
            }

            allVacanciesResult.fold(
                onSuccess = { allVacancies ->
                    _vacanciesInfo.value = allVacancies

                    favoriteVacanciesResult.fold(
                        onSuccess = { favoriteVacancies ->
                            val filteredVacancies = allVacancies.filter { vacancy ->
                                favoriteVacancies.any { it.id == vacancy.id }
                            }
                            _vacanciesFromDBInfo.value = filteredVacancies
                        },
                        onFailure = {
                            Log.d("FavoritesViewModel", it.message ?: "Error fetching favorite vacancies")
                        }
                    )
                },
                onFailure = {
                    Log.d("FavoritesViewModel", it.message ?: "Error fetching all vacancies")
                }
            )
        }
    }

    fun deleteVacancyFromFavoritesDB(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteVacancyUseCase.deleteVacancyFromDB(FavoritesVacanciesDB(id))
        }
    }
}

