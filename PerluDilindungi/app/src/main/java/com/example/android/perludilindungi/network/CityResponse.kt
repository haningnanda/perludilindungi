package com.example.android.perludilindungi.network

data class CityResponse(
    val curr_val: String,
    val message: String,
    val results: List<City>
)

data class City(
    val key: String,
    val value: String
)