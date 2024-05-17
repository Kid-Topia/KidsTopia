package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.limheejin.kidstopia.databinding.FragmentHomeBinding
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.ChannelData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import com.limheejin.kidstopia.presentation.network.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManagerMost: LinearLayoutManager
    private lateinit var layoutManagerCategotry: LinearLayoutManager
    private lateinit var layoutManagerChannel: LinearLayoutManager

    private lateinit var adapterMost: MostFragmentAdapter
    private lateinit var adapterCategoty: CategoryFragmentAdapter
    private lateinit var adapterChannel: ChannelFragmentAdapter

    lateinit var mostitem : PopularData
    lateinit var dao: MyFavoriteVideoDAO
    lateinit var ItemChannel: ChannelData



    override  fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }


    private fun popularVideoCommunicateNetwork() = lifecycleScope.launch {
        mostitem = NetworkClient.youtubeApiPopularVideo.getPopularVideoList(
            NetworkClient.AUTH_KEY,
            "snippet, contentDetails",
            "mostPopular",
            10,
            "KR"
        )

        val id = mostitem.items[0].id
        val channelId = mostitem.items[0].snippet.channelId
        val title = mostitem.items[0].snippet.title
        val thumbnails = mostitem.items[0].snippet.thumbnails.high.url
        val date = LocalDateTime.now()
        val classify = "isLiked"

        binding.mostVidio.adapter = adapterMost
        adapterMost.items  = mostitem.items
        binding.mostVidio.layoutManager = LinearLayoutManager(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            dao.insertVideo(MyFavoriteVideoEntity(id, title, channelId, thumbnails, date.toString(), classify))
            Log.d("checkDb", "${dao.getAllVideo()}")
        }
    }


    private fun channelsCommunicateNetwork() = lifecycleScope.launch {
        ItemChannel = NetworkClient.youtubeApiChannels.getChannels(
            NetworkClient.AUTH_KEY,
            "snippet",
            "UCL6JmiMXKoXS6bpP1D3bk8g"
        )

        binding.channel.adapter = adapterChannel
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