package com.example.hhrutest.data.entities

data class Vacancy(
    val address: Address,
    val appliedNumber: Int,
    val company: String,
    val description: String,
    val experience: Experience,
    val id: String,
    val isFavorite: Boolean,
    val lookingNumber: Int,
    val publishedDate: String,
    val questions: List<String>,
    val responsibilities: String,
    val salary: Salary,
    val schedules: List<String>,
    val title: String
)