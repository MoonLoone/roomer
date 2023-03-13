package com.example.roomer.presentation.screens.entrance.signup.screen_one

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.RoomerRepositoryInterface
import com.example.roomer.domain.usecase.signup.SignUpOneUseCase
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpScreenOneViewModel @Inject constructor(
    application: Application,
    roomerRepository: RoomerRepositoryInterface
) : AndroidViewModel(application) {
    private val _state = mutableStateOf(SignUpOneState())

    val state: State<SignUpOneState> = _state
    private val signUpOneUseCase = SignUpOneUseCase(roomerRepository)

    private val token = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        key = SpManager.Sp.TOKEN,
        ""
    )!!

    fun applyData(
        firstName: String,
        lastName: String,
        sex: String,
        birthDate: String
    ) {
        viewModelScope.launch {
            signUpOneUseCase(token, firstName, lastName, sex, birthDate).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = SignUpOneState(
                            success = true
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = SignUpOneState(
                            isLoading = true
                        )
                    }
                    is Resource.Internet -> {
                        _state.value = SignUpOneState(
                            internetProblem = true,
                            error = result.message!!
                        )
                    }
                    is Resource.Error -> {
                        _state.value = SignUpOneState(
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }
    fun clearState() {
        _state.value = SignUpOneState()
    }
}
