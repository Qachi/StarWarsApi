package com.example.starwarsapi_paging3_roomdb.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.ListUpdateCallback
import com.MainCoroutineRule
import com.example.starwarsapi_paging3_roomdb.adapter.StarWarAdapter
import com.example.starwarsapi_paging3_roomdb.data.listOfCharacters
import com.example.starwarsapi_paging3_roomdb.repositories.FakeStarWarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StarWarViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var sut: PeopleViewModel
    private lateinit var fakeStarWarRepository: FakeStarWarRepository

    @Before
    fun setup() {
        fakeStarWarRepository = FakeStarWarRepository()
        sut = PeopleViewModel(fakeStarWarRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchAllCharacters() = runTest {

        val differ = AsyncPagingDataDiffer(
            diffCallback = StarWarAdapter.diffUtil,
            updateCallback = ListUpdateTestCallback(),
            workerDispatcher = Dispatchers.Main
        )
        val pagingData = sut.getPeople.first()
        differ.submitData(pagingData)
        advanceUntilIdle()
        assertEquals(listOfCharacters.map { it.id },differ.snapshot().items.map { it.id
        })
    }

    class ListUpdateTestCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}


    




