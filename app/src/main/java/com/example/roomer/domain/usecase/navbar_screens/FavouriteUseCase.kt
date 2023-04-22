package com.example.roomer.domain.usecase.navbar_screens

import androidx.paging.PagingData
import com.example.roomer.data.repository.roomer_repository.RoomerRepository
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import com.example.roomer.utils.RoomerPagingSource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import java.io.IOException

class FavouriteUseCase(
    private val repository: RoomerRepositoryInterface,
) {

    operator fun invoke(
        userId:Int,
        offset: Int,
        limit: Int,
    ): Flow<Resource<List<Room>>> = flow {
        try {
            emit(Resource.Loading())
            val process = repository.getFavourites(userId, offset, limit)
            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(process.body()))
                }
            } else {
                var errMsg = process.errorBody()!!.string()
                val errorOn = JSONObject(errMsg).names()!![0].toString()
                errMsg = JSONObject(errMsg).getJSONArray(errorOn)[0].toString()
                emit(Resource.Error.GeneralError(message = errMsg))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}