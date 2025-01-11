package com.example.gymlocator.gyms.data

import com.example.gymlocator.gyms.domain.Gym
import com.example.gymlocator.gyms.data.remote.GymApiService
import com.example.gymlocator.GymsApplication
import com.example.gymlocator.gyms.data.local.GymsDatabase
import com.example.gymlocator.gyms.data.local.LocalGym
import com.example.gymlocator.gyms.data.local.LocalGymFavouriteState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsRepository {
    // Retrofit instant object
    private val apiService: GymApiService = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create()
        ).baseUrl("https://cairo-gyms-3b76a-default-rtdb.firebaseio.com/")
        .build()
        .create(GymApiService::class.java)

    private val gymsDao = GymsDatabase.getDaoInstant(GymsApplication.getApplicationContext())


    suspend fun loadGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (ex: Exception) {
            if (gymsDao.getAll().isEmpty()) {
                throw Exception("Something went wrong. no data was found, try connecting to internet")
            }
        }

    }

    suspend fun getGyms(): List<Gym>{
        return withContext(Dispatchers.IO){
            return@withContext gymsDao.getAll().map {
                Gym(it.id,it.name,it.location,it.isOpen,it.isFavorites)
            }
        }
    }

    suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymsDao.getFavouriteGyms()
        gymsDao.addAll(gyms.map {
            LocalGym(it.id,it.name,it.location,it.isOpen)
        }
        )
        gymsDao.updateAll(
            favouriteGymsList.map {
                LocalGymFavouriteState(id = it.id, true)
            }
        )
    }

    suspend fun toggleFavoriteGym(gymId: Int, state: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.update(
                LocalGymFavouriteState(
                    id = gymId,
                    isFavorites = state
                )
            )
            return@withContext gymsDao.getAll()
        }

}