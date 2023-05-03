package com.example.roomer.presentation.screens.navbar_screens.post_screen.add_housing_screen

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.usecase.navbar_screens.AddHousingUseCase
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The view model of [AddHousingScreen].
 *
 * @author Andrey Karanik
 */

@HiltViewModel
class AddHousingScreenViewModel @Inject constructor(
    application: Application,
    roomerRepository: RoomerRepositoryInterface
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(AddHousingState())
    val state: StateFlow<AddHousingState> = _state.asStateFlow()

    private val addHousingUseCase = AddHousingUseCase(roomerRepository)

    private val userToken = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    )

    private var host : Int = 0

    init {
        viewModelScope.launch {
            host = roomerRepository.getLocalCurrentUser().userId
        }
    }

    var roomImages = mutableStateListOf<Bitmap>()

    var monthPrice by mutableStateOf("")

    var description by mutableStateOf("")

    var bedroomsCount by mutableStateOf("1")

    var bathroomsCount by mutableStateOf("1")

    var apartmentType by mutableStateOf("F")

    var sharingType by mutableStateOf("P")

    var postConfirmation by mutableStateOf(false)

    fun clearState() {
        _state.update { currentState ->
            currentState.copy(
                isSuccess = false,
                isLoading = false,
                isInternetProblem = false,
                isError = false,
                errorMessage = ""
            )
        }
    }

    fun showConfirmDialog() {
        postConfirmation = true
    }

    fun hideConfirmDialog() {
        postConfirmation = false
    }

    fun postAdvertisement() {
        hideConfirmDialog()
        viewModelScope.async {
            if (userToken != null) {
                addHousingUseCase.putRoomData(
                    userToken,
                    roomImages,
                    monthPrice,
                    host,
                    description,
                    bedroomsCount,
                    bathroomsCount,
                    apartmentType,
                    sharingType
                ).collect {
                    when (it) {
                        is Resource.Loading -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = true
                                )
                            }
                        }
                        is Resource.Success -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    isSuccess = true,
                                )
                            }
                        }
                        is Resource.Internet -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    isError = true,
                                    errorMessage = it.message!!
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    isError = true,
                                    errorMessage = it.message!!
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}