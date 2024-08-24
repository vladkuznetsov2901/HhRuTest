package com.example.hhrutest.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hhrutest.adapters.VacancyAdapter
import com.example.hhrutest.data.entities.Offer
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

class HomeViewModel @Inject constructor(
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val getOffersUseCase: GetOffersUseCase,
    private val getAllFavoriteVacancies: GetAllFavoriteVacanciesUseCase,
    private val insertVacancyInDBUseCase: InsertVacancyInDBUseCase,
    private val deleteVacancyUseCase: DeleteVacancyUseCase,
) : ViewModel() {

    private val _vacanciesInfo = MutableStateFlow<List<Vacancy>>(emptyList())
    val vacanciesInfo = _vacanciesInfo.asStateFlow()

    private val _offersInfo = MutableStateFlow<List<Offer>>(emptyList())
    val offersInfo = _offersInfo.asStateFlow()

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

    suspend fun getOffers() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getOffersUseCase.getAllOffers()
            }.fold(
                onSuccess = { _offersInfo.value = it },
                onFailure = { Log.d("HomeViewModelOffers", it.message ?: "") }
            )
        }
    }

    fun insertOrDeleteVacanceDB(vacancyID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val vacancyDB = FavoritesVacanciesDB(vacancyID)
            if (isVacancyInDB(vacancyID)) {
                deleteVacancyUseCase.deleteVacancyFromDB(vacancyDB)

            } else {
                insertVacancyInDBUseCase.insertVacancyInDB(
                    vacancyDB
                )
            }
        }
    }

    suspend fun isVacancyInDB(vacancyID: String): Boolean {
        val vacancyDB = FavoritesVacanciesDB(id = vacancyID)
        val favoriteVacanciesResult = getAllFavoriteVacancies.getAllFavoriteVacancies()
        return if (vacancyDB in favoriteVacanciesResult) {
            true
        } else false
    }

    fun deleteVacancyFromFavoritesDB(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteVacancyUseCase.deleteVacancyFromDB(FavoritesVacanciesDB(id))
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