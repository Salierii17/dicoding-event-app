package com.example.dicodingeventapp.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.response.EventResponse
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.data.retrofit.ApiConfig
import com.example.dicodingeventapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//class EventFinishedViewModel : ViewModel() {
//
//    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
//    val listEvent: LiveData<List<ListEventsItem>> = _listEvent
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _snackBar = MutableLiveData<Event<String>>()
//    val snackBar: LiveData<Event<String>> = _snackBar
//
//    companion object {
//        private const val TAG = "EventFinishedViewModel"
//        private const val ACTIVE = 0
//    }
//
//    init {
//        fetchEvent()
//    }
//
//    private fun fetchEvent() {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getEvent(ACTIVE)
//        client.enqueue(object : Callback<EventResponse> {
//            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    _listEvent.value = response.body()?.listEvents
////                    _snackBar.value = Event(response.body()?.message.toString())
//                } else {
//                    _snackBar.value = Event(response.message())
//                    Log.e(TAG, "Response: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
//                _isLoading.value = false
//                val message =
//                    "Unable to connect. Please check your internet connection and try again."
//                _snackBar.value = Event(message)
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
//    }
//}