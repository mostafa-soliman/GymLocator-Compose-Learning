package com.example.gymlocator

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Gym::class],
    version = 2,
    exportSchema = false
)
abstract class GymsDatabase : RoomDatabase() {
    abstract val dao:GymsDAO
    companion object{
        @Volatile
        private var daoInstant :GymsDAO? = null
        private fun buildDatabase(context :Context) :GymsDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                GymsDatabase::class.java,
                "gyms_database"  // Database Name
            ).fallbackToDestructiveMigration().build()
        }
        fun getDaoInstant(context: Context):GymsDAO{
            synchronized(this) {
                if (daoInstant == null) {
                    daoInstant = buildDatabase(context).dao
                }
                return daoInstant as GymsDAO
            }
        }
    }

}