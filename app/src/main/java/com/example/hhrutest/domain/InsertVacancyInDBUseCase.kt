package com.example.hhrutest.domain

import com.example.hhrutest.data.room.FavoritesVacanciesDB
import com.example.hhrutest.data.room.FavoritesVacanciesDao
import javax.inject.Inject

class InsertVacancyInDBUseCase @Inject constructor(private val favoritesVacanciesDao: FavoritesVacanciesDao) {

    suspend fun insertVacancyInDB(favoritesVacanciesDB: FavoritesVacanciesDB) {
        favoritesVacanciesDao.insertVacancyToDb(favoritesVacanciesDB)
    }

}