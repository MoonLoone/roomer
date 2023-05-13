package com.example.roomer.data.shared.follow

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FollowManipulate @Inject constructor(
    private val roomerRepositoryInterface: RoomerRepositoryInterface,
) : FollowManipulateInterface {

    override fun addFollow(currentUserId: Int, followUserId: Int, token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            roomerRepositoryInterface.followToUser(currentUserId, followUserId, token)
        }
    }

    override fun deleteFollow(currentUserId: Int, followUserId: Int, token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            roomerRepositoryInterface.deleteFollow(currentUserId, followUserId, token)
        }
    }

}