package com.example.android.perludilindungi.faskes

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.perludilindungi.bookmark.DbBookmark
import com.example.android.perludilindungi.bookmark.DbBookmarkHandler
import com.example.android.perludilindungi.network.DetailFaskes
import com.example.android.perludilindungi.network.Faskes
import com.example.android.perludilindungi.network.PerluDilindungiApi
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch

class FaskesViewModel : ViewModel(){
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _faskes = MutableLiveData<List<Faskes>>()
    val faskes: LiveData<List<Faskes>>
        get() = _faskes

    private val _detail = MutableLiveData<Faskes>()
    val detail: LiveData<Faskes>
        get() = _detail

    private val _bookmark = MutableLiveData<List<Faskes>>()
    val bookmark: LiveData<List<Faskes>>
        get() = _bookmark


    fun getFaskes(prov:String, city:String) {
        viewModelScope.launch {
            try {
                val result = PerluDilindungiApi.retrofitService.getFaskesResponse(prov,city)
                val data = result.data
                _response.value = "Success: ${result.count_total} faskes have been retrieved"
                _faskes.value = data
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }
    fun getFaskesDetail(prov:String, city:String, id:String) {
        val dbBookmarkHandler = DbBookmarkHandler.getInstance(null, true)
        viewModelScope.launch {
            try {
                val result = PerluDilindungiApi.retrofitService.getFaskesResponse(prov,city)
                for (i in 0 until result.count_total){
                    if (result.data[i].id.toString()==id){
                        val cekbk = dbBookmarkHandler.bookmarkDao().getBookmarkById(result.data[i].kode)
                        result.data[i].isBookmarked = cekbk != null
                        _detail.value = result.data[i]
                        Log.d("detail","Detail Masuk")
                    }
                }

            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
                Log.d("detail", e.message.toString())
            }
        }
    }

    fun getBookmark(context: Context?){
        val dbBookmarkHandler = DbBookmarkHandler.getInstance(context)
        viewModelScope.launch {
            try{
                val data = dbBookmarkHandler.bookmarkDao().getAllBookmark()
                val bookmarks = ArrayList<Faskes>()
                for(i in 0 until data.size){
                    val faskes: Faskes = Gson().fromJson(data[i].faskesJson, Faskes::class.java)
                    bookmarks.add(faskes)
                }
                _bookmark.value = bookmarks
            }catch (e: Exception) {
                Log.d("detail","Gagal get bookmark, => ${e.message}")
            }
        }
    }

    fun addBookmark(faskes: Faskes, context: Context?){
        val dbBookmarkHandler = DbBookmarkHandler.getInstance(context, true)
        viewModelScope.launch {
            try{
                val data = DbBookmark(id = faskes.kode, faskesJson = Gson().toJson(faskes))
                dbBookmarkHandler.bookmarkDao().insertBookmark(data)
                getBookmark(context)
            }catch (e: Exception) {
                Log.d("detail","Gagal masukkan bookmark, ${e.message}")
            }
        }
    }

    fun deleteBookmark(faskes: Faskes, context: Context?){
        val dbBookmarkHandler = DbBookmarkHandler.getInstance(context, true)
        viewModelScope.launch {
            try{
                val data = DbBookmark(id = faskes.kode, faskesJson = Gson().toJson(faskes))
                dbBookmarkHandler.bookmarkDao().delete(data)
                getBookmark(context)
            }catch (e: Exception) {
                Log.d("detail","Detail Gagal")
            }
        }
    }
}