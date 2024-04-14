package com.example.starwarsapi_paging3_roomdb.data

import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity

object TestData {

    private const val personID = 1
    private const val url = "https://swapi.dev/api/people/1/"
    private val films1 = listOf(
        "https://swapi.dev/api/films/1/",
        "https://swapi.dev/api/films/2/",
        "https://swapi.dev/api/films/3/",
        "https://swapi.dev/api/films/6/"
    )
    private const val personID2 = 2
    private const val url2 = "https://swapi.dev/api/people/2/"
    private val films2 = listOf(
        "https://swapi.dev/api/films/1/",
        "https://swapi.dev/api/films/2/",
        "https://swapi.dev/api/films/3/",
        "https://swapi.dev/api/films/4/",
        "https://swapi.dev/api/films/5/",
        "https://swapi.dev/api/films/6/"
    )
    val personLukeSkywalker = PeopleResponseEntity(
        id = personID,
        name = "Luke Skywalker",
        birth_year = "19BBY",
        eye_color = "blue",
        films = films1,
        gender = "male",
        url = url
    )

    val personC3PO = PeopleResponseEntity(
        id = personID2,
        name = "C-3PO",
        birth_year = "112BBY",
        eye_color = "yellow",
        films = films2,
        gender = "n/a",
        url = url2
    )
}