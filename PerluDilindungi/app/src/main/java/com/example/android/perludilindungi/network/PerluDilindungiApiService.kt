package com.example.android.perludilindungi.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.GET
import retrofit2.http.Query

import retrofit2.http.*

private const val BASE_URL = "https://perludilindungi.herokuapp.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PerluDilindungiApiService {
    @GET("api/get-news")
    suspend fun getNewsProperties(): NewsProperty

    @GET("api/get-province")
    fun getProvince(): Call<LocationProperty>

    @GET("api/get-city")
    fun getCity(@Query("start_id") start_id : String) : Call<LocationProperty>

    suspend fun getProvinceResponse(): ProvinceResponse

    @GET("api/get-city")
    suspend fun getCityResponse(@Query("start_id") start_id: String?): CityResponse

    @GET("api/get-faskes-vaksinasi")
    suspend fun getFaskesResponse(@Query("province") province: String?,
                                  @Query("city") city: String?): FaskesResponse

    @POST("check-in")
    suspend fun postCheckInResponse(@Body data: RequestBody): Response<CheckInResponse>
}

object PerluDilindungiApi {
    val retrofitService : PerluDilindungiApiService by lazy {
        retrofit.create(PerluDilindungiApiService::class.java) }
}
