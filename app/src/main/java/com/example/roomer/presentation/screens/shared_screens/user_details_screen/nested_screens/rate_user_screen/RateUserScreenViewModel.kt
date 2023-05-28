package com.example.roomer.presentation.screens.shared_screens.user_details_screen.nested_screens.rate_user_screen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.usecase.shared_screens.RateUserUseCase
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The view model of [RateUserScreen].
 *
 * @author Andrey Karanik
 */

@HiltViewModel
class RateUserScreenViewModel @Inject constructor(
    application: Application,
    val roomerRepository: RoomerRepositoryInterface,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(RateUserScreenState())
    val state: StateFlow<RateUserScreenState> = _state.asStateFlow()

    private val rateUserUseCase = RateUserUseCase(roomerRepository)

    private val userToken = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    )

    var confirmation by mutableStateOf(false)

    var rating by mutableStateOf(0)

    var comment by mutableStateOf("")

    var isAnonymous by mutableStateOf(false)

    var receiverId by mutableStateOf(0)

    init {
        viewModelScope.launch {
            val userString: String? = savedStateHandle["user"]
            val user = Gson().fromJson(userString, User::class.java)
            receiverId = user.userId
        }
    }

    fun clearState() {
        _state.update {
            RateUserScreenState()
        }
    }

    fun showConfirmDialog() {
        if (screenValidate()) {
            confirmation = true
        }
    }

    fun hideConfirmDialog() {
        confirmation = false
    }

    fun sendReview() {
        hideConfirmDialog()
        viewModelScope.async {
            if (userToken != null) {
                val authorId = async { roomerRepository.getLocalCurrentUser() }.await().userId
                rateUserUseCase.sendReview(
                    userToken,
                    authorId,
                    receiverId,
                    rating,
                    isAnonymous,
                    comment
                ).collect {
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
                                    success = true
                                )
                            }
                        }
                        is Resource.Internet -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    internetProblem = true
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    requestProblem = true,
                                    error = it.message!!
                                )
                            }
                        }
                    }
                }
            } else {
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = Constants.UseCase.tokenNotFoundErrorMessage
                    )
                }
            }
        }
    }

    private fun screenValidate(): Boolean {
        if (rating == 0) {
            _state.update { currentState ->
                currentState.copy(ratingNotSpecified = true)
            }
            return false
        }

        if (comment.isEmpty()) {
            _state.update { currentState ->
                currentState.copy(commentIsEmpty = true)
            }
            return false
        }

        return true
    }
}
