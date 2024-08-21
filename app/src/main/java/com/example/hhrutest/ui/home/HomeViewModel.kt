package com.example.hhrutest.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hhrutest.data.api.GetDataApi
import com.example.hhrutest.data.entities.DataFromJSON
import com.example.hhrutest.data.entities.Vacancy
import com.example.hhrutest.data.repository.DataRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class HomeViewModel : ViewModel() {

    private val _vacanciesInfo = MutableStateFlow<List<Vacancy>>(emptyList())
    val vacanciesInfo = _vacanciesInfo.asStateFlow()

    suspend fun getVacancies() {
        withContext(Dispatchers.IO) {
            try {
                val response = GetDataApi.retrofit.loadData().execute()
                if (response.isSuccessful) {
                    _vacanciesInfo.value = response.body()?.vacancies ?: emptyList()
                    Log.d("TAG", "getVacancies: ${response.body()?.vacancies?.size}")
                } else {
                    Log.e("getObjectInfo", "Request failed with code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("getObjectInfo", "Request failed with exception: ${e.message}")
            }
        }
    }
}