package com.example.starwarsapi_paging3_roomdb.repositories

import androidx.paging.PagingData
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeStarWarRepository : StarWarRepository {

    private val personID = 1
    private val url = "https://swapi.dev/api/people/1/"
    private val films1 = listOf(
        "https://swapi.dev/api/films/1/",
        "https://swapi.dev/api/films/2/",
        "https://swapi.dev/api/films/3/",
        "https://swapi.dev/api/films/6/"
    )
    private val personID2 = 2
    private val url2 = "https://swapi.dev/api/people/2/"
    private val films2 = listOf(
        "https://swapi.dev/api/films/1/",
        "https://swapi.dev/api/films/2/",
        "https://swapi.dev/api/films/3/",
        "https://swapi.dev/api/films/4/",
        "https://swapi.dev/api/films/5/",
        "https://swapi.dev/api/films/6/"
    )
    private val starWarCharacters: MutableList<PeopleResponseEntity> = mutableListOf(
        PeopleResponseEntity(
            id = personID,
            name = "Luke Skywalker",
            birth_year = "19BBY",
            eye_color = "blue",
            films = films1,
            gender = "male",
            url = url.trim()
        ),
        PeopleResponseEntity(
            id = personID2,
            name = "C-3PO",
            birth_year = "112BBY",
            eye_color = "yellow",
            films = films2,
            gender = "n/a",
            url = url2.trim()
        )
    )

    override suspend fun insertPeople(list: List<PeopleResponseEntity>) {
        starWarCharacters.addAll(list)
    }

    override suspend fun insertPerson(person: PeopleResponseEntity) {
        starWarCharacters.add(person)
    }

    override fun getPeople(): Flow<PagingData<PeopleResponseEntity>> {
        return flowOf(PagingData.from(starWarCharacters))
    }

    override suspend fun getPerson(url: String): Resource<PeopleResponseEntity?> {
        return try {
            val person = starWarCharacters.find { it.url == url }
            if (person != null) {
                Resource.success(person)
            } else {
                Resource.error("person not found", null)
            }
        } catch (e: Exception) {
            Resource.error("An error occurred: ${e.message}", null)
        }
    }

    override suspend fun deletePeople() {
        starWarCharacters.clear()
    }
}