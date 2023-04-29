package com.example.roomer.utils

import com.example.roomer.data.local.RoomerStoreInterface
import com.example.roomer.data.paging.RoomerRemoteMediator
import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.data.room.entities.toRoom
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.toLocalRoom

object PagingFactories {

    fun createFavouritesMediator(
        roomerApi: RoomerApi,
        roomerStore: RoomerStoreInterface,
        userId: Int,
        limit: Int
    ): RoomerRemoteMediator<LocalRoom> {
        return RoomerRemoteMediator(
            apiFunction = { offset ->
                roomerApi.getFavourites(userId, offset, limit).body()?.map {
                    (it.housing ?: Room()).toLocalRoom()
                }
            },
            saveToDb = { response ->
                roomerStore.addManyFavourites((response as List<LocalRoom>).map { it.toRoom() })
            },
            deleteFromDb = {
                roomerStore.clearFavourites()
            }
        )
    }
}
