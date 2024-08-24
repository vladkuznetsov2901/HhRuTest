package com.example.hhrutest

import android.app.Application
import androidx.room.Room
import com.example.hhrutest.data.room.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {


    private lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "favorites_vacancies_db"
        ).fallbackToDestructiveMigration()
            .build()

    }

}