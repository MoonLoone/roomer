package com.example.roomer.domain.usecase.profile_nested_screens

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Follow
import com.example.roomer.domain.model.entities.User

class FollowScreenUseCase(private val roomerRepositoryInterface: RoomerRepositoryInterface) {

    suspend fun getFollows(currentUserId: Int, token: String): List<Follow> {
        return roomerRepositoryInterface.getFollows(currentUserId, token).body() ?: emptyList()
    }

    suspend fun getCurrentUser(): User{
        return roomerRepositoryInterface.getLocalCurrentUser()
    }

}