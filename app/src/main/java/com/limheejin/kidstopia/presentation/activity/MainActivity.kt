package com.limheejin.kidstopia.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.ActivityMainBinding
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDatabase
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import com.limheejin.kidstopia.presentation.fragment.HomeFragment
import com.limheejin.kidstopia.presentation.fragment.MyVideoFragment
import com.limheejin.kidstopia.presentation.fragment.SearchFragment
import com.limheejin.kidstopia.presentation.network.NetworkClient.AUTH_KEY
import com.limheejin.kidstopia.presentation.network.NetworkClient.youtubeApiChannels
import com.limheejin.kidstopia.presentation.network.NetworkClient.youtubeApiPopularVideo
import com.limheejin.kidstopia.presentation.network.NetworkClient.youtubeApiSearch
import com.limheejin.kidstopia.repository.Repository
import com.limheejin.kidstopia.viewmodel.PopularVideoViewModelFactory
import com.limheejin.kidstopia.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    // 뷰모델 생성
    private val viewModel by viewModels<ViewModel> {
        PopularVideoViewModelFactory()
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var testData : PopularData
    lateinit var dao: MyFavoriteVideoDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val kidsTopia = "찌글이"

        //searchCommunicateNetwork("game")
        //popularVideoCommunicateNetwork()
        //channelsCommunicateNetwork()

        // 뷰모델에서 생성한 함수로 http요청을 함
        viewModel.getSearchData("고양이")
        // 옵저버패턴으로 뷰모델의 라이브데이터를 감시
        viewModel.getSearchData.observe(this) {
            it[0].snippet.thumbnails



        }

        dao = MyFavoriteVideoDatabase.getDatabase(application).getDao()
        binding.button.setOnClickListener {
            popularVideoCommunicateNetwork()
        }

        binding.button2.setOnClickListener {
            // dao.deleteVideo("Detail이 받은 비디오ID값")
        }
//        searchCommunicateNetwork("game")
//        popularVideoCommunicateNetwork()
//        channelsCommunicateNetwork()

        binding.nav.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.fl, HomeFragment()).commit()
    }

    private fun searchCommunicateNetwork (query: String) = lifecycleScope.launch {
        /* CategoryId
        15 - Pets & Animals,   1 -  Film & Animation
        27 - Education,        31 - Anime/Animation
        37 - Family,           23 - Comedy
        */
//        var videoIdData = youtubeApiSearch.getSearchList(
//            AUTH_KEY,
//            "snippet",
//            "strict",
//            "video",
//            8, query,
//            "15"
//        )

//        val url = "https://img.youtube.com/vi/" + videoIdData.items[0].id.videoId + "/mqdefault.jpg"
//        Glide.with(binding.root.context)
//            .load(url)
//            .into(binding.imageView)

        /* 받은 snippet에서 썸네일을 가져와도 되지만 여백이 싫을때는 url값을 아래로 지정하면 여백없는 썸네일이 나옴
        https://img.youtube.com/vi + ${items.id.videoId} + /mqdefault.jpg
        */
    }

    private fun popularVideoCommunicateNetwork() = lifecycleScope.launch {
        testData = youtubeApiPopularVideo.getPopularVideoList(
            AUTH_KEY,
            "snippet, contentDetails",
            "mostPopular",
            10
        )

        val id = testData.items[1].id
        val channelId = testData.items[1].snippet.channelId
        val title = testData.items[1].snippet.title
        val thumbnails = testData.items[1].snippet.thumbnails.high.url
        val date = LocalDateTime.now()
        val classify = "isLiked"

        CoroutineScope(Dispatchers.IO).launch {
            dao.insertVideo(MyFavoriteVideoEntity(id, title, channelId, thumbnails, date.toString(), classify))
            Log.d("checkDb", "${dao.getAllVideo()}")
        }
    }

    private fun channelsCommunicateNetwork() = lifecycleScope.launch {
        val data = youtubeApiChannels.getChannels(
            AUTH_KEY,
            "snippet",
            "UCL6JmiMXKoXS6bpP1D3bk8g"
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.mnu_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl, HomeFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.mnu_search -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl, SearchFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.mnu_user -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl, MyVideoFragment()).commitAllowingStateLoss()
                return true
            }
        }
        return false
    }
}