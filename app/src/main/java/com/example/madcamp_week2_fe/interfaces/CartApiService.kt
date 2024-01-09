package com.example.madcamp_week2_fe.interfaces

import com.example.madcamp_week2_fe.models.AddToCartRequest
import com.example.madcamp_week2_fe.models.Cart
import com.example.madcamp_week2_fe.models.CartItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface CartApiService {
    @GET("cart/get_cart")
    suspend fun getCart(@Header("Authorization") accessToken: String): Response<Cart>

    @POST("cart/add/")
    suspend fun addToCart(
        @Header("Authorization") accessToken: String,
        @Body addToCartRequest: AddToCartRequest
    ): Response<CartItem>
}