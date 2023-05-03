package com.example.roomer.utils

import android.util.Log
import com.example.roomer.data.local.RoomerStoreInterface
import com.example.roomer.data.paging.RoomerRemoteMediator
import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.data.room.entities.LocalMessage
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.toLocalMessage
import com.example.roomer.domain.model.pojo.ChatRawData
import com.example.roomer.domain.model.pojo.FavouriteRawData

object PagingFactories {

    fun createFavouritesMediator(
        roomerApi: RoomerApi,
        roomerStore: RoomerStoreInterface,
        userId: Int,
    ): RoomerRemoteMediator<LocalRoom, FavouriteRawData> {
        return RoomerRemoteMediator(
            apiFunction = { page ->
                roomerApi.getFavourites(userId, page).body()
            },
            saveToDb = { response ->
                response.results?.map {
                    val room = it.housing?: Room()
                    room.page = response.page
                    room
                }?.let {
                        roomerStore.addManyFavourites(it)
                    }
            },
            deleteFromDb = {
                roomerStore.clearFavourites()
            }
        )
    }

    fun createMessagesMediator(
        roomerApi: RoomerApi,
        roomerStore: RoomerStoreInterface,
        userId: Int,
        chatId: String,
    ): RoomerRemoteMediator<LocalMessage, ChatRawData> {
        return RoomerRemoteMediator(
            apiFunction = { page ->
                roomerApi.getChatsForUser(userId, chatId, page).body()
            },
            saveToDb = { response ->
                response.results?.map {
                    val message = it.toLocalMessage()
                    message.page = response.page
                    message
                }
                    ?.let { roomerStore.saveManyLocalMessages(it) }
            },
            deleteFromDb = {
                roomerStore.clearLocalMessages()
            }
        )
    }
}
