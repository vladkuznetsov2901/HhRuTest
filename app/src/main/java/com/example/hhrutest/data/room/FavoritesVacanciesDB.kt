package com.example.hhrutest.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_vacancies")
data class FavoritesVacanciesDB(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id: String
)
