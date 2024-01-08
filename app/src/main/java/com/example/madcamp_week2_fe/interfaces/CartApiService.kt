package com.example.madcamp_week2_fe.interfaces

import com.example.madcamp_week2_fe.models.Cart
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header


interface CartApiService {
    @GET("cart/get_cart")
    suspend fun getCart(@Header("Authorization") accessToken: String): Response<Cart>
}