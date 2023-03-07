package com.example.starwarsapi_paging3_roomdb

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.starwarsapi_paging3_roomdb.viewmodel.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_people_details.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PeopleDetailsActivity : AppCompatActivity() {

    private val viewModel by viewModels<PersonViewModel>()
    private val url by lazy { intent.extras!!.getString("PEOPLE")!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_details)

        initViewModel()
    }

    private fun initViewModel() {
        setActionBar()
        lifecycleScope.launchWhenCreated {
            viewModel.getPerson(url).catch {
            }.collectLatest {
                nameText.text = it.name
                nameText.text = it.name
                birthTxt.text = it.birth_year
                eyeTxt.text = it.eye_color
                filmTxt.text = it.films.joinToString("\n")
                genderTxt.text = it.gender

                supportActionBar?.title = it.name
            }
        }
    }

    private fun setActionBar(){

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}