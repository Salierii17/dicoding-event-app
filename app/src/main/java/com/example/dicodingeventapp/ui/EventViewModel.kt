package com.example.dicodingeventapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.EventRepository
import com.example.dicodingeventapp.data.Result
import com.example.dicodingeventapp.data.local.EventEntity

class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun fetchEvent(isActive: Int): LiveData<Result<List<EventEntity>>> {
        return eventRepository.getEvents(isActive)
    }

}