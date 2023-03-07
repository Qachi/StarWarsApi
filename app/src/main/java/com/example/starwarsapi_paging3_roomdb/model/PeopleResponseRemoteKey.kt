package com.example.starwarsapi_paging3_roomdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PeopleResponseRemoteKey(
    @PrimaryKey(autoGenerate = false) val id: String,
    val prev: Int?,
    val next: Int?
)