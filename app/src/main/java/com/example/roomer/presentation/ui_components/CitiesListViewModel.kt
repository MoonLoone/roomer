package com.example.roomer.presentation.ui_components

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.shared.cities_list.CitiesListInterface
import com.example.roomer.domain.model.city.CityModel
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesListViewModel @Inject constructor(
    application: Application,
    private val repository: CitiesListInterface
) : AndroidViewModel(application) {

    private val _cities: MutableState<List<CityModel>> =
        mutableStateOf(emptyList())
    val cities: State<List<CityModel>> = _cities

    private val token = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        key = SpManager.Sp.TOKEN,
        null
    ) ?: ""

    init {
        loadCities()
    }

    private fun loadCities() {
        viewModelScope.launch {
            repository.getCities(token).body()?.let {
                _cities.value = it
            }
        }
    }

}