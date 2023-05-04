package com.example.roomer.domain.usecase.navbar_screens

import android.graphics.Bitmap
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.room_post.RoomPost
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class AddHousingUseCase(
    private val repository: RoomerRepositoryInterface
) {
    fun putRoomData(
        token: String,
        roomImages: List<Bitmap>,
        monthPrice: String,
        host: Int,
        description: String,
        bedroomsCount: String,
        bathroomsCount: String,
        apartmentType: String,
        sharingType: String
    ): Flow<Resource<Room>> = flow {
        try {
            emit(Resource.Loading())

            val processData = repository.postRoom(
                token,
                RoomPost(
                    monthPrice,
                    host,
                    description,
                    bedroomsCount,
                    bathroomsCount,
                    apartmentType,
                    sharingType
                )
            )

            val processPhotos = repository.putRoomPhotos(token, processData.body()!!.id, roomImages)

            if (processData.isSuccessful && processPhotos.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(processData.body()!!))
                }
            } else {
                emit(Resource.Error.GeneralError(message = "An error occurred"))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
