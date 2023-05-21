package com.example.roomer.presentation.screens.shared_screens.user_details_screen.comment_screen

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.roomer.R
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.comment.UserReview
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.usecase.shared_screens.CommentScreenUseCase
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
import kotlin.math.roundToInt

/**
 * The view model of [CommentScreen].
 *
 * @author Andrey Karanik
 */

@HiltViewModel
class CommentScreenViewModel @Inject constructor(
    application: Application,
    val roomerRepository: RoomerRepositoryInterface,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(CommentScreenState())
    val state: StateFlow<CommentScreenState> = _state.asStateFlow()

    private val commentScreenUseCase = CommentScreenUseCase(roomerRepository)

    private val userToken = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    )

    private val _reviews: MutableState<List<UserReview>> =
        mutableStateOf(emptyList())
    val reviews: State<List<UserReview>> = _reviews

    init {
        getReviews()
    }
    fun clearState() {
        _state.update {
            CommentScreenState()
        }
    }

    fun getStarRating(): Float {
        var sum = 0.0f
        reviews.value.forEach {
            sum += it.score
        }

        return sum / reviews.value.size
    }

    fun getStarRatingAsString(): String {
        return String.format("%.1f", getStarRating())
    }

    fun getStarRatingAsCount(): Int {
        return getStarRating().roundToInt()
    }

    fun getReviews() {
        viewModelScope.async {
            if (userToken != null) {
                val userString: String? = savedStateHandle["user"]
                val user = Gson().fromJson(userString, User::class.java)
                val receiverId = user.userId
                commentScreenUseCase.getReviews(
                    userToken,
                    receiverId
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
                            _reviews.value = it.data!!
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
}
