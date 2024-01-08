package com.example.madcamp_week2_fe.interfaces

import com.example.madcamp_week2_fe.models.Menu
import com.example.madcamp_week2_fe.models.StoreResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreApiService {
    @GET("store/stores")
    suspend fun getAllStores(): Map<String, Int>

    @GET("store/stores/{storeId}")
    suspend fun getStoreById(@Path("storeId") storeId: Int): StoreResponse

    @GET("store/menu")
    suspend fun getAllMenus(): List<Menu>
}
