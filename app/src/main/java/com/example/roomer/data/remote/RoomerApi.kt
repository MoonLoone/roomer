package com.example.roomer.data.remote

import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.MessageNotification
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.login_sign_up.IdModel
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.example.roomer.domain.model.login_sign_up.LoginDto
import com.example.roomer.domain.model.login_sign_up.SignUpDataModel
import com.example.roomer.domain.model.login_sign_up.SignUpModel
import com.example.roomer.domain.model.login_sign_up.TokenDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RoomerApi {

    @POST("/auth/token/login/")
    suspend fun login(@Body loginDto: LoginDto): Response<TokenDto>

    @POST("/auth/users/")
    suspend fun signUpPrimary(@Body signUpModel: SignUpModel): Response<IdModel>

    @GET("/interests/")
    suspend fun getInterests(): List<InterestModel>

    @PUT("/auth/users/me/")
    suspend fun putSignUpData(
        @Header("Authorization") token: String,
        @Body signUpDataModel: SignUpDataModel
    ): Response<IdModel>

    @Multipart
    @PUT("/auth/users/me/")
    suspend fun putSignUpAvatar(
        @Header("Authorization") token: String,
        @Part avatar: MultipartBody.Part
    ): Response<IdModel>

    @GET("/housing/")
    suspend fun filterRooms(
        @Query("month_price_from") monthPriceFrom: String,
        @Query("month_price_to") monthPriceTo: String,
        @Query(" bedrooms_count") bedroomsCount: String,
        @Query("bathrooms_count") bathroomsCount: String,
        @Query("housing_type") housingType: String
    ): Response<List<Room>>

    @GET("/profile/")
    suspend fun filterRoommates(
        @Query("sex") sex: String,
        @Query("employment") employment: String,
        @Query("alcohol_attitude") alcoholAttitude: String,
        @Query("smoking_attitude") smokingAttitude: String,
        @Query("sleep_time") sleepTime: String,
        @Query("personality_type") personalityType: String,
        @Query("clean_habits") cleanHabits: String
    ): Response<List<User>>

    @GET("/auth/users/me/")
    suspend fun getCurrentUserInfo(
        @Header("Authorization") token: String
    ): Response<User>

    @GET("/chats/")
    suspend fun getChatsForUser(
        @Query("user_id") userId: Int,
        @Query("chat_id") chatId: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 10
    ): Response<List<Message>>

    @PUT("/chats/{id}/mark_checked/")
    suspend fun messageChecked(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body isChecked: Boolean = true
    ): Response<Message>

    @GET("/notifications/")
    suspend fun getNotifications(
        @Query("user_id") userId: Int
    ): Response<List<MessageNotification>>

    @POST("/favourites/")
    suspend fun addToFavourite(
        @Query("user_id") userId:Int,
        @Query("housing_id") housingId: Int,
    ): Response<String>

    @DELETE("/favourites/")
    suspend fun deleteFavourite(
        @Query("user_id") userId:Int,
        @Query("housing_id") housingId: Int,
    ): Response<String>

    @GET("/favourites/")
    suspend fun getFavourites(
        @Query("user_id") userId:Int,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<List<Room>>

}
