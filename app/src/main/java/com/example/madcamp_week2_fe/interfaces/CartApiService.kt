package com.example.madcamp_week2_fe.interfaces

import com.example.madcamp_week2_fe.models.AddToCartRequest
import com.example.madcamp_week2_fe.models.AddToOrderRequest
import com.example.madcamp_week2_fe.models.Cart
import com.example.madcamp_week2_fe.models.CartItem
import com.example.madcamp_week2_fe.models.Order
import com.example.madcamp_week2_fe.models.OrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface CartApiService {

    @GET("cart/order/history")
    suspend fun getOrderHistory(
        @Header("Authorization") accessToken: String,
    ): Response<List<Order>>

    @POST("cart/order/")
    suspend fun addToOrder(
        @Header("Authorization") accessToken: String,
        @Body addToOrderRequest: AddToOrderRequest
    ):Response<OrderResponse>

    @GET("cart/get_cart")
    suspend fun getCart(
        @Header("Authorization") accessToken: String
    ): Response<Cart>

    @POST("cart/add/")
    suspend fun addToCart(
        @Header("Authorization") accessToken: String,
        @Body addToCartRequest: AddToCartRequest
    ): Response<CartItem>

    @DELETE("cart/remove/{cartIndex}/")
    suspend fun deleteItemFromCart(
        @Header("Authorization") accessToken: String,
        @Path("cartIndex") cartIndex: Int
    ): Response<Unit>

}