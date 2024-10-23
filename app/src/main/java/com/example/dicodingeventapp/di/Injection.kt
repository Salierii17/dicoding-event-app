package com.example.dicodingeventapp.di

import android.content.Context
import com.example.dicodingeventapp.data.repository.EventRepository
import com.example.dicodingeventapp.data.local.EventDatabase
import com.example.dicodingeventapp.data.remote.ApiConfig
import com.example.dicodingeventapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        return EventRepository.getInstance(apiService, dao)
    }

}