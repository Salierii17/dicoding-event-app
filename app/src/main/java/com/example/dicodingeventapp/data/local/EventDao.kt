package com.example.dicodingeventapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {


    // Fetch  Event
//    @Query("SELECT * FROM events ORDER BY id ASC")
//    fun getEvent(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM events WHERE Active = :isActive")
    fun getEvent(isActive: Int): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("SELECT * FROM events WHERE Active = :isActive")
    fun getBooksByBookmarkStatus(isActive: Int): List<EventEntity>


    // Fetch Event Detail
    @Query("SELECT * FROM events WHERE id = :eventId")
    fun getEventDetail(eventId: Int): LiveData<List<EventEntity>>


}