package com.example.gymlocator.gyms.persentation.gymsList

import com.example.gymlocator.gyms.domain.Gym

data class GymsScreenState(
    val gyms: List<Gym>,
    val isLoading : Boolean,
    val error : String? = null
)
