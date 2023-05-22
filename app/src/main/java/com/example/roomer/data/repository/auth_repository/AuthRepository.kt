package com.example.roomer.data.repository.auth_repository

import android.graphics.Bitmap
import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.domain.model.login_sign_up.IdModel
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.example.roomer.domain.model.login_sign_up.LoginDto
import com.example.roomer.domain.model.login_sign_up.SignUpDataModel
import com.example.roomer.domain.model.login_sign_up.SignUpModel
import com.example.roomer.domain.model.login_sign_up.TokenDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import kotlin.random.Random
import kotlin.random.nextUInt

class AuthRepository(
    private val roomerApi: RoomerApi
) : AuthRepositoryInterface {
    override suspend fun userLogin(
        email: String,
        password: String
    ): Response<TokenDto> {
        return roomerApi.login(LoginDto(email, password))
    }

    override suspend fun userSignUpPrimary(
        username: String,
        email: String,
        password: String
    ): Response<IdModel> {
        return roomerApi.signUpPrimary(SignUpModel(username, password, email))
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
        aboutMe: String,
        employment: String,
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String,
        interests: List<InterestModel>,
        city: String
    ): Response<IdModel> {
        val refToken = "Token ".plus(token)
        return roomerApi.putSignUpData(
            refToken,
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
                interests,
                city
            )
        )
    }

    override suspend fun putSignUpAvatar(token: String, avatar: Bitmap): Response<IdModel> {
        val refToken = "Token ".plus(token)
        val stream = ByteArrayOutputStream()
        avatar.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val avatarPart = MultipartBody.Part.createFormData(
            "avatar",
            Random.nextUInt(8000000u).toString().plus(".jpeg"),
            byteArray.toRequestBody("image/*".toMediaTypeOrNull(), 0, byteArray.size)
        )
        return roomerApi.putSignUpAvatar(refToken, avatarPart)
    }
}
