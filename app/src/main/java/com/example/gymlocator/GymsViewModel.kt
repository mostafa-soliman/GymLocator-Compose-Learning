package com.example.gymlocator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GymsViewModel() :ViewModel() {
    var state by mutableStateOf(getGyms())

    private fun getGyms() = gymsList
    
    fun toggleFavoritesState(gymId:Int) {
        val grms = state.toMutableList()  //Copy from state list
        val itemIndex = gymsList.indexOfFirst { it.id == gymId } // Make sure the "id" is equal
        grms[itemIndex] =
            grms[itemIndex].copy(isFavorites = !gymsList[itemIndex].isFavorites) //update value in list
        state = grms // update state
    }
}