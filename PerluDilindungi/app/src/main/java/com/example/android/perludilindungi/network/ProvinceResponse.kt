package com.example.android.perludilindungi.network

data class ProvinceResponse(
    val curr_val: String,
    val message: String,
    val results: List<Province>
)

data class Province(
    val key: String,
    val value: String
)