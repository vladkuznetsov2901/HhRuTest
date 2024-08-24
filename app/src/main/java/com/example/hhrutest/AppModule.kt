package com.example.hhrutest

import android.content.Context
import androidx.room.Room
import com.example.hhrutest.data.room.AppDatabase
import com.example.hhrutest.data.room.FavoritesVacanciesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): FavoritesVacanciesDao {
        return appDatabase.favoritesVacanciesDao()
    }

}