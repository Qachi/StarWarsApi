package com.example.starwarsapi_paging3_roomdb.di

import android.content.Context
import androidx.room.Room
import com.example.starwarsapi_paging3_roomdb.database.StarWarDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Named("test_db")
    fun provideInMemoryDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,
            StarWarDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
}