package com.example.roomer.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    sealed class Error<T>(message: String, data: T? = null) : Resource<T>(
        data = data,
        message = message,
    ) {
        class GeneralError<T>(message: String) : Error<T>(message = message)
        class EmailError<T>(message: String) : Error<T>(message = message)
        class PasswordError<T>(message: String) : Error<T>(message = message)
        class UsernameError<T>(message: String) : Error<T>(message = message)
        class FirstNameError<T>(message: String) : Error<T>(message = message)
        class LastNameError<T>(message: String) : Error<T>(message = message)
    }
    class Success<T>(data: T? = null) : Resource<T>(data = data)
    class Loading<T>(data: T? = null) : Resource<T>(data = data)
    class Internet<T>(message: String, data: T? = null) : Resource<T>(
        data = data,
        message = message,
    )
}
