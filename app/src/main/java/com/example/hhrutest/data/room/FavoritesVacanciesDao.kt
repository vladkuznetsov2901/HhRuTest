package com.example.hhrutest.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritesVacanciesDao {
    @Insert
    suspend fun insertVacancyToDb(favoritesVacanciesDB: FavoritesVacanciesDB)

    @Query("SELECT * FROM favorites_vacancies")
    suspend fun getAllFavoritesVacancies(): List<FavoritesVacanciesDB>

    @Delete
    suspend fun deleteVacancyFromFavorites(favoritesVacanciesDB: FavoritesVacanciesDB)
}