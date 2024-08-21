package com.example.hhrutest.data.api

import com.example.hhrutest.data.entities.DataFromJSON
import com.example.hhrutest.data.entities.Offer
import com.example.hhrutest.data.entities.Vacancy
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface GetDataApi {
    @GET("/json/hh/test/data")
    fun loadData(): Call<DataFromJSON>

    companion object {

        val retrofit by lazy {
            Retrofit
                .Builder()
                .baseUrl("https://jjwjw.wiremockapi.cloud")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create<GetDataApi>()
        }
    }
}



