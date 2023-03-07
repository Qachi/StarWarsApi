package com.example.starwarsapi_paging3_roomdb.repository

import androidx.paging.PagingData
import com.example.starwarsapi_paging3_roomdb.model.People
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

interface StarWarRepository {
    fun getPeople(): Flow<PagingData<People>>

}

@Module
@InstallIn(SingletonComponent::class)
abstract class StarWarRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindStarWarRepository(starWarRepositoryImpl: StarWarRepositoryImpl): StarWarRepository

}
