package com.example.roomer.data.remote

import com.example.roomer.domain.model.city.CityModel
import com.example.roomer.domain.model.comment.Comment
import com.example.roomer.domain.model.comment.UserReview
import com.example.roomer.domain.model.entities.Follow
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
import com.example.roomer.domain.model.pojo.ChatRawData
import com.example.roomer.domain.model.pojo.FavouriteRawData
import com.example.roomer.domain.model.room_post.RoomPost
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
import retrofit2.http.QueryMap

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
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("month_price_from") monthPriceFrom: String?,
        @Query("month_price_to") monthPriceTo: String?,
        @Query("city") location: String?,
        @Query("bedrooms_count") bedroomsCount: String?,
        @Query("bathrooms_count") bathroomsCount: String?,
        @Query("housing_type") housingType: String?
    ): Response<List<Room>>

    @GET("/profile/")
    suspend fun filterRoommates(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("sex") sex: String?,
        @Query("city") location: String?,
        @Query("age_from") ageFrom: String?,
        @Query("age_to") ageTo: String?,
        @Query("employment") employment: String?,
        @Query("alcohol_attitude") alcoholAttitude: String?,
        @Query("smoking_attitude") smokingAttitude: String?,
        @Query("sleep_time") sleepTime: String?,
        @Query("personality_type") personalityType: String?,
        @Query("clean_habits") cleanHabits: String?,
        @QueryMap interests: Map<String, String>
    ): Response<List<User>>

    @GET("/auth/users/me/")
    suspend fun getCurrentUserInfo(
        @Header("Authorization") token: String
    ): Response<User>

    @GET("/chats/")
    suspend fun getChatsForUser(
        @Query("user_id") userId: Int,
        @Query("chat_id") chatId: String,
        @Query("page") page: Int = 1
    ): Response<ChatRawData>

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
        @Query("user_id") userId: Int,
        @Query("housing_id") housingId: Int
    ): Response<String>

    @DELETE("/favourites/")
    suspend fun deleteFavourite(
        @Query("user_id") userId: Int,
        @Query("housing_id") housingId: Int
    ): Response<String>

    @GET("/favourites/")
    suspend fun getFavourites(
        @Query("user_id") userId: Int,
        @Query("page") page: Int = 1
    ): Response<FavouriteRawData>

    @POST("/housing/")
    suspend fun postAdvertisement(
        @Header("Authorization") token: String,
        @Body room: RoomPost
    ): Response<Room>

    @DELETE("/housing/{roomId}/")
    suspend fun removeAdvertisement(
        @Header("Authorization") token: String,
        @Path("roomId") roomId: Int
    ): Response<Unit>

    @Multipart
    @PUT("/housing/{roomId}/")
    suspend fun putAdvertisement(
        @Header("Authorization") token: String,
        @Path("roomId") roomId: Int,
        @Part filesContent: List<MultipartBody.Part>
    ): Response<Room>

    @PUT("/housing/{roomId}/")
    suspend fun putAdvertisement(
        @Header("Authorization") token: String,
        @Path("roomId") roomId: Int,
        @Body room: RoomPost
    ): Response<Room>

    @GET("/housing/")
    suspend fun getCurrentUserAdvertisements(
        @Header("Authorization") token: String,
        @Query("host_id") hostId: Int
    ): Response<List<Room>>

    @GET("/follow/")
    suspend fun getFollows(
        @Query("user_id") userId: Int,
        @Header("Authorization") token: String
    ): Response<List<Follow>>

    @POST("/follow/")
    suspend fun followToUser(
        @Query("user_id") userId: Int,
        @Query("follow_id") followId: Int,
        @Header("Authorization") token: String
    ): Response<String>

    @DELETE("/follow/")
    suspend fun deleteFollow(
        @Query("user_id") userId: Int,
        @Query("follow_id") followId: Int,
        @Header("Authorization") token: String
    ): Response<String>

    @GET("/cities/")
    suspend fun getCities(
        @Header("Authorization") token: String
    ): Response<List<CityModel>>

    @GET("/follow/check_follower/")
    suspend fun checkIsFollowed(
        @Header("Authorization") token: String,
        @Query("user_id") currentUserId: Int,
        @Query("follow_id") userId: Int
    ): Response<Unit>

    @POST("/review/")
    suspend fun addReview(
        @Header("Authorization") token: String,
        @Body comment: Comment
    ): Response<Unit>

    @GET("/review/")
    suspend fun getReviews(
        @Header("Authorization") token: String,
        @Query("receiver_id") receiverId: Int
    ): Response<List<UserReview>>

    @POST("/auth/token/logout/")
    suspend fun logout(
        @Header("Authorization") token: String
    ): Response<Unit>
}
