package com.example.roomer.presentation.screens.shared_screens

import androidx.lifecycle.ViewModel
import com.example.roomer.data.shared.AddToHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailsScreenViewModel @Inject constructor(
    private val addToHistory: AddToHistory
) : ViewModel() {
    init {

    }
}