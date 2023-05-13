package com.example.roomer.data.shared.follow

interface FollowManipulateInterface {

    fun addFollow(currentUserId: Int, followUserId: Int, token: String)

    fun deleteFollow(currentUserId: Int, followUserId: Int, token: String)

}