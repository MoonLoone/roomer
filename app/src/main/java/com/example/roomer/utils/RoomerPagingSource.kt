package com.example.roomer.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Response

class RoomerPagingSource<T : Any>(
    private val request: suspend (Int, Int) -> Response<List<T>>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val response = request.invoke(page * pageSize, pageSize)
            LoadResult.Page(
                data = response.body() ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if ((response.body()?.size ?: 0) >= pageSize) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
