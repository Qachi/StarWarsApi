package com.example.starwarsapi_paging3_roomdb

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.starwarsapi_paging3_roomdb.databinding.ActivityPeopleDetailsBinding
import com.example.starwarsapi_paging3_roomdb.util.Constant
import com.example.starwarsapi_paging3_roomdb.util.Status
import com.example.starwarsapi_paging3_roomdb.viewmodel.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PeopleDetailsActivity : AppCompatActivity() {

    private val viewModel by viewModels<PersonViewModel>()
    private val url by lazy { intent.extras!!.getString(Constant.CHARACTERS)!! }

    private lateinit var binding: ActivityPeopleDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViewModel()
    }

    private fun initViewModel() {
        setActionBar()
        lifecycleScope.launchWhenCreated {
            viewModel.getPerson(url)
                .catch {
                    // Handle errors if needed
                }
                .collectLatest { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            val person = resource.data
                            binding.nameText.text = person?.name
                            binding.birthTxt.text = person?.birth_year
                            binding.eyeTxt.text = person?.eye_color
                            binding.filmTxt.text = person?.films?.joinToString("\n")
                            binding.genderTxt.text = person?.gender
                            supportActionBar?.title = person?.name
                        }

                        Status.ERROR -> {
                            // Handle the error state if needed
                            Log.e(TAG, "Error fetching person: ${resource.message}")
                        }

                        Status.LOADING -> {
                            // Handle the loading state if needed
                            Log.d(TAG, "Loading person data...")
                        }
                    }
                }
        }
    }

    companion object {
        private const val TAG = "PeopleDetailActivity"
    }

    private fun setActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}