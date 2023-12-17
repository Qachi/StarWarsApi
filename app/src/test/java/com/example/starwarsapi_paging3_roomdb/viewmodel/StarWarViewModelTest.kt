package com.example.starwarsapi_paging3_roomdb.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.MainCoroutineRule
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.repositories.FakeStarWarRepository
import com.example.starwarsapi_paging3_roomdb.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StarWarViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var sut: PeopleViewModel
    private lateinit var fakeStarWarRepository: FakeStarWarRepository

    @Before
    fun setup() {
        fakeStarWarRepository = FakeStarWarRepository()
        sut = PeopleViewModel(fakeStarWarRepository)
    }

    @Test
    fun insertCharacterIntoDB() = runTest {
        val personID = 1
        val url = "https://swapi.dev/api/people/1/"
        val films1 = listOf(
            "https://swapi.dev/api/films/1/",
            "https://swapi.dev/api/films/2/",
            "https://swapi.dev/api/films/3/",
            "https://swapi.dev/api/films/6/"
        )
        val character = PeopleResponseEntity(
            id = personID,
            name = "Luke Skywalker",
            birth_year = "19BBY",
            eye_color = "blue",
            films = films1,
            gender = "male",
            url = url.trim()
        )
        sut.insertPersonIntoDB(character)
        val resource = fakeStarWarRepository.getPerson(url)
        assertNotNull(resource)
        assertEquals(Status.SUCCESS, resource.status)
        val insertedCharacter = resource.data
        assertNotNull(insertedCharacter)
        assertEquals(url, insertedCharacter?.url)
    }

    @Test
    fun deleteCharacters() = runTest {
        val personID = 1
        val url = "https://swapi.dev/api/people/1/"
        val films1 = listOf(
            "https://swapi.dev/api/films/1/",
            "https://swapi.dev/api/films/2/",
            "https://swapi.dev/api/films/3/",
            "https://swapi.dev/api/films/6/"
        )
        val personID2 = 2
        val url2 = "https://swapi.dev/api/people/2/"
        val films2 = listOf(
            "https://swapi.dev/api/films/1/",
            "https://swapi.dev/api/films/2/",
            "https://swapi.dev/api/films/3/",
            "https://swapi.dev/api/films/4/",
            "https://swapi.dev/api/films/5/",
            "https://swapi.dev/api/films/6/"
        )
        val person1 = PeopleResponseEntity(
            id = personID,
            name = "Luke Skywalker",
            birth_year = "19BBY",
            eye_color = "blue",
            films = films1,
            gender = "male",
            url = url.trim()
        )
        val person2 = PeopleResponseEntity(
            id = personID2,
            name = "C-3PO",
            birth_year = "112BBY",
            eye_color = "yellow",
            films = films2,
            gender = "n/a",
            url = url2.trim()
        )
        sut.insertPeople(listOf(person1, person2))
        sut.deleteCharacters()
        val getPerson = fakeStarWarRepository.getPerson(url)
        val getPerson2 = fakeStarWarRepository.getPerson(url2)
        assertTrue(getPerson.data == null)
        assertTrue(getPerson2.data == null)
    }
}


    




