package com.example.starwarsapi_paging3_roomdb.model

import com.google.gson.annotations.SerializedName

data class PeopleResponseDto(

    @SerializedName("info")
    val info: InfoDto,

    @SerializedName("results")
    val results: List<PeopleDto>
)
