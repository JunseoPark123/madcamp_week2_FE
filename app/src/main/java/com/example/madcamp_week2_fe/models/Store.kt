package com.example.madcamp_week2_fe.models

import com.google.gson.annotations.SerializedName

data class StoreResponse(
    val store_name: String,
    val latitude : Float,
    val longitude : Float,
    val menus: List<StoreMenu>
)

data class StoreMenu(
    val menu_id: Int,
    val remaining_quantity: Int,
    val details: MenuDetails,
)

data class Menu(
    val menu_id: Int,
    val store_name: String,
    val name: String,
    val store_id: Int,
    val remaining_quantity: Int,
    val details: MenuDetails,
    val price: Int
)
data class MenuDetails(
    val detail_name1: String,
    val detail_gram1: String,
    val detail_name2: String,
    val detail_gram2: String,
    val detail_name3: String,
    val detail_gram3: String,
)