package com.example.roomer.presentation.screens.profile_nested_screens.account_screen

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
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
import com.example.roomer.domain.usecase.account.AccountUseCase
import com.example.roomer.domain.usecase.login_sign_up.SignUpUseCase
import com.example.roomer.presentation.screens.entrance.signup.SignUpState
import com.example.roomer.presentation.screens.entrance.signup.SignUpViewModel
import com.example.roomer.presentation.screens.entrance.signup.interests_screen.InterestsScreenState
import com.example.roomer.presentation.screens.entrance.signup.interests_screen.InterestsScreenViewModel
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    application: Application,
    repositoryAuth: AuthRepositoryInterface,
    private val repositoryRoomer: RoomerRepositoryInterface
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(AccountScreenState())
    val uiState: StateFlow<AccountScreenState> = _uiState.asStateFlow()

    private val useCase = AccountUseCase(repositoryAuth)

    private val token = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        key = SpManager.Sp.TOKEN,
        null
    ) ?: ""

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
        Log.d("INTER", interests.toString())
        city = user.city ?: ""
    }

    private fun areFieldsValid(): Boolean {
        if (firstName.isEmpty() || lastName.isEmpty() || personDescription.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(error = SignUpViewModel.EMPTY_FIELDS_ERROR_MESSAGE)
            }
            return false
        }
        return true
    }

    fun clearState() {
        _uiState.value = AccountScreenState()
    }

    fun updateData() {
        if (!areFieldsValid()) return
        viewModelScope.launch {
            useCase.putProfileData(
                token,
                firstName,
                lastName,
                sex,
                birthDate,
                personDescription,
                employment,
                sleepTime,
                alcoholAttitude,
                smokingAttitude,
                personalityType,
                cleanHabits,
                interests,
                city
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let {
                            repositoryRoomer.addLocalCurrentUser(it)
                        }
                        _uiState.update { currentState ->
                            currentState.copy(
                                success = true,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Internet -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                error = result.message ?: "",
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                error = result.message ?: "",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}
