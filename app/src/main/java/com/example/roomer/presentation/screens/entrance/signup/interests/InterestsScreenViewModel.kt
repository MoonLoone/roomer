package com.example.roomer.presentation.screens.entrance.signup.interests

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.signup.interests.InterestModel
import com.example.roomer.domain.usecase.InterestsUseCase
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class InterestsScreenViewModel @Inject constructor(
    application: Application,
    roomerRepository: RoomerRepositoryInterface
) : AndroidViewModel(application) {

    private val _state = mutableStateOf(InterestsScreenState())
    val state: State<InterestsScreenState> = _state

    private val interestsUseCase = InterestsUseCase(roomerRepository)

    private val _interests: MutableState<List<InterestModel>> =
        mutableStateOf(emptyList())
    val interests: State<List<InterestModel>> = _interests

    private val token = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        key = SpManager.Sp.TOKEN,
        ""
    )!!

    init {
        getInterests()
    }

    fun getInterests() {
        viewModelScope.launch {
            interestsUseCase.loadInterests().collect { result ->
                when (result) {

                    is Resource.Loading -> {
                        _state.value = InterestsScreenState(
                            isLoading = true,
                        )
                    }
                    is Resource.Success -> {
                        _state.value = InterestsScreenState(
                            isInterestsLoaded = true
                        )
                        _interests.value = result.data!!
                    }
                    is Resource.Internet -> {
                        _state.value = InterestsScreenState(
                            internetProblem = true,
                            error = result.message!!
                        )
                    }
                    is Resource.Error -> {
                        _state.value = InterestsScreenState(
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }

    fun putInterests(selectedItems: List<InterestModel>) {
        if (selectedItems.isEmpty()) {
            _state.value = InterestsScreenState(
                error = NO_CHOSEN_ERR_MSG,
                isInterestsLoaded = true
            )
            return
        }
        viewModelScope.launch {
            interestsUseCase.putInterests(token, selectedItems).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = InterestsScreenState(
                            isLoading = true,
                            isInterestsLoaded = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = InterestsScreenState(
                            isInterestsLoaded = true,
                            isInterestsSent = true
                        )
                    }
                    is Resource.Internet -> {
                        _state.value = InterestsScreenState(
                            internetProblem = true,
                            error = result.message!!,
                            isInterestsLoaded = true
                        )
                    }
                    is Resource.Error -> {
                        _state.value = InterestsScreenState(
                            error = result.message!!,
                            isInterestsLoaded = true
                        )
                    }
                }
            }
        }
    }

    fun clearState() {
        _state.value = InterestsScreenState(isInterestsLoaded = _state.value.isInterestsLoaded)
    }

    companion object {
        const val NO_CHOSEN_ERR_MSG = "No interests chosen!"
    }
}
