package com.example.vk_task.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vk_task.R
import com.example.vk_task.data.model.Video
import com.example.vk_task.data.model.VideoHit
import com.example.vk_task.databinding.ItemVideoBinding
import com.squareup.picasso.Picasso

class VideoAdapter(
    private val onClickListener: (videoUrl: String) -> Unit
) : ListAdapter<VideoHit, VideoAdapter.VideoViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener(item.videos.medium?.url!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoBinding.inflate(inflater, parent, false)
        return VideoViewHolder(binding)
    }

    class VideoViewHolder(
        private val binding: ItemVideoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(video: VideoHit) {
            Picasso.get()
                .load(video.videos.medium?.thumbnail)
                .into(binding.imgThumbnail)

            binding.tvDuration.text = itemView.context.getString(
                R.string.duration,
                video.duration.toString()
            )
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VideoHit>() {
            override fun areItemsTheSame(
                oldItem: VideoHit,
                newItem: VideoHit
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: VideoHit,
                newItem: VideoHit
            ) = oldItem == newItem
        }
    }
}