package com.example.starwarsapi_paging3_roomdb.repository

import androidx.paging.*
import com.example.starwarsapi_paging3_roomdb.api.StarWarsApi
import com.example.starwarsapi_paging3_roomdb.database.StarWarDatabase
import com.example.starwarsapi_paging3_roomdb.model.People
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.remote.StarWarRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class StarWarRepositoryImpl @Inject constructor(
    private val starWarApi: StarWarsApi,
    private val starWarDatabase: StarWarDatabase

) : StarWarRepository {

    override fun getPeople(): Flow<PagingData<People>> {
        val starWarPagingSource = { starWarDatabase.getStarWarDao().getPeople() }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = true
            ),
            remoteMediator = StarWarRemoteMediator(starWarApi, starWarDatabase, 1),
            pagingSourceFactory = starWarPagingSource
        ).flow.map { PeopleEntityPagingData ->
            PeopleEntityPagingData.map { PeopleEntity ->
                PeopleEntity.toPeople()
            }
        }

    }


     fun getPerson(url: String): Flow<PeopleResponseEntity> {

        return flow {
            val starWarPagingSource = starWarDatabase.getStarWarDao().getPerson(url)
            if (starWarPagingSource != null) {
                emit(starWarPagingSource)

            } else {
                val people = starWarApi.getPerson(url).body()
                emit(people!!)

                starWarDatabase.getStarWarDao().insertPerson(people)
            }

        }.flowOn(Dispatchers.IO)
    }


}
