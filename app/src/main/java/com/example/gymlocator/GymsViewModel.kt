package com.example.gymlocator

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GymsViewModel(
    // private val stateHandle: SavedStateHandle  //because used local database  || Room DB
) : ViewModel() {
    private var _state by mutableStateOf(GymsScreenState(gyms = emptyList(), isLoading = true))
    val state : State<GymsScreenState>
        get() = derivedStateOf {_state}

    private val repo = GymRepository()
    // instant of API Service
    private val errorHandle =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            _state = _state.copy(
                isLoading = false,
                error = throwable.message)
        }
//    private lateinit var gyms  :Call<List<Gym>>

//    val job = Job()
//    val scope = CoroutineScope(context = job + Dispatchers.IO)

    init {

        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(Dispatchers.IO + errorHandle) {
            val receivedGym = repo.getGymsFromRemoteDB()
            _state = _state.copy(
                gyms = receivedGym,
                isLoading = false
            )
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


    fun toggleFavoritesState(gymId: Int) {
        val gyms = _state.gyms.toMutableList()  //Copy from state list
        val itemIndex = gyms.indexOfFirst { it.id == gymId } // Make sure the "id" is equal
        //  because used local database
        //        gyms[itemIndex] =
        //            gyms[itemIndex].copy(isFavorites = !gyms[itemIndex].isFavorites) //update value in list
        //        storeSelectedGym(gyms[itemIndex])
        //  state = gyms // update state
        viewModelScope.launch {
            val updateGymsList = repo.toggleFavoriteGym(gymId, !gyms[itemIndex].isFavorites)
            _state = _state.copy(gyms = updateGymsList)
        }
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