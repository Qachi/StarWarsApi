package com.example.starwarsapi_paging3_roomdb.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity

@Dao
interface StarWarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeople(list: List<PeopleResponseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PeopleResponseEntity)

    @Query("SELECT * FROM user_table")
    fun getPeople(): PagingSource<Int, PeopleResponseEntity>

    @Query("SELECT * FROM user_table WHERE url == :url LIMIT 1")
    fun getPerson(url: String): PeopleResponseEntity?

    @Query("DELETE FROM user_table")
    suspend fun deletePeople()
}