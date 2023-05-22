package com.example.roomer.presentation.screens.entrance.splash_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.usecase.login_sign_up.SplashScreenUseCase
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    application: Application,
    private val roomerRepository: RoomerRepositoryInterface
) : AndroidViewModel(application) {
    private val userToken = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    )
    private val isSignUpNotFinished = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.SIGN_UP_NOT_FINISHED,
        null
    )
    private val _state = MutableStateFlow(SplashScreenState())
    val state: StateFlow<SplashScreenState> = _state.asStateFlow()

    private val splashScreenUseCase = SplashScreenUseCase(roomerRepository)

    init {
        if (userToken == null) {
            _state.update { currentState ->
                currentState.copy(isError = true)
            }
        } else {
            verifyToken()
        }
    }

    private fun clearCurrentUser() {
        viewModelScope.launch {
            roomerRepository.deleteLocalCurrentUser()
        }
    }

    fun verifyToken() {
        viewModelScope.launch {
            splashScreenUseCase(userToken!!).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        if (isSignUpNotFinished.isNullOrEmpty()) {
                            result.data?.let {
                                roomerRepository.addLocalCurrentUser(it)
                            }
                            _state.update { currentState ->
                                currentState.copy(isLoading = false, success = true)
                            }
                        } else {
                            _state.update { currentState ->
                                currentState.copy(isSignUpNotFinished = true)
                            }
                        }
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
                        eraseSharedPreferences()
                        clearCurrentUser()
                        _state.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                    }
                }
            }
        }
    }
    private fun eraseSharedPreferences() {
        SpManager().removeSharedPreference(
            getApplication<Application>().applicationContext,
            SpManager.Sp.TOKEN
        )
        SpManager().removeSharedPreference(
            getApplication<Application>().applicationContext,
            SpManager.Sp.EMAIL
        )
        SpManager().removeSharedPreference(
            getApplication<Application>().applicationContext,
            SpManager.Sp.PASSWORD
        )
    }

    fun clearState() {
        _state.value = SplashScreenState()
    }
}
