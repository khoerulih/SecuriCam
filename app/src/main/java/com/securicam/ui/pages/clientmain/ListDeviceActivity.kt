package com.securicam.ui.pages.clientmain

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.securicam.R
import com.securicam.data.responses.LoginData
import com.securicam.databinding.ActivityListDeviceBinding
import com.securicam.ui.pages.login.LoginActivity

class ListDeviceActivity : AppCompatActivity() {

    private var _binding : ActivityListDeviceBinding? = null
    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        _binding = ActivityListDeviceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.rvDevice?.setHasFixedSize(true)
        val deviceViewModel = ViewModelProvider(this)[CameraSearchViewModel::class.java]
        val layoutManager = LinearLayoutManager(this)
        binding?.rvDevice?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvDevice?.addItemDecoration(itemDecoration)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = SearchView(this)
        binding?.searchEditText?.addView(searchView)
        binding?.searchEditText?.visibility = View.VISIBLE

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.hint_text)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                deviceViewModel.searchCamera(query)
                binding?.searchEditText?.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrBlank()) {
                    deviceViewModel.showListCamera()
                }
                return true
            }
        })

        deviceViewModel.listUser.observe(this) { listCameras ->
            setListData(listCameras)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setListData(listCameras: List<LoginData>) {
        val users = ArrayList<LoginData>()
        for (user in listCameras) {
            val list = LoginData(
                user.id,
                user.email,
                user.username,
                user.password,
                user.role,
                user.accessToken
            )
            users.add(list)
        }
        val adapter = CameraAdapter(users)
        binding?.rvDevice?.adapter = adapter
    }

}