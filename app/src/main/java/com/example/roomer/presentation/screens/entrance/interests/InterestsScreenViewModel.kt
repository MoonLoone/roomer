package com.example.roomer.presentation.screens.entrance.interests

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.api.RoomerApiObj
import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.domain.model.interests.InterestModel
import com.example.roomer.domain.usecase.InterestsUseCase
import com.example.roomer.utils.Resource
import com.example.roomer.domain.usecase.SignUpUseCase
import com.example.roomer.utils.SpManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class InterestsScreenViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val roomerRepository = RoomerRepository(RoomerApiObj.api)
    private val _state = mutableStateOf(InterestsScreenState())

    val state: State<InterestsScreenState> = _state
    private val interestsUseCase = InterestsUseCase(roomerRepository)
    private val _interests: MutableState<List<InterestModel>> =
        mutableStateOf(emptyList())
    val interests: State<List<InterestModel>> = _interests

    init {
        getInterests()
    }

    fun getInterests() {
        viewModelScope.launch {
            interestsUseCase.loadInterests(REQUEST_DELAY).collect { result ->
                when (result) {

                    is Resource.Loading -> {
                        _state.value = InterestsScreenState(
                            isLoading = true,
                            internetProblem = false
                        )
                    }
                    is Resource.Success -> {

                        _state.value = InterestsScreenState(
                            isLoading = false,
                            internetProblem = false,
                            isInterestsLoaded = true
                        )
                        _interests.value = result.data!!
                    }
                    is Resource.Internet -> {
                        _state.value = InterestsScreenState(
                            internetProblem = true,
                            isLoading = false,
                            error = result.message!!
                        )
                    }
                    is Resource.Error -> {
                        _state.value = InterestsScreenState(
                            internetProblem = false,
                            isLoading = false,
                            error = result.message!!
                        )
                    }
                    else -> {
                        _state.value = InterestsScreenState(
                            internetProblem = false,
                            isLoading = false,
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }

    fun setInterests(selectedItems: List<InterestModel>) {
        if (selectedItems.isEmpty()) {
            _state.value = InterestsScreenState(
                            isLoading = false,
                            internetProblem = false,
                            success = true,
                            error = NO_CHOSEN_ERR_MSG
                        )
            return
        }
    }

    fun clearState() {
        _state.value = InterestsScreenState(isInterestsLoaded = _state.value.isInterestsLoaded)
    }

    companion object {
        const val NO_CHOSEN_ERR_MSG = "No interests chosen!"
        const val REQUEST_DELAY: Long = 2000
    }
}
