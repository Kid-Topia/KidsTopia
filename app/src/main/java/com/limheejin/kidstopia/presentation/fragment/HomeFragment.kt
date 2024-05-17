package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.limheejin.kidstopia.databinding.FragmentHomeBinding
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.presentation.network.NetworkClient
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManagerMost: LinearLayoutManager
    private lateinit var layoutManagerCategotry: LinearLayoutManager
    private lateinit var layoutManagerChannel: LinearLayoutManager

    private lateinit var adapterMost: MostFragmentAdapter
    private lateinit var adapterCategoty: CategoryFragmentAdapter
    private lateinit var adapterChannel: ChannelFragmentAdapter

    lateinit var data : PopularData
    lateinit var dao: MyFavoriteVideoDAO
    private val handler = Handler(Looper.getMainLooper())



    override  fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root

        // 수평 스크롤
        layoutManagerMost = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        layoutManagerCategotry = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        layoutManagerChannel = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)

        //어뎁터 연결
        adapterMost = MostFragmentAdapter(requireContext())
        adapterCategoty = CategoryFragmentAdapter(requireContext())
        adapterChannel = ChannelFragmentAdapter(requireContext())

        binding.mostVidio.layoutManager = layoutManagerMost
        binding.mostVidio.adapter = adapterMost

        binding.category.layoutManager = layoutManagerCategotry
//        binding.category.adapter = adapterCategory

        binding.channel.layoutManager = layoutManagerChannel
        binding.channel.adapter = adapterChannel

        popularVideoCommunicateNetwork()
        channelsCommunicateNetwork()

        return view
    }

    private fun popularVideoCommunicateNetwork() = lifecycleScope.launch {
        data = NetworkClient.youtubeApiPopularVideo.getPopularVideoList(
            NetworkClient.AUTH_KEY,
            "snippet, contentDetails",
            "mostPopular",
            10,
            "KR"
        )

        binding.mostVidio.adapter = adapterMost
        adapterMost.items  = data.items
        binding.mostVidio.layoutManager = LinearLayoutManager(requireContext())
    }


    private fun channelsCommunicateNetwork() = lifecycleScope.launch {
        val item = NetworkClient.youtubeApiChannels.getChannels(
            NetworkClient.AUTH_KEY,
            "snippet",
            "UCL6JmiMXKoXS6bpP1D3bk8g"
        )

        binding.channel.adapter = adapterChannel
//        adapterChannel.itemsChannel = item

    }



//    private fun popularVideoCommunicateNetwork() = lifecycleScope.launch {
//        testData = NetworkClient.youtubeApiPopularVideo.getPopularVideoList(
//            NetworkClient.AUTH_KEY,
//            "snippet, contentDetails",
//            "mostPopular",
//            10,
//        )
//
//        val id = items.items[1].id
//        val channelId = items.items[1].snippet.channelId
//        val title = items.items[1].snippet.title
//        val thumbnails = items.items[1].snippet.thumbnails.high.url
//        val date = LocalDateTime.now()
//        val classify = "isLiked"
//
//        CoroutineScope(Dispatchers.IO).launch {
//            dao.insertVideo(MyFavoriteVideoEntity(id, title, channelId, thumbnails, date.toString(), classify))
//            Log.d("checkDb", "${dao.getAllVideo()}")
//        }
//    }
//
//    private fun channelsCommunicateNetwork() = lifecycleScope.launch {
//        val data = NetworkClient.youtubeApiChannels.getChannels(
//            NetworkClient.AUTH_KEY,
//            "snippet",
//            "UCL6JmiMXKoXS6bpP1D3bk8g"
//        )
//    }
}