package com.example.gymlocator

import retrofit2.Call
import retrofit2.http.GET


interface GymApiService {
    @GET("gyms.json")
    fun gitGyms() : Call<List<Gym>>
}

