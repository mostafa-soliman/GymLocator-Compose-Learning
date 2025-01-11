package com.example.gymlocator.gyms.domain

import com.example.gymlocator.gyms.data.GymsRepository

class ToggleFavouriteStateUseCase {

    private var gymsRepository = GymsRepository()
    private var getSortedGymsUseCase = GetSortedGymsUseCase()
    suspend operator fun invoke(id: Int, oldState: Boolean): List<Gym> {
        val newState = oldState.not()
        gymsRepository.toggleFavoriteGym(id, newState)
        return getSortedGymsUseCase()
    }
}