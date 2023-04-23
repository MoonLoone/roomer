package com.example.roomer.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Dao
import androidx.room.withTransaction
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.RoomerDatabase
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.toLocalRoom
import com.example.roomer.utils.ScreenState
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class RoomerRemoteMediator<T: Any>(
    private val database: RoomerDatabase,
    private val useCaseFunction: suspend () -> List<T>,
): RemoteMediator<Int, T>() {

    private val favouriteDao = database.favourites

    override suspend fun load(loadType: LoadType, state: PagingState<Int, T>): MediatorResult {
        return try{
            val loadKey = when(loadType){
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.hashCode()
                }
            }
            val response = (useCaseFunction.invoke() as List<Room>).map { it.toLocalRoom() }
            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    favouriteDao.deleteAll()
                }
                favouriteDao.save(response)
            }
            MediatorResult.Success(endOfPaginationReached = true)
        }
        catch (e: IOException){
            MediatorResult.Error(e)
        }
        catch (e: HttpException){
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        return if (true){
            InitializeAction.SKIP_INITIAL_REFRESH
        }
        else{
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

}