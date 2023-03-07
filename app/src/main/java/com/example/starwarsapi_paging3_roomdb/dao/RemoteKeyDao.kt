package com.example.starwarsapi_paging3_roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseRemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(list: List<PeopleResponseRemoteKey>)

    @Query("SELECT * FROM PeopleResponseRemoteKey WHERE id = :id")
    suspend fun getPeopleRemoteKeys(id: Int): PeopleResponseRemoteKey

    @Query("DELETE FROM PeopleResponseRemoteKey")
    suspend fun deleteRemoteKeys()
}