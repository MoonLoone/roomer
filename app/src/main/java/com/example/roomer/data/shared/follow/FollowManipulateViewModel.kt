package com.example.roomer.data.shared.follow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowManipulateViewModel @Inject constructor(
    private val roomerRepositoryInterface: RoomerRepositoryInterface,
    application: Application
) : AndroidViewModel(application) {

    private val token = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        key = SpManager.Sp.TOKEN,
        null
    ) ?: ""

    fun addFollow(
        followManipulate: FollowManipulate,
        currentUserId: Int,
        followUserId: Int
    ) {
        followManipulate.addFollow(currentUserId, followUserId, token)
    }

    fun deleteFollow(
        followManipulate: FollowManipulate,
        currentUserId: Int,
        followUserId: Int
    ) {
        followManipulate.deleteFollow(currentUserId, followUserId, token)
    }
}
