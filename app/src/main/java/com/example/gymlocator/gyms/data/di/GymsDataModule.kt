package com.example.gymlocator.gyms.data.di

import android.content.Context
import androidx.room.Room
import com.example.gymlocator.gyms.data.local.GymsDAO
import com.example.gymlocator.gyms.data.local.GymsDatabase
import com.example.gymlocator.gyms.data.remote.GymApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GymsDataModule {

    @Provides
    fun providesApiService(retrofit: Retrofit):GymApiService{
        return retrofit.create(GymApiService::class.java)
    }
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            ).baseUrl("https://cairo-gyms-3b76a-default-rtdb.firebaseio.com/")
            .build()
    }

    @Provides
    fun providesRoomDao(
        db: GymsDatabase
    ): GymsDAO {
        return db.dao
    }

    @Singleton
    @Provides
    fun providesDataBase(
        @ApplicationContext context: Context
    ): GymsDatabase {
        return Room.databaseBuilder(
            context,
            GymsDatabase::class.java,
            "gyms_database"  // Database Name
        ).fallbackToDestructiveMigration().build()
    }
}