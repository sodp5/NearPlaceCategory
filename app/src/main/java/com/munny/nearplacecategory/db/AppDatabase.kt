package com.munny.nearplacecategory.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.munny.nearplacecategory.db.entity.PlaceEntity
import com.munny.nearplacecategory.db.entity.PlaceEntityTypeConverter

@Database(entities = [PlaceEntity::class], version = 1)
@TypeConverters(PlaceEntityTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}