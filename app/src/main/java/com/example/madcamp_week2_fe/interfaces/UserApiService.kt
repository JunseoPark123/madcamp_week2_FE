package com.example.madcamp_week2_fe.interfaces

import com.example.madcamp_week2_fe.models.LoginRequest
import com.example.madcamp_week2_fe.models.LoginResponse
import com.example.madcamp_week2_fe.models.RegisterRequest
import com.example.madcamp_week2_fe.models.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET

interface UserApiService {
    @POST("accounts/login/") // 여기에 실제 로그인 API 경로를 입력하세요
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("accounts/register/") // 실제 회원가입 API 경로
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @GET("accounts/signout/") // 실제 로그아웃 API 경로
    suspend fun logoutUser(): Response<Unit> // 단순한 성공 여부만 확인하면 될 경우 Unit 사용
}
