package com.example.android.perludilindungi.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.perludilindungi.network.News
import com.example.android.perludilindungi.network.PerluDilindungiApi
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>>
        get() = _news

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            try {
                val result = PerluDilindungiApi.retrofitService.getNewsProperties()
                _response.value = "Success: ${result.count_total} news have been retrieved"
                _news.value = result.results
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }
}