package com.example.roomer.utils

import com.example.roomer.data.paging.RoomerRemoteMediator
import com.example.roomer.data.room.entities.LocalRoom

object PagingFactories {

    fun createFavouritesMediator(
        apiFunction: suspend (Int) -> List<LocalRoom>?,
        saveFunction: suspend (Any) -> Unit,
        deleteFunction: suspend () -> Unit
    ): RoomerRemoteMediator<LocalRoom> {
        return RoomerRemoteMediator(
            apiFunction,
            saveFunction,
            deleteFunction
        )
    }

}
