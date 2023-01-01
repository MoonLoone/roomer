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
                var errMsg = process.errorBody()!!.string()
                val errorOn = JSONObject(errMsg).names()!![0].toString()
                errMsg = JSONObject(errMsg).getJSONArray(errorOn)[0].toString()

                if (errorOn == "email") {
                    emit(Resource.EmailError(message = errMsg))
                } else {
                    emit(Resource.Error(message = errMsg))
                }
            }

        } catch (e: IOException) {
            emit(Resource.Internet(ConstUseCase.internetErrorMessage))
        }
    }
}