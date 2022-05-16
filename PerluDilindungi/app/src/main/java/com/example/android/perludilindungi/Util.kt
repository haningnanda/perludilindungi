package com.example.android.perludilindungi

fun dateNewsFormatter(tanggal: String): String{
    val delimiter = " "
    val parts = tanggal.split(delimiter)
    return parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3]
}