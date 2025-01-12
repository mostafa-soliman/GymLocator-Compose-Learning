package com.example.gymlocator.gyms.domain

import com.example.gymlocator.gyms.data.GymsRepository
import javax.inject.Inject

class GetInitialGymsUseCase @Inject constructor(
    private var gymsRepository : GymsRepository,
    private var getSortedGymsUseCase : GetSortedGymsUseCase
) {

    suspend operator fun invoke(): List<Gym> {
        gymsRepository.loadGyms()
        return getSortedGymsUseCase()
    }

}