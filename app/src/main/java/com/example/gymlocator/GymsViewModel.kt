package com.example.gymlocator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(emptyList<Gym>())

    // instant of API Service
    private val apiService: GymApiService
    private lateinit var gymsCall  :Call<List<Gym>>
    init {
        // Retrofit instant object
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            ).baseUrl("https://cairo-gyms-3b76a-default-rtdb.firebaseio.com/")
            .build()
        apiService = retrofit.create(GymApiService::class.java)

        getGyms()
    }

    private fun getGyms() {
        gymsCall = apiService.gitGyms()
        gymsCall.enqueue(object : Callback<List<Gym>> {
            override fun onResponse(p0: Call<List<Gym>>, response: Response<List<Gym>>) {
                response.body()?.let { gymsList ->
                    state = gymsList.restoreSelectedGym()
                }
            }

            override fun onFailure(p0: Call<List<Gym>>, t: Throwable) {
                t.printStackTrace()
            }

        })
//        apiService.gitGyms().execute().body()?.let { gymsList ->
//            state = gymsList.restoreSelectedGym()
//        }
    }

    override fun onCleared() {
        super.onCleared()
        gymsCall.cancel()
    }

    fun toggleFavoritesState(gymId: Int) {
        val gyms = state.toMutableList()  //Copy from state list
        val itemIndex = gyms.indexOfFirst { it.id == gymId } // Make sure the "id" is equal
        gyms[itemIndex] =
            gyms[itemIndex].copy(isFavorites = !gyms[itemIndex].isFavorites) //update value in list
        storeSelectedGym(gyms[itemIndex])
        state = gyms // update state
    }

    private fun storeSelectedGym(gym: Gym) {
        val savedHandleList = stateHandle.get<List<Int>>(FAV_ID).orEmpty().toMutableList()
        if (gym.isFavorites) savedHandleList.add(gym.id)
        else savedHandleList.remove(gym.id)
        stateHandle[FAV_ID] = savedHandleList
    }

    private fun List<Gym>.restoreSelectedGym(): List<Gym> {
//        val gyms = this
        stateHandle.get<List<Int>>(FAV_ID)?.let { savedIDs ->
            savedIDs.forEach { gymId ->
                this.find { it.id == gymId }?.isFavorites = true
            }
        }
        return this
    }


    companion object {
        const val FAV_ID = "favoritesGymsIDs"
    }
}