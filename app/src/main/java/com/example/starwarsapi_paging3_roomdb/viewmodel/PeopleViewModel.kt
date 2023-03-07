package com.example.starwarsapi_paging3_roomdb.viewmodel

import androidx.lifecycle.ViewModel
import com.example.starwarsapi_paging3_roomdb.repository.StarWarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
     starWarRepository: StarWarRepository
) : ViewModel() {

    val getPeople = starWarRepository.getPeople()

}