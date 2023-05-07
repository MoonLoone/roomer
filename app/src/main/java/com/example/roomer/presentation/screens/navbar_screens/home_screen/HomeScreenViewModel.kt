package com.example.roomer.presentation.screens.navbar_screens.home_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.HistoryItem
import com.example.roomer.data.shared.HousingLikeInterface
import com.example.roomer.domain.model.entities.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface,
    val housingLike: HousingLikeInterface
) : ViewModel() {

    private val _state: MutableState<HomeScreenState> = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state
    private val _history: MutableStateFlow<List<HistoryItem>> = MutableStateFlow(emptyList())
    val history: StateFlow<List<HistoryItem>> = _history

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _history.value = roomerRepository.getHistory()
            history.value.forEach { Log.d("!!!", it.user?.userId?.toString()?:"") }
        }
    }

}
