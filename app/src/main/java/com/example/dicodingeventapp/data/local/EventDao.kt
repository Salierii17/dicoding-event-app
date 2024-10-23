package com.example.dicodingeventapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {

    @Query("SELECT * FROM events WHERE Active = :isActive")
    fun getEvent(isActive: Int): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("SELECT * FROM events WHERE id = :eventId")
    fun getEventDetail(eventId: Int): LiveData<List<EventEntity>>

    @Query("SELECT * FROM events WHERE  id = :eventId AND favorite = 1")
    fun getFavoriteEventById(eventId: String): LiveData<List<EventEntity>>

    @Update
    suspend fun updateEvents(events: EventEntity)
    @Query("DELETE FROM events WHERE favorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM events WHERE id = :eventId AND favorite = 1)")
    suspend fun isEventFavorite(eventId: Int): Boolean


}