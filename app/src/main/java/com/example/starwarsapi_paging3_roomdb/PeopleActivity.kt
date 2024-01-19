package com.example.starwarsapi_paging3_roomdb

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapi_paging3_roomdb.adapter.StarWarAdapter
import com.example.starwarsapi_paging3_roomdb.databinding.ActivityPeopleBinding
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.util.Constant
import com.example.starwarsapi_paging3_roomdb.util.NetworkUtils
import com.example.starwarsapi_paging3_roomdb.viewmodel.PeopleViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.M)
class PeopleActivity : AppCompatActivity(), StarWarAdapter.OnPeopleClickListener {

    private val viewModel by viewModels<PeopleViewModel>()
    private val myAdapter by lazy { StarWarAdapter(this) }
    private lateinit var bindings: ActivityPeopleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityPeopleBinding.inflate(layoutInflater)
        val view = bindings.root
        setContentView(view)

        showNetworkErrorIfRequired()
    }

    private fun setUpRecyclerView() {
        bindings.recyclerView.apply {
            adapter = myAdapter
            layoutManager =
                LinearLayoutManager(
                    this@PeopleActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            val divider = DividerItemDecoration(
                applicationContext,
                DividerItemDecoration.VERTICAL
            )
            addItemDecoration(divider)
        }
    }

    private fun initViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getPeople.catch {
                showNetworkErrorIfRequired()
            }.collectLatest {
                myAdapter.submitData(it)
            }
        }
    }

    private fun showNetworkErrorIfRequired() {
        if (NetworkUtils.isConnected(this)) {
            setUpRecyclerView()
            initViewModel()
        } else {
            setUpRecyclerView()
            initViewModel()
            snackBar()
        }
    }

    private fun snackBar() {
        val snackBar = Snackbar.make(
            findViewById(R.id.frameLayout),
            getString(R.string.network_connection),
            Toast.LENGTH_SHORT
        )
            .setAction(getString(R.string.ok)) {

            }
            .setActionTextColor(Color.WHITE)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.RED)
        val textView =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snackBar.show()
    }

    override fun itemClicked(people: PeopleResponseEntity, position: Int) {
        val intent = Intent(this, PeopleDetailsActivity::class.java)
        intent.putExtra(Constant.EXTRA_CHARACTERS, people.url)
        startActivity(intent)
    }
}