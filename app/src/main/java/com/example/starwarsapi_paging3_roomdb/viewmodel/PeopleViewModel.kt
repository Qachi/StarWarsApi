package com.example.starwarsapi_paging3_roomdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.repositories.StarWarRepository
import com.example.starwarsapi_paging3_roomdb.util.Resource
import com.example.starwarsapi_paging3_roomdb.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val starWarRepository: StarWarRepository
) : ViewModel() {

    val getPeople: Flow<PagingData<PeopleResponseEntity>> = starWarRepository.getPeople()
    fun insertPersonIntoDB(person: PeopleResponseEntity) = viewModelScope.launch {
        starWarRepository.insertPerson(person)
    }

    fun insertPeople(list: List<PeopleResponseEntity>) = viewModelScope.launch {
        starWarRepository.insertPeople(list)
    }

    fun deleteCharacters() = viewModelScope.launch {
        starWarRepository.deletePeople()
    }

    fun getPerson(url: String) = viewModelScope.launch {
        val resource = starWarRepository.getPerson(url)
        when (resource.status) {
            Status.SUCCESS -> {
                val data = resource.data
                Resource.success(data)
            }
            Status.ERROR -> {
                val errorMessage = resource.message
                if (errorMessage != null) {
                    Resource.error(errorMessage, null)
                } else {
                    Resource.error("An unknown error occurred", null)
                }
            }
            Status.LOADING -> {
                val data = resource.data
                Resource.loading(data)
            }
        }
    }
}