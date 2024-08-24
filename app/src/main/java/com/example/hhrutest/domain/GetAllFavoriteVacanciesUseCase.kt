package com.example.hhrutest.domain

import com.example.hhrutest.data.room.FavoritesVacanciesDB
import com.example.hhrutest.data.room.FavoritesVacanciesDao
import javax.inject.Inject

class GetAllFavoriteVacanciesUseCase @Inject constructor(private val favoritesVacanciesDao: FavoritesVacanciesDao) {

    suspend fun getAllFavoriteVacancies(): List<FavoritesVacanciesDB> {
        return favoritesVacanciesDao.getAllFavoritesVacancies()
    }

}