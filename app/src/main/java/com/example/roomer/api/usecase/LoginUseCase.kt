package com.example.roomer.api.usecase

import android.util.Log
import com.example.roomer.api.repository.RoomerRepository
import com.example.roomer.api.utils.Resource
import kotlinx.coroutines.coroutineScope
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
                    JSONObject(it).getString("non_field_errors")
                }
                Log.e("LOG :::", "Error $errMsg")

                emit(Resource.Error(errMsg!!))
            }

        } catch (e: IOException) {

                emit(Resource.Internet("Sunucuya ulaşılamadı. İnternet bağlantınızı kontrol ediniz!"))
                Log.e("LOG :::", e.localizedMessage!!)
        }
    }
}