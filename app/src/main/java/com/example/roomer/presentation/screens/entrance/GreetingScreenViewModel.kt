package com.example.roomer.presentation.screens.entrance

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.roomer.utils.SpManager

class GreetingScreenViewModel(application: Application) : AndroidViewModel(application) {
    val isUserAuthorized = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    ).toBoolean()
}
