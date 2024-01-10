package com.example.madcamp_week2_fe.interfaces

import com.example.madcamp_week2_fe.models.AverageRatingRequest
import com.example.madcamp_week2_fe.models.AverageRatingResponse
import com.example.madcamp_week2_fe.models.RatingRequest
import com.example.madcamp_week2_fe.models.RatingResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface StarApiService {
    @POST("star/")
    suspend fun postRating(
        @Header("Authorization") accessToken: String,
        @Body ratingRequest: RatingRequest
    ): Response<RatingResponse>

    @POST("star/average_rating/")
    suspend fun getAverageRating(
        @Body averageRatingRequest: AverageRatingRequest
    ): Response<AverageRatingResponse>
}