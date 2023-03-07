package com.example.starwarsapi_paging3_roomdb.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class People(
    val id: Int,
    val name: String,
    val birth_year: String,
    val eye_color: String,
    val films: List<String>,
    val gender: String,
    val url: String

) : Parcelable
