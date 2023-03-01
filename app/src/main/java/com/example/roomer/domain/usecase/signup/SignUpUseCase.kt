package com.example.roomer.domain.usecase.signup

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

    operator fun invoke(username: String, email: String, password: String): Flow<Resource<String>> = flow {

        try {

            emit(Resource.Loading())
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
                    emit(Resource.Error.EmailError(message = errMsg))
                } else {
                    emit(Resource.Error.GeneralError(message = errMsg))
                }
            }

        } catch (e: IOException) {
            emit(Resource.Internet(ConstUseCase.internetErrorMessage))
        }
    }
}