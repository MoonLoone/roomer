package com.example.roomer.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.roomer.data.room.entities.RoomWithHost
import com.example.roomer.data.room.entities.toRoom
import com.example.roomer.domain.model.entities.BaseEntity
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class RoomerRemoteMediator<T : RoomWithHost>(
    private val useCaseFunction: suspend (Int) -> List<T>?,
    private val saveToDb: suspend (Any) -> Unit,
    private val deleteFromDb: suspend () -> Unit
) : RemoteMediator<Int, T>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, T>): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.room?.roomId
                }
            }
            val response = useCaseFunction(loadKey ?: 0)
            response?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    if (loadType == LoadType.REFRESH) {
                        deleteFromDb()
                    }
                    saveToDb(response.map { it.room.toRoom() })
                }
            }
            MediatorResult.Success(endOfPaginationReached = response?.isEmpty() ?: true)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        // return if (System.currentTimeMillis() <= cacheTimeout){
        return if (false) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
}
