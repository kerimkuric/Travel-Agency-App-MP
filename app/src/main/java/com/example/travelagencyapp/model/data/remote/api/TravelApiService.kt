package com.example.travelagencyapp.model.data.remote.api

import com.example.travelagencyapp.model.data.remote.dto.TripRemoteDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TravelApiService {

    @GET("posts")
    suspend fun getTrips(): List<TripRemoteDto>

    @GET("posts/{id}")
    suspend fun getTrip(@Path("id") id: Int): TripRemoteDto

    @POST("posts")
    suspend fun createTrip(@Body trip: TripRemoteDto): TripRemoteDto

    @PUT("posts/{id}")
    suspend fun updateTrip(@Path("id") id: Int, @Body trip: TripRemoteDto): TripRemoteDto

    @DELETE("posts/{id}")
    suspend fun deleteTrip(@Path("id") id: Int)
}
