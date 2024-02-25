package com.example.starwarsapi_paging3_roomdb.model

import androidx.room.TypeConverter

class RoomTypeConverter {
    @TypeConverter
    fun fromFilms(films: List<String>) = films.joinToString()

    @TypeConverter
    fun toFilms(films: String) = films.split(",")
}