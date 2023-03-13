package com.example.roomer.data.repository

import android.graphics.Bitmap
import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.domain.model.RoomsFilterInfo
import com.example.roomer.domain.model.UsersFilterInfo
import com.example.roomer.domain.model.login.LoginDto
import com.example.roomer.domain.model.login.TokenDto
import com.example.roomer.domain.model.signup.IdModel
import com.example.roomer.domain.model.signup.SignUpDataModel
import com.example.roomer.domain.model.signup.SignUpModel
import com.example.roomer.domain.model.signup.interests.InterestModel
import com.example.roomer.domain.model.signup.interests.PutInterestsModel
import com.example.roomer.domain.model.signup.signup_one.SignUpOneModel
import com.example.roomer.domain.model.signup.signup_three.SignUpThreeModel
import java.io.ByteArrayOutputStream
import kotlin.random.Random
import kotlin.random.nextUInt
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class RoomerRepository(
    private val roomerApi: RoomerApi
) : RoomerRepositoryInterface {
    override suspend fun userLogin(
        email: String,
        password: String
    ): Response<TokenDto> {
        return roomerApi.login(LoginDto(email, password))
    }

    override suspend fun userSignUp(
        username: String,
        email: String,
        password: String
    ): Response<IdModel> {
        return roomerApi.signUp(SignUpModel(username, password, email))
    }

    override suspend fun getInterests(): List<InterestModel> {
        return roomerApi.getInterests()
    }

    override suspend fun putSignUpData(
        token: String,
        firstName: String,
        lastName: String,
        sex: String,
        birthDate: String,
        avatar: Bitmap,
        aboutMe: String,
        employment: String,
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String,
        interests: List<InterestModel>
    ): Response<IdModel> {
        val refToken = "Token ".plus(token)
        val stream = ByteArrayOutputStream()
        avatar.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val avatarBody = MultipartBody.Part.createFormData(
            "avatar",
            Random.nextUInt(8000000u).toString().plus(".jpeg"),
            byteArray.toRequestBody("image/*".toMediaTypeOrNull(), 0, byteArray.size)
        )
        return roomerApi.putSignUpData(
            token = refToken,
            avatar = avatarBody,
            SignUpDataModel(
                firstName,
                lastName,
                sex,
                birthDate,
                aboutMe,
                employment,
                sleepTime,
                alcoholAttitude,
                smokingAttitude,
                personalityType,
                cleanHabits,
                interests
            )
        )
    }

    override suspend fun putInterests(
        token: String,
        interests: List<InterestModel>
    ): Response<IdModel> {
        val refToken = "Token ".plus(token)

        return roomerApi.putInterests(
            token = refToken,
            PutInterestsModel(interests)
        )
    }

    override suspend fun putSignUpDataOne(
        token: String,
        firstName: String,
        lastName: String,
        sex: String,
        birthDate: String
    ): Response<IdModel> {
        val refToken = "Token ".plus(token)

        return roomerApi.putSignUpDataOne(
            refToken,
            SignUpOneModel(birthDate, sex, firstName, lastName)
        )
    }

    override suspend fun putSignUpDataThree(
        token: String,
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String
    ): Response<IdModel> {
        val refToken = "Token ".plus(token)
        return roomerApi.putSignUpDataThree(
            refToken,
            SignUpThreeModel(
                sleepTime,
                alcoholAttitude,
                smokingAttitude,
                personalityType,
                cleanHabits
            )
        )
    }

    override suspend fun putSignUpDataTwo(
        token: String,
        avatar: Bitmap,
        aboutMe: String,
        employment: String
    ): Response<IdModel> {
        val refToken = "Token ".plus(token)
        val stream = ByteArrayOutputStream()
        avatar.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val avatarBody = MultipartBody.Part.createFormData(
            "avatar",
            Random.nextUInt(8000000u).toString().plus(".jpeg"),
            byteArray.toRequestBody("image/*".toMediaTypeOrNull(), 0, byteArray.size)
        )
        return roomerApi.putSignUpDataTwo(
            token = refToken,
            avatar = avatarBody,
            hashMapOf(
                Pair("about_me", aboutMe.toRequestBody()),
                Pair("employment", employment.toRequestBody()),
            )
        )
    }

    override suspend fun getFilterRooms(
        monthPriceFrom: String,
        monthPriceTo: String,
        bedroomsCount: String,
        bathroomsCount: String,
        housingType: String
    ): Response<List<RoomsFilterInfo>> {
        return roomerApi.filterRooms(
            monthPriceFrom,
            monthPriceTo,
            bedroomsCount,
            bathroomsCount,
            housingType,
        )
    }

    override suspend fun getFilterRoommates(
        sex: String,
        employment: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        sleepTime: String,
        personalityType: String,
        cleanHabits: String
    ): Response<List<UsersFilterInfo>> {
        return roomerApi.filterRoommates(
            sex,
            employment,
            alcoholAttitude,
            smokingAttitude,
            sleepTime,
            personalityType,
            cleanHabits,
        )
    }
}
