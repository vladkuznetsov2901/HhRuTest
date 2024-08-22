package com.example.hhrutest.data.repository

import android.util.Log
import com.example.hhrutest.data.api.GetDataApi
import com.example.hhrutest.data.entities.Offer
import com.example.hhrutest.data.entities.Vacancy
import javax.inject.Inject

class DataRepository @Inject constructor() {

    fun getOffers(): List<Offer> {
        return GetDataApi.retrofit.loadData().execute().body()?.offers ?: emptyList()
    }

    fun getVacancies(): List<Vacancy> {
        val vacancies = GetDataApi.retrofit.loadData().execute().body()?.vacancies ?: emptyList()
        Log.d("TAG", "getVacancies: ${vacancies.size}")
        return vacancies
    }

}