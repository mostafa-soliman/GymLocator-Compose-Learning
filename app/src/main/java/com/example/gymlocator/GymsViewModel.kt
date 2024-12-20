package com.example.gymlocator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class GymsViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(restoreSelectedGym())

    private fun getGyms() = gymsList

    fun toggleFavoritesState(gymId: Int) {
        val gyms = state.toMutableList()  //Copy from state list
        val itemIndex = gymsList.indexOfFirst { it.id == gymId } // Make sure the "id" is equal
        gyms[itemIndex] =
            gyms[itemIndex].copy(isFavorites = !gymsList[itemIndex].isFavorites) //update value in list
        storeSelectedGym(gyms[itemIndex])
        state = gyms // update state
    }

    fun storeSelectedGym(gym: Gym) {
        val savedHandleList = stateHandle.get<List<Int>>(FAV_ID).orEmpty().toMutableList()
        if (gym.isFavorites) savedHandleList.add(gym.id)
        else savedHandleList.remove(gym.id)
        stateHandle[FAV_ID] = savedHandleList
    }

    fun restoreSelectedGym() : List<Gym> {
        val gyms = getGyms()
        stateHandle.get<List<Int>>(FAV_ID)?.let { savedIDs ->
            savedIDs.forEach { gymId ->
                gyms.find { it.id == gymId }?.isFavorites =true
            }
        }
        return gyms
    }


    companion object {
        const val FAV_ID = "favoritesGymsIDs"
    }
}