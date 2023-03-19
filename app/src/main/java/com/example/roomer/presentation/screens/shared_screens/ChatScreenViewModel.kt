package com.example.roomer.presentation.screens.shared_screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.roomer.data.repository.AuthRepositoryInterface
import javax.inject.Inject

class ChatScreenViewModel @Inject constructor(
    application: Application,
    authRepository: AuthRepositoryInterface
) : AndroidViewModel(application)
