package com.example.starwarsapi_paging3_roomdb.viewmodel

import androidx.lifecycle.ViewModel
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.repositories.StarWarRepositoryImpl
import com.example.starwarsapi_paging3_roomdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val starWarRepositoryImpl: StarWarRepositoryImpl
) : ViewModel() {

    fun getPerson(url: String): Flow<Resource<PeopleResponseEntity?>> {
        return flow {
            try {
                emit(starWarRepositoryImpl.getPerson(url))
            } catch (e: Exception) {
                emit(Resource.error("Exception occurred: ${e.message}", null))
            }
        }.flowOn(Dispatchers.IO)
    }
}

