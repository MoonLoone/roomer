package com.example.roomer.presentation.screens.entrance.signup.interests_screen

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.AuthRepositoryInterface
import com.example.roomer.domain.model.login_sign_up.interests.InterestModel
import com.example.roomer.domain.usecase.login_sign_up.SignUpUseCase
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class InterestsScreenViewModel @Inject constructor(
    application: Application,
    roomerRepository: AuthRepositoryInterface
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(InterestsScreenState())
    private val signUpUseCase = SignUpUseCase(roomerRepository)

    val state: StateFlow<InterestsScreenState> = _state.asStateFlow()

    private val _interests: MutableState<List<InterestModel>> =
        mutableStateOf(emptyList())
    val interests: State<List<InterestModel>> = _interests

    private val token = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        key = SpManager.Sp.TOKEN,
        null
    ) ?: ""

    init {
        getInterests()
    }

    fun getInterests() {
        viewModelScope.launch {
            signUpUseCase.loadInterests().collect { result ->
                when (result) {

                    is Resource.Loading -> {
                        _state.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _state.update { currentState ->
                            currentState.copy(isLoading = false, isInterestsLoaded = true)
                        }
                        _interests.value = result.data!!
                    }
                    is Resource.Internet -> {
                        _state.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                internetProblem = true,
                                error = result.message!!
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update { currentState ->
                            currentState.copy(isLoading = false, error = result.message!!)
                        }
                    }
                }
            }
        }
    }

    fun putSignUpData(
        firstName: String,
        lastName: String,
        sex: String,
        birthDate: String,
        avatar: Bitmap,
        aboutMe: String,
        employment: String,
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String,
        interests: List<InterestModel>
    ) {
        if (interests.isEmpty()) {
            _state.update { currentState ->
                currentState.copy(error = NO_CHOSEN_ERR_MSG)
            }
            return
        }
        viewModelScope.launch {
            signUpUseCase.putSignUpData(
                token,
                firstName,
                lastName,
                sex,
                birthDate,
                avatar,
                aboutMe,
                employment,
                sleepTime,
                alcoholAttitude,
                smokingAttitude,
                personalityType,
                cleanHabits,
                interests
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = InterestsScreenState(
                            isLoading = true,
                            isInterestsLoaded = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = InterestsScreenState(
                            isInterestsLoaded = true,
                            isInterestsSent = true
                        )
                    }
                    is Resource.Internet -> {
                        _state.value = InterestsScreenState(
                            internetProblem = true,
                            error = result.message!!,
                            isInterestsLoaded = true
                        )
                    }
                    is Resource.Error -> {
                        _state.value = InterestsScreenState(
                            error = result.message!!,
                            isInterestsLoaded = true
                        )
                    }
                }
            }
        }
    }

    fun clearState() {
        _state.value = InterestsScreenState(isInterestsLoaded = _state.value.isInterestsLoaded)
    }

    companion object {
        const val NO_CHOSEN_ERR_MSG = "No interests chosen!"
    }
}
