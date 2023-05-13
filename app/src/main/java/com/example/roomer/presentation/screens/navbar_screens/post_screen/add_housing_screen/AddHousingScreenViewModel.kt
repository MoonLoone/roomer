package com.example.roomer.presentation.screens.navbar_screens.post_screen.add_housing_screen

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.usecase.navbar_screens.AddHousingUseCase
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The view model of [AddHousingScreen].
 *
 * @author Andrey Karanik
 */

@HiltViewModel
class AddHousingScreenViewModel @Inject constructor(
    application: Application,
    val roomerRepository: RoomerRepositoryInterface,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(AddHousingState())
    val state: StateFlow<AddHousingState> = _state.asStateFlow()

    private val addHousingUseCase = AddHousingUseCase(roomerRepository)

    private val userToken = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    )

    var postConfirmation by mutableStateOf(false)

    var roomImages = mutableStateListOf<Bitmap>()

    var monthPrice by mutableStateOf("")

    var description by mutableStateOf("")

    var bedroomsCount by mutableStateOf("1")

    var bathroomsCount by mutableStateOf("1")

    var apartmentType by mutableStateOf("F")

    var sharingType by mutableStateOf("P")

    var roomId by mutableStateOf(0)

    var photosRemoved by mutableStateOf(false)


    init {
        viewModelScope.launch {
            val roomString: String? = savedStateHandle["room"]
            val room = Gson().fromJson(roomString, Room::class.java)
            room?.let {
                monthPrice = it.monthPrice.toString()
                description = it.description
                bedroomsCount = it.bedroomsCount.toString()
                bathroomsCount = it.bathroomsCount.toString()
                apartmentType = it.housingType
                sharingType = it.sharingType
                roomId = it.id
            }
            if (room == null) {
                photosRemoved = true
            }
        }
    }

    fun clearState() {
        _state.update {
            AddHousingState()
        }
    }

    fun showConfirmDialog() {
        if (screenValidate()) {
            postConfirmation = true
        }
    }

    fun hideConfirmDialog() {
        postConfirmation = false
    }

    fun putAdvertisement() {
        hideConfirmDialog()
        viewModelScope.async {
            if (userToken != null) {
                addHousingUseCase.putRoomData(
                    userToken,
                    photosRemoved,
                    roomId,
                    roomImages,
                    monthPrice,
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
                                    success = true
                                )
                            }
                        }
                        is Resource.Internet -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    internetProblem = true
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    requestProblem = true,
                                    error = it.message!!
                                )
                            }
                        }
                    }
                }
            } else {
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = "Token not found"
                    )
                }
            }
        }
    }

    fun postAdvertisement() {
        hideConfirmDialog()
        viewModelScope.async {
            val host = async { roomerRepository.getLocalCurrentUser() }.await().userId
            if (userToken != null) {
                addHousingUseCase.postRoomData(
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
                                    success = true
                                )
                            }
                        }
                        is Resource.Internet -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    internetProblem = true
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    requestProblem = true,
                                    error = it.message!!
                                )
                            }
                        }
                    }
                }
            } else {
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = "Token not found"
                    )
                }
            }
        }
    }

    fun screenValidate(): Boolean {
        try {
            if (monthPrice.toInt() < 0) {
                _state.update { currentState ->
                    currentState.copy(monthPriceIsNotPositive = true)
                }
                return false
            }
        } catch (e: NumberFormatException) {
            _state.update { currentState ->
                currentState.copy(monthPriceIsNotInteger = true)
            }
            return false
        }

        if (roomImages.isEmpty() && photosRemoved) {
            _state.update { currentState ->
                currentState.copy(roomImagesIsEmpty = true)
            }
            return false
        }

        if (description.isEmpty()) {
            _state.update { currentState ->
                currentState.copy(descriptionIsEmpty = true)
            }
            return false
        }

        return true
    }
}
