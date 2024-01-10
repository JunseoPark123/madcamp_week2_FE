package com.example.madcamp_week2_fe.models

data class RatingRequest(
    val store_name: String,
    val rating: Int
)

data class RatingResponse(
    val message: String
)

data class AverageRatingRequest(
    val store_name: String
)

data class AverageRatingResponse(
    val store_name: String,
    val average_rating: Double?
)