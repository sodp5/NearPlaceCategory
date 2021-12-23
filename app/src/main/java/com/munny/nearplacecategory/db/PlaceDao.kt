package com.munny.nearplacecategory.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.munny.nearplacecategory.db.entity.PlaceEntity
import com.munny.nearplacecategory.model.Place

@Dao
abstract class PlaceDao {
    @Insert
    abstract suspend fun insertPlace(placeEntity: PlaceEntity)

    @Query("DELETE FROM PlaceEntity WHERE id = :placeId")
    abstract suspend fun deletePlace(placeId: Long)

    @Query("SELECT * FROM PlaceEntity")
    abstract suspend fun getAllPlace(): List<PlaceEntity>
}