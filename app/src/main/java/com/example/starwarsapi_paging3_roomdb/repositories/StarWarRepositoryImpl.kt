package com.example.starwarsapi_paging3_roomdb.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.starwarsapi_paging3_roomdb.api.StarWarsApi
import com.example.starwarsapi_paging3_roomdb.dao.StarWarDao
import com.example.starwarsapi_paging3_roomdb.database.StarWarDatabase
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.remote.StarWarRemoteMediator
import com.example.starwarsapi_paging3_roomdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class StarWarRepositoryImpl @Inject constructor(
    private val starWarApi: StarWarsApi,
    private val starWarDao: StarWarDao,
    private val starWarDatabase: StarWarDatabase
) : StarWarRepository {

    override suspend fun insertPeople(list: List<PeopleResponseEntity>) {
        starWarDao.insertPeople(list)
    }

    override suspend fun insertPerson(person: PeopleResponseEntity) {
        starWarDao.insertPerson(person)
    }

    override fun getPeople(): Flow<PagingData<PeopleResponseEntity>> {
        val starWarPagingSource = { starWarDatabase.getStarWarDao().getPeople() }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = true,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MAX_VALUE,
            ),
            remoteMediator = StarWarRemoteMediator(starWarApi, starWarDatabase, 1),
            pagingSourceFactory = starWarPagingSource
        ).flow.map { peopleEntityPagingData ->
            peopleEntityPagingData.map { peopleEntity ->
                PeopleResponseEntity(
                    id = peopleEntity.id,
                    name = peopleEntity.name,
                    birth_year = peopleEntity.birth_year,
                    eye_color = peopleEntity.eye_color,
                    films = peopleEntity.films,
                    gender = peopleEntity.gender,
                    url = peopleEntity.url
                )
            }
        }
    }

    override suspend fun getPerson(url: String): Resource<PeopleResponseEntity?> {
        return try {
            val starWarPagingSource = starWarDatabase.getStarWarDao().getPerson(url)
            if (starWarPagingSource != null) {
                Resource.success(starWarPagingSource)
            } else {
                val response = starWarApi.getPerson(url)
                if (response.isSuccessful) {
                    val networkPerson = response.body()
                    if (networkPerson != null) {
                        starWarDatabase.getStarWarDao().insertPerson(networkPerson)
                        Resource.success(networkPerson)
                    } else {
                        Resource.error("Failed to parse person response", null)
                    }
                } else {
                    Resource.error("Network request failed", null)
                }
            }
        } catch (e: Exception) {
            Resource.error("Exception occurred: ${e.message}", null)
        }
    }

    override suspend fun deletePeople() {
        starWarDao.deletePeople()
    }
}
