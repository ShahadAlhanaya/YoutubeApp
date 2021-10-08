package com.example.youtubeapp

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity() {

    lateinit var youTubePlayerView: YouTubePlayerView
    lateinit var youTubePlayer: YouTubePlayer
    lateinit var recyclerView : RecyclerView

    var currentVideo = 0
    var videoTime = 0f

    var videoList = arrayListOf<YoutubeVideo>(
        YoutubeVideo("Mango Cake Recipe \uD83D\uDC9B","QjRzdvqySgk"),
        YoutubeVideo("Strawberry Crepe Cake Recipe \uD83C\uDF53","gYex98w3Vc0"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)//keep the app always on light mode


        //show dialog when internet is disconnected
        internetDialog()

        //initialize recyclerView
        recyclerView = findViewById(R.id.rv_videos)
        recyclerView.layoutManager = LinearLayoutManager(this)


        //initialize youtubePlayerView
        youTubePlayerView = findViewById(R.id.v_youtube_player)
        youTubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
            override fun onReady(player: YouTubePlayer) {
                super.onReady(player)
                youTubePlayer = player
                youTubePlayer.loadVideo(videoList[currentVideo].videoUrl, videoTime)
                //initialize adapter after creating youtubePlayer
                recyclerView.adapter = RecyclerViewAdapter(videoList, youTubePlayer)
            }
        })
    }

    private fun internetDialog(){
        if (!checkConnectivity()) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("No Internet Connection, please connect to the internet and Retry.")
                .setPositiveButton("Retry") { _, _ -> checkConnectivity() }
                .show()
        }
    }

    private fun checkConnectivity(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            youTubePlayerView.enterFullScreen()
        }else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            youTubePlayerView.exitFullScreen()
        }
    }

}