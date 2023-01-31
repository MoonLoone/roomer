package com.example.roomer.presentation.screens.entrance.signup.screen_three

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.api.RoomerApiObj
import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.domain.usecase.signup.SignUpThreeUseCase
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import kotlinx.coroutines.launch

class SignUpScreenThreeViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val roomerRepository = RoomerRepository(RoomerApiObj.api)
    private val _state = mutableStateOf(SignUpThreeState())

    val state: State<SignUpThreeState> = _state
    private val signUpThreeUseCase = SignUpThreeUseCase(roomerRepository)

    private val token = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        key = SpManager.Sp.TOKEN,
        ""
    )!!

    fun applyData(
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String
    ) {
        viewModelScope.launch {
            signUpThreeUseCase(
                token,
                sleepTime,
                alcoholAttitude,
                smokingAttitude,
                personalityType,
                cleanHabits
            ).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.value = SignUpThreeState(
                            success = true
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = SignUpThreeState(
                            isLoading = true
                        )
                    }
                    is Resource.Internet -> {
                        _state.value = SignUpThreeState(
                            internetProblem = true,
                            error = result.message!!
                        )
                    }
                    is Resource.Error -> {
                        _state.value = SignUpThreeState(
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }
    fun clearState() {
        _state.value = SignUpThreeState()
    }
}