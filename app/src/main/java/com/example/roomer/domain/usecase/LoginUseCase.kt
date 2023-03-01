package com.example.roomer.domain.usecase

import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.utils.ConstUseCase
import com.example.roomer.utils.Resource
import kotlinx.coroutines.Delay
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import java.io.IOException


class LoginUseCase (
    private val repository: RoomerRepository
) {

    operator fun invoke(email: String, password: String): Flow<Resource<String>> = flow {

        try {

            emit(Resource.Loading())

            val process = repository.userLogin(email, password)

            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(process.body()!!.token))
                }
            }
            else {
                val errMsg = process.errorBody()?.string()?.let {
                    JSONObject(it).getJSONArray(ConstUseCase.loginErrorName).toString()
                }
                emit(Resource.Error.GeneralError(errMsg!!))
            }

        } catch (e: IOException) {

                emit(Resource.Internet(ConstUseCase.internetErrorMessage))
        }
    }
}