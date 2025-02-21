package com.example.vk_task.presentation.view

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vk_task.R
import com.example.vk_task.data.model.VideoHit
import com.example.vk_task.databinding.ActivityMainBinding
import com.example.vk_task.presentation.adapter.VideoAdapter
import com.example.vk_task.presentation.state.VideoListState
import com.example.vk_task.presentation.viewModel.VideoViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var videoAdapter: VideoAdapter

    private val viewModel: VideoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        videoAdapter = VideoAdapter { videoUrl -> onVideoSelectedForViewing(videoUrl) }
        binding.videoRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
        }
    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadVideoList()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is VideoListState.Loading -> handleLoadingState()
                        is VideoListState.Content -> handleContentState(state.currentList)
                        is VideoListState.Error -> handleErrorState(state.e)
                    }
                }
            }
        }
    }

    private fun handleLoadingState() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun handleContentState(videoList: List<VideoHit>) {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.imgError.visibility = View.GONE
        binding.tvMsgError.visibility = View.GONE
        videoAdapter.submitList(videoList)
    }

    private fun handleErrorState(error: Throwable) {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.imgError.visibility = View.VISIBLE
        binding.tvMsgError.visibility = View.VISIBLE
        videoAdapter.submitList(emptyList())

        val errorMessage = when (error) {
            is IOException -> getString(R.string.io_exception_error)
            is HttpException -> getString(R.string.http_exception_error)
            else -> getString(R.string.unknown_error)
        }
        binding.tvMsgError.text = errorMessage
    }

    private fun onVideoSelectedForViewing(videoUrl: String) {
        if (isNetworkAvailable()) {
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra(EXTRA_KEY, videoUrl)
            startActivity(intent)
        } else {
            showToast(getString(R.string.io_exception_error))
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_KEY = "VIDEO_URL"
    }
}