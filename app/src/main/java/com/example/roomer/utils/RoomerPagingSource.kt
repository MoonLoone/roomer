package com.example.roomer.utils

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Response

class RoomerPagingSource<T : Any>(
    private val request: suspend (Int, Int) -> List<T>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val response = request.invoke(page * pageSize, pageSize)
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if ((response.size) >= pageSize) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
