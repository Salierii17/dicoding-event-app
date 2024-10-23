package com.example.dicodingeventapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.dicodingeventapp.data.local.EventDao
import com.example.dicodingeventapp.data.local.EventEntity
import com.example.dicodingeventapp.data.remote.ApiService
import com.example.dicodingeventapp.utils.Result

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
) {
    companion object {

        const val TAG = "EventRepository"

        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: EventDao,
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, newsDao)
            }.also { instance = it }
    }

    fun getEvents(isActive: Int): LiveData<Result<List<EventEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getEvent(isActive)
            val listEvents = response.listEvents
            val eventsList = listEvents.map { listEvent ->
                val isFavorite = eventDao.isEventFavorite(listEvent.id)
                EventEntity(
                    eventId = listEvent.id.toString(),
                    name = listEvent.name,
                    mediaCover = listEvent.mediaCover,
                    isActive = isActive == 1,
                    isFavorite
                )
            }
//            eventDao.deleteAll()
            eventDao.insertEvents(eventsList)
            emit(Result.Success(eventsList))
        } catch (e: Exception) {
            Log.e(TAG, "getActiveEvent: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<EventEntity>>> =
            eventDao.getEvent(isActive).map { Result.Success(it) }
        emitSource(localData)
    }

    fun getFavoriteEvents(id: String): LiveData<List<EventEntity>> {
        return eventDao.getFavoriteEventById(id)
    }

    suspend fun setEventFavorite(events: EventEntity, favoriteState: Boolean) {
        events.isFavorite = favoriteState
        eventDao.updateEvents(events)
    }


}