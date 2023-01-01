package com.example.roomer.utils

sealed class Resource<T>(val data: T? = null, val message:String? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(message: String, data:T? = null) : Resource<T>(data = data,message=message)
    class EmailError<T>(message: String) : Resource<T>(message=message)
    class Loading<T>(data:T?= null) : Resource<T>(data=data)
    class Internet<T>(message: String, data:T? = null) : Resource<T>(data = data,message=message)
}
