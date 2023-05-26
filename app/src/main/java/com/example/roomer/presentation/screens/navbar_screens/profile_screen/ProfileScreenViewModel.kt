package com.example.roomer.presentation.screens.navbar_screens.profile_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.auth_repository.AuthRepositoryInterface
import com.example.roomer.domain.usecase.profile_screen.ProfileScreenUseCase
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    application: Application,
    repository: AuthRepositoryInterface
) : AndroidViewModel(application) {
    private val userToken = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    )
    private val _state = MutableStateFlow(ProfileScreenState())
    val state: StateFlow<ProfileScreenState> = _state.asStateFlow()

    private val profileScreenUseCase = ProfileScreenUseCase(repository)

    fun logout() {
        viewModelScope.launch {
            profileScreenUseCase.logout(userToken!!).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update { currentState ->
                            currentState.copy(
                                error = result.message ?: "",
                                isLoading = false,
                                isLogout = false
                            )
                        }
                    }
                    is Resource.Internet -> {
                        _state.update { currentState ->
                            currentState.copy(
                                error = result.message ?: "",
                                isLoading = false,
                                isLogout = false,
                                internetProblem = true
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update { currentState ->
                            currentState.copy(
                                isLoading = true,
                                isLogout = true
                            )
                        }
                    }
                    is Resource.Success -> {
                        eraseSharedPreferences()
                        _state.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                isLogout = true,
                                success = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun markStateLogout() {
        _state.update { currentState ->
            currentState.copy(
                isLogout = true
            )
        }
    }

    private fun eraseSharedPreferences() {
        SpManager().clearSharedPreference(getApplication<Application>().applicationContext)
    }

    fun clearState() {
        _state.value = ProfileScreenState()
    }
}
