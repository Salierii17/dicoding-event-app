package com.example.dicodingeventapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.dicodingeventapp.data.local.EventDao
import com.example.dicodingeventapp.data.local.EventEntity
import com.example.dicodingeventapp.data.retrofit.ApiService
import com.example.dicodingeventapp.utils.AppExecutors

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
    private val appExecutor: AppExecutors
) {
    companion object {

        const val TAG = "EventRepository"

        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: EventDao,
            appExecutors: AppExecutors
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }

    suspend fun getEvents(isActive: Int): LiveData<Result<List<EventEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getEvent(isActive)
            val event = response.listEvents
            val eventList = event.map { event ->
                EventEntity(
                    event.name,
                    event.mediaCover,
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "getActiveEvent: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<EventEntity>>> =
            eventDao.getActiveEvent(isActive).map { Result.Success(it) }
        emitSource(localData)
    }


}