package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.FragmentHomeBinding
import com.limheejin.kidstopia.presentation.adapter.CategoryRVAdapter
import com.limheejin.kidstopia.presentation.adapter.ChannelRVAdapter
import com.limheejin.kidstopia.presentation.fragment.Adapter.MostPopularRVAdapter
import com.limheejin.kidstopia.presentation.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterMostPopular: MostPopularRVAdapter
    private lateinit var adapterCategoty: CategoryRVAdapter
    private lateinit var adapterChannel: ChannelRVAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val a = listOf<String>()

        setupMostPopularRV()
        setupChannelRV()
        setupCategoryRV()
        setupRecyclerView()
        fetchMostPopularVideos()
        fetchChannel()
        fetchCategory()

    }

    private fun fetchMostPopularVideos() {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    NetworkClient.youtubeApiPopularVideo.getPopularVideoList(
                        key = NetworkClient.AUTH_KEY,
                        part = "snippet",
                        chart = "mostPopular",
                        maxResults = 5
                    )
                }
                adapterMostPopular.setItems(response.items)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "인기 동영상이 정상적으로 연결되지 않았습니다.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun fetchChannel() {
        lifecycleScope.launch {

            val response = withContext(Dispatchers.IO) {
                NetworkClient.youtubeApiChannels.getChannels(
                    key = NetworkClient.AUTH_KEY,
                    part = "snippet,contentDetails",
                    id = "UCL6JmiMXKoXS6bpP1D3bk8g,UCPUeGC_AL-OnDQORKhRm6iA,UC-oIulX1JBJ0aKAB0GHnThA,UCscjd-azZ1AqHxxrO6YDIQg,UCqXwKu6dKobXEQFhdKtiJLQ,UCTc15uvrhUmW044MfJG4HHw,UCqvhycZ4nzxTHU4RxHjYgWQ,UCJJk67SRRXWos0d2l47dOuA"
                )
            }
            adapterChannel.setItemsChannel(response.items)
        }
    }

    private fun fetchCategory() {
        lifecycleScope.launch {
            val response = withContext(Dispatchers.IO) {
                NetworkClient.youtubeApiCategories.getCategoryList(
                    key = NetworkClient.AUTH_KEY,
                    part = "snippet",
                    id = ""
                )
            }
            adapterCategoty.setCategoryItems(response.items)
        }
    }

    private fun setupRecyclerView() {
        // 수평 스크롤
        val layoutManagerMostPopular = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )

        val layoutManagerCategotry = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        val layoutManagerChannel = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )

        //어댑터 연결

        binding.homeMostVidio.layoutManager = layoutManagerMostPopular
        binding.homeMostVidio.adapter = adapterMostPopular

        binding.hoemCategory.layoutManager = layoutManagerCategotry
        binding.hoemCategory.adapter = adapterCategoty

        binding.homeChannel.layoutManager = layoutManagerChannel
        binding.homeChannel.adapter = adapterChannel


    }

    private fun setupMostPopularRV() {

        adapterMostPopular = MostPopularRVAdapter(
            onItemClick = { position ->
                val MostvideoId = position.id
                val videoDetailFragment = VideoDetailFragment()
                val bundle = Bundle()
                bundle.putString("VideoId", MostvideoId)
                videoDetailFragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_up,
                        R.anim.none,
                        R.anim.none,
                        R.anim.slide_down
                    )
                    .replace(R.id.fl, videoDetailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
    }

    private fun setupChannelRV() {
        adapterChannel = ChannelRVAdapter(
            onItemClick = {
                position ->
                val channelId = position.id
                val videoDetailFragment = VideoDetailFragment()
                val bundle = Bundle()
                bundle.putString("VideoId", channelId)
                videoDetailFragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_up, R.anim.none, R.anim.none, R.anim.slide_down)
                    .replace(R.id.fl, videoDetailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
    }

    private fun setupCategoryRV() {
        adapterCategoty = CategoryRVAdapter (
            onItemClick = {
//                position ->
//                val channelId = position.snippet.channelTitle
//                val videoDetailFragment = VideoDetailFragment()
//                val bundle = Bundle()
//                bundle.putString("VideoId", channelId)
//                videoDetailFragment.arguments = bundle
//                parentFragmentManager.beginTransaction()
//                    .setCustomAnimations(R.anim.slide_up, R.anim.none, R.anim.none, R.anim.slide_down)
//                    .replace(R.id.fl, videoDetailFragment)
//                    .addToBackStack(null)
//                    .commit()
            }
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

