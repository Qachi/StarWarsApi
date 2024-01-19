package com.example.starwarsapi_paging3_roomdb.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.starwarsapi_paging3_roomdb.api.StarWarsApi
import com.example.starwarsapi_paging3_roomdb.database.StarWarDatabase
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseRemoteKey
import java.io.InvalidObjectException

@ExperimentalPagingApi
class StarWarRemoteMediator(
    private val starWarsApi: StarWarsApi,
    private val starWarDatabase: StarWarDatabase,
    private val initialPage: Int = 1,
):RemoteMediator<Int, PeopleResponseEntity>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PeopleResponseEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.APPEND -> {
                    val remoteKey = getLastRemoteKey(state)
                        ?: throw InvalidObjectException("InvalidObjectException")
                    remoteKey.next ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.PREPEND -> {
                    val remoteKey = getFirstRemoteKey(state)
                        ?: throw InvalidObjectException("InvalidObjectException")
                    remoteKey.prev ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.REFRESH -> {
                    val remoteKey = getClosestRemoteKeys(state)
                    remoteKey?.next?.minus(1) ?: initialPage
                }
            }
            val response = starWarsApi.getPeople()
            val endOfPagination = response.body()?.results?.size!! < state.config.pageSize

            if (response.isSuccessful) {
                response.body()?.let {
                    if (loadType == LoadType.REFRESH) {
                        starWarDatabase.getStarWarDao().deletePeople()
                        starWarDatabase.getRemoteKeyDao().deleteRemoteKeys()
                    }
                    val prev = if (page == initialPage) null else page.minus(1)
                    val next = if (endOfPagination) null else page.plus(1)

                    val list = response.body()?.results?.map {
                        PeopleResponseRemoteKey(it.url, prev, next)
                    }
                    if (list != null) {
                        starWarDatabase.getStarWarDao().insertPeople(response.body()?.results?.map {
                            it.toPeopleResponseEntity()
                        }!!)
                    }
                    if (list != null) {
                        starWarDatabase.getRemoteKeyDao().insertAllRemoteKeys(list)
                    }
                }
                MediatorResult.Success(endOfPagination)
            } else {
                MediatorResult.Success(endOfPaginationReached = true)
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
    private suspend fun getClosestRemoteKeys(state: PagingState<Int, PeopleResponseEntity>): PeopleResponseRemoteKey? {
        return state.anchorPosition?.let { it ->
            state.closestItemToPosition(it)?.let {
                starWarDatabase.getRemoteKeyDao().getPeopleRemoteKeys(it.id)
            }
        }
    }
    private suspend fun getLastRemoteKey(state: PagingState<Int, PeopleResponseEntity>): PeopleResponseRemoteKey? {
        return state.lastItemOrNull()?.let {
            starWarDatabase.getRemoteKeyDao().getPeopleRemoteKeys(it.id)
        }
    }
    private suspend fun getFirstRemoteKey(state: PagingState<Int, PeopleResponseEntity>): PeopleResponseRemoteKey? {
        return state.firstItemOrNull()?.let {
            starWarDatabase.getRemoteKeyDao().getPeopleRemoteKeys(it.id)
        }
    }
}