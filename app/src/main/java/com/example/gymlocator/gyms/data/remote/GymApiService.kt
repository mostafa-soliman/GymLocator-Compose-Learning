package com.example.gymlocator.gyms.data.remote

import com.example.gymlocator.gyms.data.remote.RemoteGym
import retrofit2.http.GET
import retrofit2.http.Query


interface GymApiService {
    @GET("gyms.json")
    suspend fun getGyms() : List<RemoteGym>

    @GET("gyms.json?orderBy=\"id\"")
    suspend fun getGym(
        @Query("equalTo") id:Int
    ): Map<String , RemoteGym>
}

