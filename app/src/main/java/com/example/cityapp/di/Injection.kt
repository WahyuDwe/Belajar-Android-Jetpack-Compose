package com.example.cityapp.di

import com.example.cityapp.data.CityRepository

object Injection {
    fun provideRepository(): CityRepository {
        return CityRepository.getInstance()
    }
}