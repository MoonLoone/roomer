package com.example.roomer.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.roomer.domain.model.entities.BaseEntity
import com.example.roomer.domain.model.pojo.RawData
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class RoomerRemoteMediator<in T : BaseEntity, in K: RawData>(
    private val apiFunction: suspend (Int) -> K?,
    private val saveToDb: suspend (K) -> Unit,
    private val deleteFromDb: suspend () -> Unit
) : RemoteMediator<Int, @UnsafeVariance T>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, @UnsafeVariance T>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    lastItem.page+1
                }
            }
            val response = apiFunction(loadKey?:1)
            response?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    if (loadType == LoadType.REFRESH) {
                        deleteFromDb()
                    }
                    saveToDb(response)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response?.next == null)
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
