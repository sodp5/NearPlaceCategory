package com.munny.nearplacecategory.di

import android.content.Context
import androidx.room.Room
import com.munny.nearplacecategory.db.AppDatabase
import com.munny.nearplacecategory.db.PlaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun providePlaceDao(appDataBase: AppDatabase): PlaceDao {
        return appDataBase.placeDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}