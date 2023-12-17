package com.example.starwarsapi_paging3_roomdb.repositories

import androidx.paging.PagingData
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface StarWarRepository {
    suspend fun insertPeople(list: List<PeopleResponseEntity>)
    suspend fun insertPerson(person: PeopleResponseEntity)
    fun getPeople(): Flow<PagingData<PeopleResponseEntity>>
    suspend fun getPerson(url: String): Resource<PeopleResponseEntity?>
    suspend fun deletePeople()
}

