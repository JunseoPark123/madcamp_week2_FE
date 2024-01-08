package com.example.madcamp_week2_fe.interfaces

import com.example.madcamp_week2_fe.models.LoginRequest
import com.example.madcamp_week2_fe.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface UserApiService {
    @POST("accounts/login/") // 여기에 실제 로그인 API 경로를 입력하세요
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
}