package com.example.cityapp.data

import com.example.cityapp.model.City
import com.example.cityapp.model.FakeCityDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CityRepository {
    private val cities = mutableListOf<City>()

    init {
        if (cities.isEmpty()) {
            FakeCityDataSource.cities.forEach {
                cities.add(City(it.id, it.name, it.description, it.photo))
            }
        }
    }

    fun getAllCities(): Flow<List<City>> {
        return flowOf(cities)
    }

    fun getCityById(cityId: Long): City {
        return cities.first {
            it.id == cityId
        }
    }

    companion object {
        @Volatile
        private var instance: CityRepository? = null

        fun getInstance(): CityRepository =
            instance ?: synchronized(this) {
                CityRepository().apply {
                    instance = this
                }
            }
    }
}