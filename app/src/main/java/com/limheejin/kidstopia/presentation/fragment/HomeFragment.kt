package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.limheejin.kidstopia.databinding.FragmentHomeBinding
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.PopularItems
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.presentation.network.NetworkClient
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManagerMost: LinearLayoutManager
    private lateinit var layoutManagerCategotry: LinearLayoutManager
    private lateinit var layoutManagerChannel: LinearLayoutManager

    private lateinit var mostAdapter: MostAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var channelAdapter: ChannelAdapter

    lateinit var mostitem : PopularData
    lateinit var dao: MyFavoriteVideoDAO
    lateinit var ItemChannel: PopularData
    lateinit var ItemCategoty: PopularData

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
        mostAdapter= MostAdapter(requireContext())
        categoryAdapter= CategoryAdapter(requireContext())
        channelAdapter = ChannelAdapter(requireContext())

        binding.mostVidio.layoutManager = layoutManagerMost
        binding.mostVidio.adapter = mostAdapter

        binding.category.layoutManager = layoutManagerCategotry
//        binding.category.adapter = adapterCategory

        binding.channel.layoutManager = layoutManagerChannel
        binding.channel.adapter = channelAdapter

        binding.mostVidio.adapter = mostAdapter
        mostAdapter.items  = mutableListOf<PopularItems>()
        binding.mostVidio.layoutManager = LinearLayoutManager(requireContext())

        popularVideoCommunicateNetwork()
//        channelsCommunicateNetwork()
    }

    private fun popularVideoCommunicateNetwork() = lifecycleScope.launch {
        mostitem = NetworkClient.youtubeApiPopularVideo.getPopularVideoList(
            NetworkClient.AUTH_KEY,
            "snippet, contentDetails",
            "mostPopular",
            10,
            "KR"
        )

        mostAdapter.items = mostitem.items
        mostAdapter.notifyDataSetChanged()

    }
    private fun channelsCommunicateNetwork() = lifecycleScope.launch {
        ItemChannel = NetworkClient.youtubeApiChannels.getChannels(
            NetworkClient.AUTH_KEY,
            "snippet",
            "UCL6JmiMXKoXS6bpP1D3bk8g"
        )

        binding.channel.adapter = channelAdapter
        channelAdapter.itemsChannel  = ItemChannel.items
        binding.mostVidio.layoutManager = LinearLayoutManager(requireContext())

    }

//    private fun ctegotyCommunicateNetwork() = lifecycleScope.launch {
//
//    }


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