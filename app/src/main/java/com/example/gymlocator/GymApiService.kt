package com.example.gymlocator

import retrofit2.http.GET


interface GymApiService {
    @GET("gyms.json")
    suspend fun gitGyms() : List<Gym>
}

