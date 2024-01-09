package com.example.madcamp_week2_fe.models

data class CartItem(
    val id: Int,
    val product_name: String,
    val store_name: String,
    val price: Int
)

data class Cart(
    val cart_items: List<CartItem>,
    val total_price: Int
)
data class AddToCartRequest(
    val product_name: String,
    val price: Int
)

data class AddToOrderRequest(
    val product_name : String,
    val store_name : String,
    val price: Int
)

data class OrderResponse(
    val message: String
)

