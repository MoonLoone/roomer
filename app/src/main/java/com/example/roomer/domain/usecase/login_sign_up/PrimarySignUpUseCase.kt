package com.example.roomer.domain.usecase.login_sign_up

import com.example.roomer.data.repository.auth_repository.AuthRepositoryInterface
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class PrimarySignUpUseCase(
    private val repository: AuthRepositoryInterface
) {

    operator fun invoke(
        username: String,
        email: String,
        password: String
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val process = repository.userSignUpPrimary(username, email, password)

            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(process.body()!!.id))
                }
            } else {
                var errMsg = process.errorBody()!!.string()
                val errorOn = JSONObject(errMsg).names()!![0].toString()
                errMsg = JSONObject(errMsg).getJSONArray(errorOn)[0].toString()

                when (errorOn) {
                    "email" -> emit(Resource.Error.EmailError(message = errMsg))
                    "username" -> emit(Resource.Error.UsernameError(message = errMsg))
                    "password" -> emit(Resource.Error.PasswordError(message = errMsg))
                    else -> emit(Resource.Error.GeneralError(message = errMsg))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
