package com.example.gymlocator.gyms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GymsDAO {
    @Query("SELECT * from gyms")
    suspend fun getAll():List<LocalGym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gyms : List<LocalGym>)

    @Update(entity = LocalGym::class)
    suspend fun update(gymFavouriteState: LocalGymFavouriteState)
    @Query("SELECT * from gyms where is_favorites = 1")
    suspend fun getFavouriteGyms(): List<LocalGym>
    @Update(entity = LocalGym::class)
    suspend fun updateAll(gymState: List<LocalGymFavouriteState>)


}