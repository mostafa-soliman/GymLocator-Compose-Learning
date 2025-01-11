package com.example.gymlocator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymRepository {
    // Retrofit instant object
    private val apiService: GymApiService = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create()
        ).baseUrl("https://cairo-gyms-3b76a-default-rtdb.firebaseio.com/")
        .build()
        .create(GymApiService::class.java)

    private val gymsDao = GymsDatabase.getDaoInstant(GymsApplication.getApplicationContext())


    suspend fun getGymsFromRemoteDB() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (ex: Exception) {
            if (gymsDao.getAll().isEmpty()) {
                throw Exception("Something went wrong. no data was found, try connecting to internet")
            }
        }
        gymsDao.getAll()
    }

    suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymsDao.getFavouriteGyms()
        gymsDao.addAll(gyms)
        gymsDao.updateAll(
            favouriteGymsList.map {
                GymFavouriteState(id = it.id, true)
            }
        )
    }

    suspend fun toggleFavoriteGym(gymId: Int, newFavoriteState: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.update(
                GymFavouriteState(
                    id = gymId,
                    isFavorites = newFavoriteState
                )
            )
            return@withContext gymsDao.getAll()
        }

}