package com.example.android.perludilindungi.network

import com.squareup.moshi.Json

data class LocationProperty(
    val curr_val: String,
    val message: String,
    val results: List<Location>
)

data class Location(
    val key: String,
    val value: String
)
