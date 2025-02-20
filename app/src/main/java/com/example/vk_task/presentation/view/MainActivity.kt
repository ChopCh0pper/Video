package com.example.vk_task.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vk_task.databinding.ActivityMainBinding
import com.example.vk_task.presentation.adapter.VideoAdapter
import com.example.vk_task.presentation.state.VideoListState
import com.example.vk_task.presentation.viewModel.VideoViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var videoAdapter: VideoAdapter

    private val viewModel by lazy {
        ViewModelProvider(this)[VideoViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        setupListeners()
        viewModel.loadVideoList()
    }

    private fun setupRecyclerView() {
        videoAdapter = VideoAdapter { videoUrl ->  onVideoSelectedForViewing(videoUrl)}
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
            viewModel.state.collectLatest {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {

                    when(it) {

                        is VideoListState.Loading -> {
                            binding.swipeRefreshLayout.isRefreshing = true
                        }

                        is VideoListState.Content -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                            videoAdapter.submitList(it.currentList)
                        }
                    }
                }
            }

        }
    }

    private fun onVideoSelectedForViewing(videoUrl: String) {

        val intent = Intent(this, VideoPlayerActivity::class.java)
        intent.putExtra(EXTRA_KEY, videoUrl)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_KEY = "VIDEO_URL"
    }
}