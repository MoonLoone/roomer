package com.example.roomer.presentation.screens.entrance.signup

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.api.RoomerApiObj
import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.utils.Resource
import com.example.roomer.domain.usecase.SignUpUseCase
import com.example.roomer.utils.SpManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpScreenViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val roomerRepository = RoomerRepository(RoomerApiObj.api)
    private val _state = mutableStateOf(SignUpScreenState())

    val state: State<SignUpScreenState> = _state
    val signUpUseCase = SignUpUseCase(roomerRepository)

    fun signUpUser(email: String, password: String, username: String, confPassword: String) {

        if (email.trim().isEmpty() || password.trim().isEmpty() || username.trim().isEmpty()) {
            _state.value =
                SignUpScreenState(error = EMPTY_FIELD_ERR_MSG, isLoading = false)
            return
        } else if (confPassword.trim().isEmpty()) {
            _state.value =
                SignUpScreenState(error = CONF_PASS_ERR_MSG, isLoading = false)
            return
        }

        viewModelScope.launch {
            signUpUseCase(username, email, password, REQUEST_DELAY).collect { result ->
                when (result) {

                    is Resource.Loading -> {
                        _state.value = SignUpScreenState(
                            isLoading = true,
                            internetProblem = false
                        )
                    }
                    is Resource.Success -> {
                        SpManager().setSharedPreference(
                            getApplication<Application>().applicationContext,
                            key = SpManager.Sp.EMAIL,
                            value = email
                        )
                        SpManager().setSharedPreference(
                            getApplication<Application>().applicationContext,
                            key = SpManager.Sp.PASSWORD,
                            value = password
                        )

                        _state.value = SignUpScreenState(
                            isLoading = false,
                            internetProblem = false,
                            success = true,
                        )
                    }
                    is Resource.Internet -> {
                        _state.value = SignUpScreenState(
                            internetProblem = true,
                            isLoading = false,
                            error = result.message!!
                        )
                    }
                    is Resource.Error -> {
                        _state.value = SignUpScreenState(
                            internetProblem = false,
                            isLoading = false,
                            error = result.message!!
                        )
                    }
                    }
                }
            }
        }

    fun clearState() {
        _state.value = SignUpScreenState()
    }

    companion object {
        const val EMPTY_FIELD_ERR_MSG = "Field(s) can't be empty!"
        const val CONF_PASS_ERR_MSG = "You have to confirm password!"
        const val REQUEST_DELAY: Long = 2000
    }
}
