package com.example.youtubeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import kotlinx.android.synthetic.main.video_item.view.*

class RecyclerViewAdapter(
    private val videoList: ArrayList<YoutubeVideo>,
    private val player: YouTubePlayer
) : RecyclerView.Adapter<RecyclerViewAdapter.VideoViewHolder>() {
    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoLinearLayout: LinearLayout = itemView.ll_video_row
        val videoTitleTextView: TextView = itemView.tv_video_title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.video_item,
            parent,
            false
        )
        return VideoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val currentTitle = videoList[position].videoTitle
        val currentLink = videoList[position].videoUrl
        holder.videoTitleTextView.text = currentTitle
        holder.videoLinearLayout.setOnClickListener {
            player.loadVideo(currentLink, 0f)
        }
    }

    override fun getItemCount() = videoList.size

}