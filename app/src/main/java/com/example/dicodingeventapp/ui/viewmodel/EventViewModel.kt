package com.example.dicodingeventapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.local.EventEntity
import com.example.dicodingeventapp.data.repository.EventRepository
import com.example.dicodingeventapp.utils.Result

class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun fetchEvent(isActive: Int): LiveData<Result<List<EventEntity>>> = eventRepository.getEvents(isActive)

}