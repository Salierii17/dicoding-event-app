package com.example.dicodingeventapp.data.retrofit

import com.example.dicodingeventapp.data.response.EventDetailResponse
import com.example.dicodingeventapp.data.response.EventResponse
import com.example.dicodingeventapp.data.response.ListEventsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvent(
        @Query("active") isActive: Int
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: Int
    ): Call<EventDetailResponse>

    @GET("events")
    fun searchEvent(
        @Query("q") query: String
    ): Call<EventResponse>

}