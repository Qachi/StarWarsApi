package com.example.starwarsapi_paging3_roomdb.di

import android.content.Context
import androidx.room.Room
import com.example.starwarsapi_paging3_roomdb.api.StarWarsApi
import com.example.starwarsapi_paging3_roomdb.dao.StarWarDao
import com.example.starwarsapi_paging3_roomdb.database.StarWarDatabase
import com.example.starwarsapi_paging3_roomdb.repositories.StarWarRepository
import com.example.starwarsapi_paging3_roomdb.repositories.StarWarRepositoryImpl
import com.example.starwarsapi_paging3_roomdb.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object StarWarModule {

    @[Singleton Provides]
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @[Singleton Provides]
    fun provideStarWarsApi(retrofit: Retrofit): StarWarsApi =
        retrofit.create(StarWarsApi::class.java)

    @[Singleton Provides]
    fun provideStarWarDatabase(@ApplicationContext context: Context): StarWarDatabase =
        Room.databaseBuilder(
            context,
            StarWarDatabase::class.java,
            "user_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @[Singleton Provides]
    fun provideStarWarRepositoryImpl(
        starWarsApi: StarWarsApi,
        starWarDao: StarWarDao,
        starWarDatabase: StarWarDatabase
    ) = StarWarRepositoryImpl(
        starWarsApi,
        starWarDao,
        starWarDatabase
    ) as StarWarRepository

    @[Singleton Provides]
    fun provideStarWarDao(starWarDatabase: StarWarDatabase): StarWarDao =
        starWarDatabase.getStarWarDao()
}