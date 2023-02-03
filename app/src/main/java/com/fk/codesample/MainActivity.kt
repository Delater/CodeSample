package com.fk.codesample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.fk.codesample.ui.CatListAdapter
import com.fk.codesample.viewModels.CatListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorText: TextView
    private lateinit var refreshButton: Button
    private lateinit var loadingSpinner: ProgressBar

    private val viewModel: CatListViewModel by viewModels()

    private val catListAdapter: CatListAdapter by lazy { CatListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        refreshButton.setOnClickListener {
            viewModel.fetchCats()
        }
        recyclerView.adapter = catListAdapter
        subscribeToViewModel()

        viewModel.fetchCats()
    }

    private fun findViews() {
        recyclerView = findViewById(R.id.recyclerView)
        errorText = findViewById(R.id.errorText)
        refreshButton = findViewById(R.id.refreshButton)
        loadingSpinner = findViewById(R.id.loader)
    }

    private fun subscribeToViewModel() {
        viewModel.stateLiveData.observe(this) { state ->
            when (state) {
                is CatListViewModel.State.Loading -> {
                    loadingSpinner.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    errorText.visibility = View.GONE
                    refreshButton.visibility = View.GONE
                }
                is CatListViewModel.State.Content -> {
                    loadingSpinner.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    errorText.visibility = View.GONE
                    refreshButton.visibility = View.GONE
                    catListAdapter.submitList(state.catList)
                }
                is CatListViewModel.State.Error -> {
                    loadingSpinner.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                    refreshButton.visibility = View.VISIBLE
                }
            }
        }
    }
}