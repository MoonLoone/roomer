package com.example.roomer.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.api.RoomerApiObj
import com.example.roomer.api.repository.RoomerRepository
import com.example.roomer.api.usecase.LoginUseCase
import com.example.roomer.api.utils.Resource
import com.example.roomer.state.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val roomerRepository = RoomerRepository(RoomerApiObj.api)
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state
    val loginUseCase = LoginUseCase(roomerRepository)

//    var job: Job? = null

    fun getUserLogin(email: String, password: String) {

        if (email.trim()
                .isEmpty() && password.trim().isEmpty()
        ) {
            _state.value =
                LoginState(error = "Values can't be empty!", isLoading = false)
            return
        }

//        job?.cancel()

//        job = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                when (result) {

                    is Resource.Loading -> {
                        _state.value = LoginState(
                            isLoading = true,
                            internetProblem = false
                        )
                    }
                    is Resource.Success -> {
                        Log.d("TOKEN", "${result.data}")
                        _state.value = LoginState(
                            isLoading = false,
                            internetProblem = false,
                            success = 1,
                        )
                        /*TODO Shared preferences saving*/
                    }
                    is Resource.Internet -> {
                        delay(200)
                        _state.value = LoginState(
                            internetProblem = true,
                            isLoading = false,
                            error = "No internet connection"
                        )
                    }
                    is Resource.Error -> {
                        delay(200)
                        _state.value = LoginState(
                            error = result.message ?: "An unexpected error occurred",
                            isLoading = false,
                            internetProblem = false
                        )
                    }
                }
            }
//            loginUseCase(email, password).onEach { result ->
//
//                when (result) {
//
//                    is Resource.Loading -> {
//                        _state.value = LoginState(
//                            isLoading = true,
//                            internetProblem = false
//                        )
//                    }
//                    is Resource.Success -> {
//                        Log.d("TOKEN", "${result.data}")
//                        _state.value = LoginState(
//                            isLoading = false,
//                            internetProblem = false,
//                            success = 1,
//                        )
//                        /*TODO Shared preferences saving*/
//                    }
//
//                    is Resource.Internet -> {
//                        delay(200)
//                        _state.value = LoginState(
//                            internetProblem = true,
//                            isLoading = false,
//                            error = "No internet connection"
//                        )
//                    }
//
//                    is Resource.Error -> {
//                        delay(200)
//                        _state.value = LoginState(
//                            error = result.message ?: "An unexpected error occurred",
//                            isLoading = false,
//                            internetProblem = false
//                        )
//                    }
//                }
//            }.launchIn(viewModelScope)
        }
    }

    fun clearViewModel() {
        state.value.internetProblem = false
        state.value.isLoading = false
        state.value.success = -1
        state.value.error = ""
    }
}