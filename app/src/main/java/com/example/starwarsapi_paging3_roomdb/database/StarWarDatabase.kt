package com.example.starwarsapi_paging3_roomdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.starwarsapi_paging3_roomdb.dao.RemoteKeyDao
import com.example.starwarsapi_paging3_roomdb.dao.StarWarDao
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseRemoteKey
import com.example.starwarsapi_paging3_roomdb.model.RoomTypeConverter

@Database(
    entities = [PeopleResponseEntity::class,
        PeopleResponseRemoteKey::class],
    version = 4
)
@TypeConverters(RoomTypeConverter::class)
abstract class StarWarDatabase : RoomDatabase() {
    abstract fun getStarWarDao(): StarWarDao
    abstract fun getRemoteKeyDao(): RemoteKeyDao
}