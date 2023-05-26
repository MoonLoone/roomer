package com.example.roomer.presentation.screens.profile_nested_screens.account_screen

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.auth_repository.AuthRepositoryInterface
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.example.roomer.presentation.screens.entrance.signup.SignUpState
import com.example.roomer.presentation.screens.entrance.signup.SignUpViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    application: Application,
    repositoryAuth: AuthRepositoryInterface,
    repositoryRoomer: RoomerRepositoryInterface
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(AccountScreenState())
    val uiState: StateFlow<AccountScreenState> = _uiState.asStateFlow()

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var sex by mutableStateOf("M")
    var personDescription by mutableStateOf("")
    var employment by mutableStateOf("E")
    var sleepTime by mutableStateOf("N")
    var alcoholAttitude by mutableStateOf("I")
    var smokingAttitude by mutableStateOf("I")
    var personalityType by mutableStateOf("M")
    var cleanHabits by mutableStateOf("N")
    var interests by mutableStateOf<List<InterestModel>>(emptyList())
    var city by mutableStateOf("")

    init {
        viewModelScope.launch {
            setStoredFieldValues(repositoryRoomer.getLocalCurrentUser())
        }
    }

    private fun setStoredFieldValues(user: User) {
        firstName = user.firstName
        lastName = user.lastName
        birthDate = user.birthDate ?: ""
        sex = user.sex
        personDescription = user.aboutMe ?: ""
        employment = user.employment
        sleepTime = user.sleepTime
        alcoholAttitude = user.alcoholAttitude
        smokingAttitude = user.smokingAttitude
        personalityType = user.personalityType
        cleanHabits = user.cleanHabits
        interests = user.interests ?: emptyList()
        city = user.city ?: ""
    }
}
