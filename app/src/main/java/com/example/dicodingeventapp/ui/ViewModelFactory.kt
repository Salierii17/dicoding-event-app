package com.example.dicodingeventapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingeventapp.data.repository.EventRepository
import com.example.dicodingeventapp.di.Injection
import com.example.dicodingeventapp.ui.home.HomeViewModel
import com.example.dicodingeventapp.ui.settings.SettingsPreferences
import com.example.dicodingeventapp.ui.settings.SettingsViewModel

class ViewModelFactory private constructor(
    private val eventRepository: EventRepository,
    private val pref: SettingsPreferences
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideRepository(context),
                Injection.providePreferences(context)
            )
        }.also { instance = it }
    }
}