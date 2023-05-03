package com.example.roomer.presentation.screens.navbar_screens.post_screen

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.usecase.navbar_screens.PostUseCase
import com.example.roomer.presentation.screens.UsualScreenState
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The view model of [PostScreen].
 *
 * @author Andrey Karanik
 */

@HiltViewModel
class PostScreenViewModel @Inject constructor(
    application: Application,
    roomerRepository: RoomerRepositoryInterface
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(UsualScreenState())
    val state: StateFlow<UsualScreenState> = _state.asStateFlow()

    private val postUseCase = PostUseCase(roomerRepository)

    private val userToken = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    )

    private var host : Int = 0

    private val _advertisements: MutableState<List<Room>> =
        mutableStateOf(emptyList())
    val advertisements: State<List<Room>> = _advertisements

    init {
        viewModelScope.launch {
            host = roomerRepository.getLocalCurrentUser().userId
            getAdvertisements()
        }
    }

    fun clearState() {
        _state.update { currentState ->
            currentState.copy(
                isSuccess = false,
                isLoading = false,
                isInternetProblem = false,
                isError = false,
                errorMessage = ""
            )
        }
    }

    fun getAdvertisements() {
        viewModelScope.launch {
            if (userToken != null) {
                postUseCase.getCurrentUserRoomData(userToken, host).collect {
                    when (it) {
                        is Resource.Loading -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = true
                                )
                            }
                        }
                        is Resource.Success -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    isSuccess = true,
                                )
                            }
                            _advertisements.value = it.data!!
                        }
                        is Resource.Internet -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    isError = true,
                                    errorMessage = it.message!!
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    isError = true,
                                    errorMessage = it.message!!
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}