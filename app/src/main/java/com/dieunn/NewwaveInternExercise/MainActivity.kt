package com.dieunn.NewwaveInternExercise

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dieunn.NewwaveInternExercise.adapters.MovieAdapter
import com.dieunn.NewwaveInternExercise.databinding.ActivityMainBinding
import com.dieunn.NewwaveInternExercise.models.Movie
import com.dieunn.NewwaveInternExercise.videmodels.MovieViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val TAG = "WTF"
    val viewModel: MovieViewModel by viewModels()
    var movieList: MutableSet<Movie> = mutableSetOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {

        super.onStart()

        initData()
        setupRefreshLayout()
        loadMore()
        scrollToTop()
        setupRecyclerView()

    }

    fun initData() {
        lifecycleScope.launch {
            viewModel.initData()
        }
    }

    fun setupRecyclerView() {
        val recyclerViewAdapter = MovieAdapter(data = movieList)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            binding.recyclerView.adapter = recyclerViewAdapter
            setHasFixedSize(true)
        }

        viewModel.data.observe(this) {
            movieList = it.toMutableSet()
            recyclerViewAdapter.refreshData(it)
            Log.d(TAG, "onStart: Refreshing ...")
        }
    }

    // load more handling, when scroll to bottom, automatically fetch more
    fun loadMore() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    viewModel.loadMore()
                    Toast.makeText(this@MainActivity, "Loaded more items", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    fun setupRefreshLayout() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.refreshData()
            binding.swipeLayout.isRefreshing = false
            Toast.makeText(
                this,
                "Refreshed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Scroll to top when press navigation button
    fun scrollToTop() {
        binding.toolBar.setNavigationOnClickListener {
            binding.recyclerView.smoothScrollToPosition(0)
        }
    }

}