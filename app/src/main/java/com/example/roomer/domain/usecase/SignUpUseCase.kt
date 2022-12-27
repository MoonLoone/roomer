package com.example.roomer.domain.usecase

import android.util.Log
import com.example.roomer.utils.Resource
import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.utils.ConstUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import java.io.IOException

class SignUpUseCase (
    private val repository: RoomerRepository
) {

    operator fun invoke(username: String, email: String, password: String, delay: Long): Flow<Resource<String>> = flow {

        try {

            emit(Resource.Loading())
            delay(delay)
            val process = repository.userSignUp(username, email, password)

            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(process.body()!!.id))
                }
            }
            else {
                val errMsg = process.errorBody()?.string()?.let {
                    val errorOn = JSONObject(it).names()!![0].toString()
                    errorOn + " " + JSONObject(it).getJSONArray(errorOn)[0]
                }
                emit(Resource.Error(message = errMsg!!))
            }

        } catch (e: IOException) {
            emit(Resource.Internet(ConstUseCase.internetErrorMessage))
        }
    }
}