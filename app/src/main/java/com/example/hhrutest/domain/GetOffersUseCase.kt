package com.example.hhrutest.domain

import com.example.hhrutest.data.entities.Offer
import com.example.hhrutest.data.entities.Vacancy
import com.example.hhrutest.data.repository.DataRepository
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(private val dataRepository: DataRepository) {

    fun getAllOffers(): List<Offer> {
        return dataRepository.getOffers()
    }
}