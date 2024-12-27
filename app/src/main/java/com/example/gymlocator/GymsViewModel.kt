package com.example.gymlocator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(
    // private val stateHandle: SavedStateHandle  //because used local database  || Room DB
) : ViewModel() {
    var state by mutableStateOf(emptyList<Gym>())

    // instant of API Service
    private val apiService: GymApiService
    private val gymsDao = GymsDatabase.getDaoInstant(GymsApplication.getApplicationContext())
    private val errorHandle =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
//    private lateinit var gyms  :Call<List<Gym>>

//    val job = Job()
//    val scope = CoroutineScope(context = job + Dispatchers.IO)

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
        viewModelScope.launch(Dispatchers.IO + errorHandle) {
            state = getGymsFromRemoteDB()
        }


//        gymsCall = apiService.gitGyms()
//        gymsCall.enqueue(object : Callback<List<Gym>> {
//            override fun onResponse(p0: Call<List<Gym>>, response: Response<List<Gym>>) {
//                response.body()?.let { gymsList ->
//                    state = gymsList.restoreSelectedGym()
//                }
//            }
//
//            override fun onFailure(p0: Call<List<Gym>>, t: Throwable) {
//                t.printStackTrace()
//            }
//
//        })
//        apiService.gitGyms().execute().body()?.let { gymsList ->
//            state = gymsList.restoreSelectedGym()
//        }
    }

    private suspend fun getGymsFromRemoteDB() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (ex: Exception) {
            if (gymsDao.getAll().isEmpty()) {
                throw Exception("Something went wrong. no data was found, try connecting to internet")
            }
        }
        gymsDao.getAll()
    }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymsDao.getFavouriteGyms()
        gymsDao.addAll(gyms)
        gymsDao.updateAll(
            favouriteGymsList.map {
                GymFavouriteState(id = it.id, true)
            }
        )
    }

    fun toggleFavoritesState(gymId: Int) {
        val gyms = state.toMutableList()  //Copy from state list
        val itemIndex = gyms.indexOfFirst { it.id == gymId } // Make sure the "id" is equal
        //  because used local database
        //        gyms[itemIndex] =
        //            gyms[itemIndex].copy(isFavorites = !gyms[itemIndex].isFavorites) //update value in list
        //        storeSelectedGym(gyms[itemIndex])
        //  state = gyms // update state
        viewModelScope.launch {
            val updateGymsList = toggleFavoriteGym(gymId, !gyms[itemIndex].isFavorites)
            state = updateGymsList
        }
    }

    private suspend fun toggleFavoriteGym(gymId: Int, newFavoriteState: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.update(
                GymFavouriteState(
                    id = gymId,
                    isFavorites = newFavoriteState
                )
            )
            return@withContext gymsDao.getAll()
        }

//    private fun storeSelectedGym(gym: Gym) {
//        val savedHandleList = stateHandle.get<List<Int>>(FAV_ID).orEmpty().toMutableList()
//        if (gym.isFavorites) savedHandleList.add(gym.id)
//        else savedHandleList.remove(gym.id)
//        stateHandle[FAV_ID] = savedHandleList
//    }
//
//    private fun List<Gym>.restoreSelectedGym(): List<Gym> {
////        val gyms = this
//        stateHandle.get<List<Int>>(FAV_ID)?.let { savedIDs ->
//            val gymsMap = this.associateBy { it.id }.toMutableMap()  //map key value
//            savedIDs.forEach { gymId ->
//                val gym = gymsMap[gymId] ?: return@forEach
//                gymsMap[gymId] = gym.copy(isFavorites = true)
//
//                //  this.find { it.id == gymId }?.isFavorites = true  //update isFavorites from var to val
//            }
//            return gymsMap.values.toList()
//        }
//        return this
//    }


//    companion object {
//        const val FAV_ID = "favoritesGymsIDs"
//    }
}