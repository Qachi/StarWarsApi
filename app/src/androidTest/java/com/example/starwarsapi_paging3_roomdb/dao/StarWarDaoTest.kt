package com.example.starwarsapi_paging3_roomdb.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import androidx.test.filters.SmallTest
import com.example.starwarsapi_paging3_roomdb.database.StarWarDatabase
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class StarWarDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var starWarDatabase: StarWarDatabase
    private lateinit var starWarDao: StarWarDao

    @Before
    fun setUp() {
        hiltRule.inject()
        starWarDao = starWarDatabase.getStarWarDao()
    }

    @After
    fun tearDown() {
        starWarDatabase.close()
    }

    @Test
    fun insertPerson() = runBlocking {
        val personID = 1
        val url = "https://swapi.dev/api/people/1/"
        val films = listOf(
            " https://swapi.dev/api/films/1/",
            " https://swapi.dev/api/films/2/",
            " https://swapi.dev/api/films/3/",
            " https://swapi.dev/api/films/6/"
        )
        val person = PeopleResponseEntity(
            id = personID,
            name = "Luke Skywalker",
            birth_year = "19BBY",
            eye_color = "blue",
            films = films,
            gender = "male",
            url = url
        )
        starWarDao.insertPerson(person)
        val starWarPerson = starWarDao.getPerson(url)
        assertNotNull(starWarPerson)
        assertEquals(person.id, starWarPerson?.id)
        assertEquals(person.name, starWarPerson?.name)
        assertEquals(person.birth_year, starWarPerson?.birth_year)
        assertEquals(person.eye_color, starWarPerson?.eye_color)
        assertEquals(person.gender, starWarPerson?.gender)
        assertEquals(person.url, starWarPerson?.url)
        assertArrayEquals(
            person.films.map { it.trim() }.toTypedArray(),
            starWarPerson?.films?.map { it.trim() }?.toTypedArray()
        )
    }

    @Test
    fun deletePeople() = runBlocking {
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
        starWarDao.insertPeople(listOf(person1, person2))
        starWarDao.deletePeople()
        val starWarPerson1 = starWarDao.getPerson(url)
        val starWarPerson2 = starWarDao.getPerson(url2)
        assertNull(starWarPerson1)
        assertNull(starWarPerson2)
    }

    // Trying to test the getPeople(): PagingData(Test fails with no error msg)
    @Test
    fun getPeopleTest() = runTest {
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
        val testData = listOf(
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
        starWarDatabase.getStarWarDao().insertPeople(testData)

        // Act: Retrieve data using getPeople()
        val pagingSource = starWarDatabase.getStarWarDao().getPeople()

        // Assert: Verify that the retrieved data matches the inserted data
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(1, 3, false)
        )
        assert(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assert(pageResult.data == testData)
    }
}

