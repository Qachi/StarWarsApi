package com.example.starwarsapi_paging3_roomdb.viewmodel

import androidx.lifecycle.ViewModel
import com.example.starwarsapi_paging3_roomdb.repository.StarWarRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val starWarRepositoryImpl: StarWarRepositoryImpl
) : ViewModel() {


    suspend fun getPerson(url: String) = withContext(Dispatchers.IO) {
   starWarRepositoryImpl.getPerson(url)
    }
}

