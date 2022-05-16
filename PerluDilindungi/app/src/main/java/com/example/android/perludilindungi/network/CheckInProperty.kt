package com.example.android.perludilindungi.network

import com.google.gson.annotations.SerializedName

data class CheckInProperty(
    @SerializedName("qrCode") val qrCode: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longtitude") val  longtitude: Double
)

data class CheckInResponse(
    @SerializedName("succes") val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: CekInRespData = CekInRespData()
)

data class CekInRespData(
    @SerializedName("userStatus") val userStatus: String = "",
    @SerializedName("reason") val reason: String = ""
)
