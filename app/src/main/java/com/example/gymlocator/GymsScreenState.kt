package com.example.gymlocator

import com.example.gymlocator.Gym

data class GymsScreenState(
    val gyms: List<Gym>,
    val isLoading : Boolean,
    val error : String? = null
)
