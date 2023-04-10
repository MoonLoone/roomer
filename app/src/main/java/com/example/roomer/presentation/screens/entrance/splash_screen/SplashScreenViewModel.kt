package com.example.roomer.presentation.screens.entrance.splash_screen

import android.app.Application
import android.os.SystemClock.sleep
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.usecase.login_sign_up.SplashScreenUseCase
import com.example.roomer.presentation.screens.UsualScreenState
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
class SplashScreenViewModel @Inject constructor(
    application: Application,
    private val roomerRepository: RoomerRepositoryInterface
) : AndroidViewModel(application) {
    private val userToken = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    )
    private val _state = MutableStateFlow(UsualScreenState())
    val state: StateFlow<UsualScreenState> = _state.asStateFlow()

    private val _currentUser: MutableStateFlow<User?> = MutableStateFlow(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val splashScreenUseCase = SplashScreenUseCase(roomerRepository)

    init {
        if (userToken == null)
            _state.update { currentState ->
                currentState.copy(isError = true)
            }
        else verifyToken()
    }

    private fun storeCurrentUser(user: User) {
        viewModelScope.launch {
            roomerRepository.addLocalUser(user)
        }
    }

    fun readCurrentUser() {
        viewModelScope.launch {
            _currentUser.value = roomerRepository.getLocalCurrentUser()
        }
        Log.d("CURRENT_USER", currentUser.value.toString())
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
                        val currentUser = result.data?.copy(isCurrentUser = true)
                        currentUser?.let {
                            storeCurrentUser(currentUser)
                        }
                        _state.update { currentState ->
                            currentState.copy(isLoading = false, isSuccess = true)
                        }
                    }
                    is Resource.Internet -> {
                        _state.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                isInternetProblem = true,
                                errorMessage = result.message!!
                            )
                        }
                    }
                    is Resource.Error -> {
                        eraseSharedPreferences()
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
            SpManager.Sp.TOKEN,
        )
        SpManager().removeSharedPreference(
            getApplication<Application>().applicationContext,
            SpManager.Sp.EMAIL,
        )
        SpManager().removeSharedPreference(
            getApplication<Application>().applicationContext,
            SpManager.Sp.PASSWORD,
        )
    }

    fun clearState() {
        _state.value = UsualScreenState()
    }
}
