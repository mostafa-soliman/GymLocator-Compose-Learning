package com.example.gymlocator.gyms.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalGym::class],
    version = 3,
    exportSchema = false
)
abstract class GymsDatabase : RoomDatabase() {
    abstract val dao: GymsDAO
//    companion object{
//        @Volatile
//        private var daoInstant : GymsDAO? = null
////        private fun buildDatabase(context :Context) : GymsDatabase {
////            return Room.databaseBuilder(
////                context.applicationContext,
////                GymsDatabase::class.java,
////                "gyms_database"  // Database Name
////            ).fallbackToDestructiveMigration().build()
////        }
////        fun getDaoInstant(context: Context): GymsDAO {
////            synchronized(this) {
////                if (daoInstant == null) {
////                    daoInstant = buildDatabase(context).dao
////                }
////                return daoInstant as GymsDAO
////            }
////        }
//    }

}