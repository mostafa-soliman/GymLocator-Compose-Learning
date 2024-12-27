package com.example.gymlocator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GymFavouriteState(
    @PrimaryKey
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @ColumnInfo(name = "is_favorites")
    val isFavorites: Boolean = false
)
