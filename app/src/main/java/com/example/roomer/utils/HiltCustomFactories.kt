package com.example.roomer.utils

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

object HiltCustomFactories {

    class LambdaFactory<T: ViewModel>(
        savedStateRegistryOwner: SavedStateRegistryOwner,
        private val create: (handle: SavedStateHandle) -> T
    ): AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return create.invoke(handle) as T
        }
    }
}