package com.munny.nearplacecategory.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.munny.nearplacecategory.model.Place

@Entity
data class PlaceEntity(
    @PrimaryKey
    val id: Long,
    val mills: Long,
    val place: Place
)

class PlaceEntityTypeConverter {
    @TypeConverter
    fun toPlace(str: String): Place {
        return Gson().fromJson(str, Place::class.java)
    }

    @TypeConverter
    fun toMap(place: Place): String {
        return Gson().toJson(place)
    }
}