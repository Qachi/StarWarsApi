package com.example.starwarsapi_paging3_roomdb.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class PeopleResponseEntity(
    @SerializedName("_id") val id: Int,
    val name: String,
    val birth_year: String,
    val eye_color: String,
    val films: List<String>,
    val gender: String,
    @PrimaryKey(autoGenerate = false) val url: String
):Parcelable


