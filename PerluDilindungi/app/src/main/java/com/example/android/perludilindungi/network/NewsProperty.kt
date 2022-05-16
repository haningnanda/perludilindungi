package com.example.android.perludilindungi.network

import com.squareup.moshi.Json

data class NewsProperty(
    val success: Boolean,
    val message: String,
    val count_total: Double,
    val results: List<News>
)

data class News(
    val title: String,
    val link: List<String>,
    val guid: String,
    val pubDate: String,
    val description: Cdata,
    val enclosure: Enclosure
)

data class Cdata(
    val __cdata: String
)

data class Enclosure(
    val _url: String,
    val _length: String,
    val _type: String
)