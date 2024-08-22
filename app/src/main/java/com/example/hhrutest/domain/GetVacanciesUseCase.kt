package com.example.hhrutest.domain

import com.example.hhrutest.data.entities.Vacancy
import com.example.hhrutest.data.repository.DataRepository
import javax.inject.Inject

class GetVacanciesUseCase @Inject constructor(private val dataRepository: DataRepository) {

    fun getAllVacancies(): List<Vacancy> {
        return dataRepository.getVacancies()
    }
}