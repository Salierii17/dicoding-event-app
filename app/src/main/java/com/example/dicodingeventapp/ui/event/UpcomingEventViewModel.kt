package com.example.dicodingeventapp.ui.event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.response.EventResponse
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingEventViewModel : ViewModel() {

//    private val _event = MutableLiveData<EventResponse>()
//    val event: LiveData<EventResponse> = _event

    private val _listevent = MutableLiveData<List<ListEventsItem>>()
    val listevent: LiveData<List<ListEventsItem>> = _listevent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "UpcomingEventModel"
        private const val ACTIVE = 1
    }

    init {
        findEvent()
    }

    private fun findEvent() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getEvent(ACTIVE)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listevent.value = response.body()?.listEvents
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

    }


}