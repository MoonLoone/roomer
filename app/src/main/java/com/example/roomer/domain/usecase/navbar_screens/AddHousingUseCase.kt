package com.example.roomer.domain.usecase.navbar_screens

import android.graphics.Bitmap
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.room_post.RoomPost
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddHousingUseCase(
    private val repository: RoomerRepositoryInterface
) {
    fun postRoomData(
        token: String,
        roomImages: List<Bitmap>,
        title: String,
        monthPrice: String,
        host: Int,
        description: String,
        bedroomsCount: String,
        bathroomsCount: String,
        apartmentType: String,
        sharingType: String,
        location: String
    ): Flow<Resource<Room>> = flow {
        try {
            emit(Resource.Loading())

            val processData = repository.postRoom(
                token,
                RoomPost(
                    title,
                    monthPrice,
                    host,
                    description,
                    bedroomsCount,
                    bathroomsCount,
                    apartmentType,
                    sharingType,
                    location
                )
            )

            val processPhotos = repository.putRoomPhotos(token, processData.body()!!.id, roomImages)

            if (processData.isSuccessful && processPhotos.isSuccessful) {
                emit(Resource.Success(processData.body()!!))
            } else {
                emit(Resource.Error.GeneralError(message = "An error occurred"))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }

    fun putRoomData(
        token: String,
        photosRemoved: Boolean,
        roomId: Int,
        roomImages: List<Bitmap>,
        title: String,
        monthPrice: String,
        description: String,
        bedroomsCount: String,
        bathroomsCount: String,
        apartmentType: String,
        sharingType: String,
    ): Flow<Resource<Room>> = flow {
        try {
            emit(Resource.Loading())

            val processData = repository.putRoom(
                token,
                roomId,
                RoomPost(
                    title,
                    monthPrice,
                    0,
                    description,
                    bedroomsCount,
                    bathroomsCount,
                    apartmentType,
                    sharingType,
                )
            )

            if (photosRemoved) {
                val processPhotos =
                    repository.putRoomPhotos(token, processData.body()!!.id, roomImages)

                if (processData.isSuccessful && processPhotos.isSuccessful) {
                    emit(Resource.Success(processData.body()!!))
                } else {
                    emit(Resource.Error.GeneralError(message = "An error occurred"))
                }
            } else {
                if (processData.isSuccessful) {
                    emit(Resource.Success(processData.body()!!))
                } else {
                    emit(Resource.Error.GeneralError(message = "An error occurred"))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
