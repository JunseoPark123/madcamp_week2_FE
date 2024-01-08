package com.example.madcamp_week2_fe.models

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val access_token: String,
    val refresh_token: String,
    val email: String,
    val username: String,
    val phone_number: String,
    val current_location: String
)

data class LoginError(
    val detail: String
)