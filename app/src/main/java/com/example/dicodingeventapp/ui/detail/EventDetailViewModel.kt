package com.example.dicodingeventapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.response.EventDetailResponse
import com.example.dicodingeventapp.data.response.ListEventsDetailItem
import com.example.dicodingeventapp.data.retrofit.ApiConfig
import com.example.dicodingeventapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetailViewModel : ViewModel() {

    private val _eventDetail = MutableLiveData<ListEventsDetailItem>()
    val eventDetail: LiveData<ListEventsDetailItem> = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBar = MutableLiveData<Event<String>>()
    val snackBar: LiveData<Event<String>> = _snackBar

    companion object {
        private const val TAG = "EventDetailViewModel"
    }

    fun fetchEventDetail(eventID: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailEvent(eventID)
        client.enqueue(object : Callback<EventDetailResponse> {
            override fun onResponse(
                call: Call<EventDetailResponse>,
                response: Response<EventDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _eventDetail.value = response.body()?.event
//                    _snackBar.value = Event(response.body()?.message.toString())
                } else {
                    _snackBar.value = Event(response.message())
                    Log.e(TAG, "Response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventDetailResponse>, t: Throwable) {
                _isLoading.value = false
                val message = "onFailure: ${t.message.toString()}"
                _snackBar.value = Event(message)
                Log.e(TAG, message)
            }

        })
    }
}