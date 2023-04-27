package com.example.roomer.utils

import com.example.roomer.data.paging.RoomerPagingSource
import com.example.roomer.data.paging.RoomerRemoteMediator
import com.example.roomer.data.room.entities.RoomWithHost
import com.example.roomer.domain.model.entities.Room

object PagingFactories {

    fun createFavouritesMediator(
        apiFunction: suspend (Int) -> List<RoomWithHost>?,
        saveFunction: suspend (Any) -> Unit,
        deleteFunction: suspend () -> Unit
    ): RoomerRemoteMediator<RoomWithHost> {
        return RoomerRemoteMediator(
            apiFunction,
            saveFunction,
            deleteFunction
        )
    }

    fun createFavouritesPagingSource(getAllFavourites: suspend (Int, Int) -> List<Room>): RoomerPagingSource<Room> {
        return RoomerPagingSource(getAllFavourites)
    }
}
