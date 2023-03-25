package com.example.roomer.presentation.screens.entrance.signup.screen_two

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.AuthRepositoryInterface
import com.example.roomer.domain.usecase.signup.SignUpTwoUseCase
import com.example.roomer.presentation.screens.UsualScreenState
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpScreenTwoViewModel @Inject constructor(
    application: Application,
    roomerRepository: AuthRepositoryInterface
) : AndroidViewModel(application) {
    private val _state = mutableStateOf(UsualScreenState())

    val state: State<UsualScreenState> = _state
    private val signUpTwoUseCase = SignUpTwoUseCase(roomerRepository)

    private val token = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        key = SpManager.Sp.TOKEN,
        ""
    )!!

    fun applyData(
        avatar: Bitmap?,
        aboutMe: String,
        employment: String
    ) {
        if (aboutMe.isEmpty()) {
            _state.value = UsualScreenState(
                error = "Can't proceed with about me field empty"
            )
            return
        } else if (avatar == null) {
            _state.value = UsualScreenState(
                error = "Can't proceed with no uploaded profile picture"
            )
            return
        }
        viewModelScope.launch {
            signUpTwoUseCase(token, avatar, aboutMe, employment).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = UsualScreenState(
                            success = true
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = UsualScreenState(
                            isLoading = true
                        )
                    }
                    is Resource.Internet -> {
                        _state.value = UsualScreenState(
                            internetProblem = true,
                            error = result.message!!
                        )
                    }
                    is Resource.Error -> {
                        _state.value = UsualScreenState(
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }
    fun clearState() {
        _state.value = UsualScreenState()
    }
}
