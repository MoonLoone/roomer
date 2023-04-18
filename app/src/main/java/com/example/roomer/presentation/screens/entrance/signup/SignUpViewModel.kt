package com.example.roomer.presentation.screens.entrance.signup

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.roomer.domain.model.login_sign_up.InterestModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var birthDate by mutableStateOf("2022-01-27")
    var sex by mutableStateOf("M")
    var avatar by mutableStateOf<Bitmap?>(null)
    var personDescription by mutableStateOf("")
    var employment by mutableStateOf("E")
    var sleepTime by mutableStateOf("N")
    var alcoholAttitude by mutableStateOf("I")
    var smokingAttitude by mutableStateOf("I")
    var personalityType by mutableStateOf("E")
    var cleanHabits by mutableStateOf("N")
    var interests by mutableStateOf<List<InterestModel>>(emptyList())

    fun aboutMeAvatarScreenValidate() {
        if (avatar == null) {
            _uiState.update { currentState ->
                currentState.copy(isError = true, errorMessage = EMPTY_AVATAR_ERROR_MESSAGE)
            }
        } else if (personDescription.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(isError = true, errorMessage = EMPTY_FIELDS_ERROR_MESSAGE)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(isValid = true)
            }
        }
    }

    fun primaryUserInfoPageValidate() {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(isError = true, errorMessage = EMPTY_FIELDS_ERROR_MESSAGE)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(isValid = true)
            }
        }
    }

    fun clearState() {
        _uiState.update {
            SignUpState()
        }
    }

    fun clearError() {
        _uiState.update { currentState ->
            currentState.copy(isError = false, errorMessage = "")
        }
    }

    companion object {
        const val EMPTY_AVATAR_ERROR_MESSAGE = "Can't proceed with no avatar!"
        const val EMPTY_FIELDS_ERROR_MESSAGE = "Can't proceed with one or more fields being empty!"
    }
}
