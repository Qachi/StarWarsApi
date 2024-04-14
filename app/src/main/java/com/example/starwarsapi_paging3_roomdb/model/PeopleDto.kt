package com.example.starwarsapi_paging3_roomdb.model

data class PeopleDto(
    val id: Int,
    val name: String,
    val birth_year: String,
    val eye_color: String,
    val films: List<String>,
    val gender: String,
    val url: String
) {
    fun toPeopleResponseEntity() = PeopleResponseEntity(
        id = id,
        name = name,
        birth_year = birth_year,
        eye_color = eye_color,
        films = films,
        gender = gender,
        url = url
    )
}
