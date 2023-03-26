package com.example.roomer.presentation.screens.entrance.signup.sign_up_primary_screen

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.AuthRepositoryInterface
import com.example.roomer.domain.usecase.login_sign_up.PrimarySignUpUseCase
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    application: Application,
    roomerRepository: AuthRepositoryInterface
) : AndroidViewModel(application) {

    private val _state = mutableStateOf(SignUpScreenState())

    val state: State<SignUpScreenState> = _state
    val signUpUseCase = PrimarySignUpUseCase(roomerRepository)

    fun signUpUser(email: String, password: String, username: String, confPassword: String) {

        if (
            email.trim().isEmpty() ||
            password.trim().isEmpty() ||
            username.trim().isEmpty() ||
            confPassword.trim().isEmpty()
        ) {
            _state.value =
                SignUpScreenState(error = EMPTY_FIELD_ERR_MSG)
            return
        } else if (confPassword != password) {
            _state.value =
                SignUpScreenState(
                    error = CONF_PASS_ERR_MSG,
                    isConfPasswordError = true
                )
            return
        }

        viewModelScope.launch {
            signUpUseCase(username, email, password).collect { result ->
                when (result) {

                    is Resource.Loading -> {
                        _state.value = SignUpScreenState(
                            isLoading = true,
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
                            success = true,
                        )
                    }
                    is Resource.Internet -> {
                        _state.value = SignUpScreenState(
                            internetProblem = true,
                            error = result.message!!
                        )
                    }
                    is Resource.Error -> {
                        when (result) {
                            is Resource.Error.EmailError -> {
                                _state.value = SignUpScreenState(
                                    error = result.message!!,
                                    isEmailError = true
                                )
                            }
                            is Resource.Error.UsernameError -> {
                                _state.value = SignUpScreenState(
                                    error = result.message!!,
                                    isUsernameError = true
                                )
                            }
                            is Resource.Error.PasswordError -> {
                                _state.value = SignUpScreenState(
                                    error = result.message!!,
                                    isPasswordError = true
                                )
                            }
                            else -> {
                                _state.value = SignUpScreenState(
                                    error = result.message!!
                                )
                            }
                        }
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
        const val CONF_PASS_ERR_MSG = "Passwords aren't matching"
    }
}
