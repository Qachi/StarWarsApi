package com.example.starwarsapi_paging3_roomdb.api

import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseDto
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface StarWarsApi {

    @GET("people")
    suspend fun getPost(): Response<PeopleResponseDto>

    @GET
    suspend fun getPerson(@Url url: String): Response<PeopleResponseEntity>
}