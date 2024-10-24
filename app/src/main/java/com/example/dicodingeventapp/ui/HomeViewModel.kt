package com.example.dicodingeventapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.response.EventResponse
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.data.remote.ApiConfig
import com.example.dicodingeventapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _searchResult = MutableLiveData<List<ListEventsItem>>()
    val searchResult: LiveData<List<ListEventsItem>> = _searchResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBar = MutableLiveData<Event<String>>()
    val snackBar: LiveData<Event<String>> = _snackBar

    companion object {
        private const val TAG = "HomeViewModel"
    }

    fun search(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchEvent(query)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchResult.value = response.body()?.listEvents
//                    _snackBar.value = Event(response.body()?.message.toString())
                } else {
                    _snackBar.value = Event(response.message())
                    Log.d(TAG, "Response: ${response.body()?.listEvents}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                val message =
                    "Unable to connect. Please check your internet connection and try again."
                _snackBar.value = Event(message)
                Log.e(TAG, "onFailure: ${t.message}")

            }
        })
    }
}