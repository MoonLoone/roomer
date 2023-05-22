package com.example.roomer.domain.usecase.login_sign_up

import com.example.roomer.data.repository.auth_repository.AuthRepositoryInterface
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import java.io.IOException

class LoginUseCase(
    private val repository: AuthRepositoryInterface
) {

    operator fun invoke(email: String, password: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.userLogin(email, password)

            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(process.body()!!.token))
                }
            } else {
                val errMsg = process.errorBody()?.string()?.let {
                    JSONObject(it).getJSONArray(Constants.UseCase.loginErrorName).toString()
                }
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
