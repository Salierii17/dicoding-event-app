package com.example.dicodingeventapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingeventapp.data.local.EventEntity
import com.example.dicodingeventapp.data.repository.EventRepository
import com.example.dicodingeventapp.data.response.ListEventsDetailItem
import com.example.dicodingeventapp.utils.Event
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val _eventDetail = MutableLiveData<ListEventsDetailItem>()
    val eventDetail: LiveData<ListEventsDetailItem> get() = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBar = MutableLiveData<Event<String>>()
    val snackBar: LiveData<Event<String>> = _snackBar


    fun fetchEvent(isActive: Int) = repository.getEvents(isActive)

    fun getFavoriteEvent() = repository.getFavoriteEvents()
    fun getListFavoriteEvent() = repository.getListFavoriteEvents()

    fun saveEvents(event: EventEntity) {
        viewModelScope.launch {
            repository.setEventFavorite(event, true)
        }
    }

    fun deleteEvents(event: EventEntity) {
        viewModelScope.launch {
            repository.setEventFavorite(event, false)
        }
    }

    fun fetchEventDetail(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            _isLoading.value = false
            val eventList = repository.fetchEvent(id).event
            _eventDetail.value = eventList
        }
    }

}