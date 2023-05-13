package com.example.roomer.data.shared.cities_list

import com.example.roomer.domain.model.city.CityModel
import retrofit2.Response

interface CitiesListInterface {
    suspend fun getCities(token: String): Response<List<CityModel>>
}