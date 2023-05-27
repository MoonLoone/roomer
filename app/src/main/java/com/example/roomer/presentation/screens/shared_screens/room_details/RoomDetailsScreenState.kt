package com.example.roomer.presentation.screens.shared_screens.room_details

import androidx.compose.ui.unit.Constraints
import com.example.roomer.utils.Constants
import com.example.roomer.utils.ScreenState

data class RoomDetailsScreenState(
    override val success: Boolean = false,
    override val isLoading: Boolean = false,
    override val error: String = Constants.RoomDetails.ERROR_MSG,
    override val internetProblem: Boolean = false,
    val isFavourite: Boolean = false,
) : ScreenState