package com.example.hhrutest.domain

import com.example.hhrutest.data.room.FavoritesVacanciesDB
import com.example.hhrutest.data.room.FavoritesVacanciesDao
import javax.inject.Inject

class DeleteVacancyUseCase @Inject constructor(private val favoritesVacanciesDao: FavoritesVacanciesDao) {

    suspend fun deleteVacancyFromDB(favoritesVacanciesDB: FavoritesVacanciesDB) {
        favoritesVacanciesDao.deleteVacancyFromFavorites(favoritesVacanciesDB)
    }

}