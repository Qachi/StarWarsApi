package com.example.starwarsapi_paging3_roomdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.repositories.StarWarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    starWarRepository: StarWarRepository
) : ViewModel() {

    val getPeople: Flow<PagingData<PeopleResponseEntity>> = starWarRepository.getPeople()
}