package com.example.gymlocator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "gyms")
data class Gym(
    @PrimaryKey
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @ColumnInfo(name = "gym_name")
    @SerializedName("gym_name")
    val name: String,
    @ColumnInfo(name = "gym_location")
    @SerializedName("gym_location")
    val location: String,
    @SerializedName("is_open")
    val isOpen:Boolean,
    @ColumnInfo(name = "is_favorites")
    val isFavorites: Boolean = false
)


/*
data class GymApiService(
    @SerializedName("gym_location")
    val gymLocation: String,
    @SerializedName("gym_name")
    val gymName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_open")
    val isOpen: Boolean
)
* */


//
//val gymsList = listOf(
//    Gym(id= 0,name = "FitZone", location = "123 Main St, Cairo"),
//    Gym(id= 1,name = "PowerHouse Gym", location = "456 Elm St, Alexandria"),
//    Gym(id= 2,name = "Elite Fitness", location = "789 Oak St, Giza"),
//    Gym(id= 3,name = "Muscle Factory", location = "101 Pine St, Mansoura"),
//    Gym(id= 4,name = "Iron Paradise", location = "202 Cedar St, Tanta"),
//    Gym(id= 5,name = "Gold's Gym", location = "303 Maple St, Assiut"),
//    Gym(id= 6,name = "Titan Gym", location = "404 Birch St, Sohag"),
//    Gym(id= 7,name = "Fitness First", location = "505 Ash St, Hurghada"),
//    Gym(id= 8,name = "Body Builders", location = "606 Palm St, Sharm El Sheikh"),
//    Gym(id= 9,name = "Pro Gym", location = "707 Willow St, Port Said"),
//    Gym(id= 10,name = "Next Level Gym", location = "808 Spruce St, Suez"),
//    Gym(id= 11,name = "The Gym", location = "909 Cypress St, Ismailia"),
//    Gym(id= 12,name = "Active Gym", location = "1010 Poplar St, Fayoum"),
//    Gym(id= 13,name = "Max Fitness", location = "1111 Aspen St, Luxor"),
//    Gym(id= 14,name = "Shape Up", location = "1212 Cherry St, Minya")
//)

