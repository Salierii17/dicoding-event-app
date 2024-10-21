package com.example.dicodingeventapp.data.local

import android.provider.CalendarContract.EventsEntity
import androidx.lifecycle.LiveData
import androidx.room.Query

interface EventDao {

    // Fetch  Event
    @Query("SELECT * FROM events ORDER BY eventId ASC")
    suspend fun getEvent(): LiveData<List<EventEntity>>

    // Fetch Active Event
    @Query("SELECT * FROM events WHERE isActive = 1 ")
    suspend fun getActiveEvent(isActive: Int): LiveData<List<EventEntity>>

    // Fetch Non Active Event
    @Query("SELECT * FROM events WHERE isActive = 0 ")
    suspend fun getNonActiveEvent(isActive: Int): LiveData<List<EventEntity>>

    // Fetch Event Detail
    @Query("SELECT * FROM events ORDER BY eventId ASC")
    suspend fun getEventDetail(eventId: Int): LiveData<List<EventEntity>>


}