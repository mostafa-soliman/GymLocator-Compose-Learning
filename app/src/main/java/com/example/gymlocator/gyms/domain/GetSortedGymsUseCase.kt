package com.example.gymlocator.gyms.domain

import com.example.gymlocator.gyms.data.GymsRepository

class GetSortedGymsUseCase {
    private var gymsRepository = GymsRepository()
    suspend operator fun invoke(): List<Gym> {
        return gymsRepository.getGyms().sortedBy { it.name }

    }
}