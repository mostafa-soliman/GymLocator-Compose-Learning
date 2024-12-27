package com.example.gymlocator

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GymsDAO {
    @Query("SELECT * from gyms")
    suspend fun getAll():List<Gym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gyms : List<Gym>)

    @Update(entity = Gym::class)
    suspend fun update(gymFavouriteState: GymFavouriteState)
    @Query("SELECT * from gyms where is_favorites = 1")
    suspend fun getFavouriteGyms(): List<Gym>
    @Update(entity = Gym::class)
    suspend fun updateAll(gymState: List<GymFavouriteState>)


}