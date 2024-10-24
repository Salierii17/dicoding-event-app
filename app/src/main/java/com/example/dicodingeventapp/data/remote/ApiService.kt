package com.example.dicodingeventapp.data.remote

import com.example.dicodingeventapp.data.response.EventDetailResponse
import com.example.dicodingeventapp.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvent(
        @Query("active") isActive: Int
    ): EventResponse

    @GET("events/{id}")
    suspend fun getDetailEvent(
        @Path("id") id: Int
    ): EventDetailResponse

    @GET("events")
    fun searchEvent(
        @Query("q") query: String
    ): Call<EventResponse>

}