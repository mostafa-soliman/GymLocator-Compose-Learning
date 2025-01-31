package com.example.gymlocator.gyms.persentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymlocator.gyms.domain.Gym
import com.example.gymlocator.gyms.data.remote.GymApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsDetailsViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    var state by mutableStateOf<Gym?>(null)

    private val apiService : GymApiService

    init{
        val retrofit :Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://cairo-gyms-3b76a-default-rtdb.firebaseio.com/")
            .build()
        apiService = retrofit.create(GymApiService::class.java)
        val id= savedStateHandle.get<Int>("gym_id") ?: 0
        getGym(id)
    }

    private fun getGym(id : Int){
        viewModelScope.launch {
            val gym = getGymFormRemoteDB(id)
            state =gym
        }
    }

    private suspend fun getGymFormRemoteDB(id: Int) = withContext(Dispatchers.IO) {
        apiService.getGym(id).values.first().let {
            Gym(it.id,it.name,it.location,it.isOpen)
        }
    }
}