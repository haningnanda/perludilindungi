package com.example.android.perludilindungi.lokasivaksin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.perludilindungi.network.City
import com.example.android.perludilindungi.network.Faskes
import com.example.android.perludilindungi.network.PerluDilindungiApi
import com.example.android.perludilindungi.network.Province
import kotlinx.coroutines.launch

class LokasiVaksinViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _province = MutableLiveData<List<Province>>()
    val province: LiveData<List<Province>>
        get() = _province

    private val _city = MutableLiveData<List<City>>()
    val city: LiveData<List<City>>
        get() = _city

    private val _faskes = MutableLiveData<List<Faskes>>()
    val faskes: LiveData<List<Faskes>>
        get() = _faskes

//    init {
//        getFaskes()
//    }
//
//    private fun getFaskes() {
//        viewModelScope.launch {
//            try {
//                val provinceResponse = PerluDilindungiApi.retrofitService.getProvinceResponse()
//                _province.value = provinceResponse.results
//
//                val cityResponse = PerluDilindungiApi.retrofitService
//                    .getCityResponse("DKI JAKARTA")
//                _city.value = cityResponse.results
//
//                val faskesResponse = PerluDilindungiApi.retrofitService
//                    .getFaskesResponse("DKI JAKARTA", "KOTA ADM. JAKARTA PUSAT")
//                _faskes.value = faskesResponse.data
//
//                _response.value = "Success: data have been retrieved"
//            } catch (e: Exception) {
//                _response.value = "Failure: ${e.message}"
//            }
//        }
//    }
}