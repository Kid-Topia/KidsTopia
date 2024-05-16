package com.limheejin.kidstopia.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.ActivityMainBinding
import com.limheejin.kidstopia.presentation.fragment.SearchFragment
import com.limheejin.kidstopia.presentation.network.NetworkClient.AUTH_KEY
import com.limheejin.kidstopia.presentation.network.NetworkClient.youtubeApiChannels
import com.limheejin.kidstopia.presentation.network.NetworkClient.youtubeApiPopularVideo
import com.limheejin.kidstopia.presentation.network.NetworkClient.youtubeApiSearch
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val kidsTopia = "찌글이"

//        searchCommunicateNetwork("game")
//        popularVideoCommunicateNetwork()
//        channelsCommunicateNetwork()

        val searchFragment = SearchFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, searchFragment)
            .commit()
    }

//    private fun searchCommunicateNetwork (query: String) = lifecycleScope.launch {
//        /* CategoryId
//        15 - Pets & Animals,   1 -  Film & Animation
//        27 - Education,        31 - Anime/Animation
//        37 - Family,           23 - Comedy
//        */
//        var videoIdData = youtubeApiSearch.getSearchList(
//            AUTH_KEY,
//            "snippet",
//            "strict",
//            "video",
//            8, query,
//            "15"
//        )
//
//        val url = "https://img.youtube.com/vi/" + videoIdData.items[0].id.videoId + "/mqdefault.jpg"
//        Glide.with(binding.root.context)
//            .load(url)
//            .into(binding.imageView)
//
//        /* 받은 snippet에서 썸네일을 가져와도 되지만 여백이 싫을때는 url값을 아래로 지정하면 여백없는 썸네일이 나옴
//        https://img.youtube.com/vi + ${items.id.videoId} + /mqdefault.jpg
//        */
//    }
//
//    private fun popularVideoCommunicateNetwork() = lifecycleScope.launch {
//        val data = youtubeApiPopularVideo.getPopularVideoList(
//            AUTH_KEY,
//            "snippet, contentDetails",
//            "mostPopular",
//            100
//        )
//    }
//
//    private fun channelsCommunicateNetwork() = lifecycleScope.launch {
//        val data = youtubeApiChannels.getChannels(
//            AUTH_KEY,
//            "snippet",
//            "UCL6JmiMXKoXS6bpP1D3bk8g"
//        )
//    }


//    private fun popularVideoCommunicateNetwork() = lifecycleScope.launch {
//        testData = youtubeApiPopularVideo.getPopularVideoList(
//            AUTH_KEY,
//            "snippet, contentDetails",
//            "mostPopular",
//            10
//        )
//
//        val id = testData.items[1].id
//        val channelId = testData.items[1].snippet.channelId
//        val title = testData.items[1].snippet.title
//        val thumbnails = testData.items[1].snippet.thumbnails.high.url
//        val date = LocalDateTime.now()
//        val classify = "isLiked"
//
//        CoroutineScope(Dispatchers.IO).launch {
//            dao.insertVideo(MyFavoriteVideoEntity(id, title, channelId, thumbnails, date.toString(), classify))
//            Log.d("checkDb", "${dao.getAllVideo()}")
//        }
//    }

}