package com.example.roomer.data.shared.cities_list

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.city.CityModel
import retrofit2.Response

class CitiesList(
    private val roomerRepositoryInterface: RoomerRepositoryInterface,
) : CitiesListInterface {
    override suspend fun getCities(token: String): Response<List<CityModel>> {
        return roomerRepositoryInterface.getCities(token)
    }
}