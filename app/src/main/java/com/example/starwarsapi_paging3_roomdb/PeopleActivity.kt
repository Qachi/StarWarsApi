package com.example.starwarsapi_paging3_roomdb

import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapi_paging3_roomdb.adapter.StarWarAdapter
import com.example.starwarsapi_paging3_roomdb.databinding.ActivityPeopleBinding
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity
import com.example.starwarsapi_paging3_roomdb.viewmodel.PeopleViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PeopleActivity : AppCompatActivity(), StarWarAdapter.OnPeopleClickListener {

    private val viewModel by viewModels<PeopleViewModel>()
    private val myAdapter by lazy { StarWarAdapter(this) }
    private lateinit var bindings: ActivityPeopleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityPeopleBinding.inflate(layoutInflater)
        val view = bindings.root
        setContentView(view)

        networkConnection()
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
                networkConnection()
            }.collectLatest {
                myAdapter.submitData(it)
            }
        }
    }

    private fun networkConnection() {
        val connectionManager = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (isConnected) {
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
            "Please Check Network Connection",
            Toast.LENGTH_SHORT
        )
            .setAction("Ok") {

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
        intent.putExtra("PEOPLE", people.url)
        startActivity(intent)
    }
}