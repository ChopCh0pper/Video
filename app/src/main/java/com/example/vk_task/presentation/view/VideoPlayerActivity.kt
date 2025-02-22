package com.example.vk_task.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.vk_task.R
import com.example.vk_task.databinding.ActivityVideoPlayerBinding
import com.example.vk_task.presentation.viewModel.VideoPlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayerBinding
    private var exoPlayer: ExoPlayer? = null

    private val viewModel: VideoPlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializePlayer()
    }

    private fun initializePlayer() {
        exoPlayer = ExoPlayer.Builder(this).build()
        binding.playerView.player = exoPlayer

        val videoUri = intent.getStringExtra(MainActivity.EXTRA_KEY)
        if (videoUri != null) {
            val mediaItem = MediaItem.fromUri(videoUri)
            exoPlayer?.setMediaItem(mediaItem)
            exoPlayer?.playWhenReady = viewModel.playWhenReady
            exoPlayer?.seekTo(viewModel.playbackPosition)
            exoPlayer?.prepare()
        } else {
            showError(getString(R.string.video_url_not_found))
        }
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.let {
            viewModel.playbackPosition = it.currentPosition
            viewModel.playWhenReady = it.playWhenReady
            it.release()
            exoPlayer = null
        }
    }

    override fun onResume() {
        super.onResume()
        if (exoPlayer == null) {
            initializePlayer()
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
    }
}
