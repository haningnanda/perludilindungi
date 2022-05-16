package com.example.android.perludilindungi.checkin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.perludilindungi.network.CheckInResponse
import com.example.android.perludilindungi.network.CheckInProperty
import com.example.android.perludilindungi.network.PerluDilindungiApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class CheckInViewModel: ViewModel() {

    private val _cinStatus = MutableLiveData<CheckInResponse>()
    val checkInResponse : LiveData<CheckInResponse>
        get() = _cinStatus

    fun postCheckIn(code:String, lat: Double, long: Double){
        viewModelScope.launch {
            try{
                val json = JSONObject()
                json.put("qrCode",  code)
                if(lat == long){
                    json.put("latitude", 6.874404665445239)
                    json.put("longitude", 107.60490985070649)
                }else{
                    json.put("latitude", lat)
                    json.put("longitude", long)
                }
                val jStr = json.toString()
                val reqBody = jStr.toRequestBody("application/json".toMediaTypeOrNull())
                val res = PerluDilindungiApi.retrofitService.postCheckInResponse(reqBody)
                if(res.isSuccessful){
                    _cinStatus.value = res.body()
                }else{
                    _cinStatus.value = CheckInResponse(false, res.code(), res.message())
                }
            }catch (e: Exception){
                e.printStackTrace()
                _cinStatus.value = CheckInResponse(false, 600, "gagal gan")
            }
        }
    }
}