package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import android.util.Log
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
    private lateinit var adapterCategory: CategoryRVAdapter
    private lateinit var adapterChannel: ChannelRVAdapter
    private lateinit var categoryId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = "20"

        setupMostPopularRV()
        setupChannelRV()
        setupCategoryRV()
        setupRecyclerView()
        fetchMostPopularVideos()
        fetchCategory()
        fetchCategoryIdVideo(categoryId)
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
    private fun fetchCategoryIdVideo(categoryId: String) = lifecycleScope.launch {
        val response = withContext(Dispatchers.IO) {
            NetworkClient.youtubeApiCategoryVideoList.getPopularVideoCategoryList(
                key = NetworkClient.AUTH_KEY,
                part = "snippet",
                chart = "mostPopular",
                categoryId,
                maxResults = 5
            )
        }
        adapterCategory.setCategoryItems(response.items)
        val channelList = response.items.joinToString { it.snippet.channelId }
        Log.d("channelList", "$channelList")
        fetchChannel(channelList)
    }

    private fun fetchChannel(channelList: String) = lifecycleScope.launch {
        val response = withContext(Dispatchers.IO) {
            NetworkClient.youtubeApiChannels.getChannels(
                key = NetworkClient.AUTH_KEY,
                part = "snippet",
                id = channelList
            )
        }
        adapterChannel.setItemsChannel(response.items)
    }

    private fun fetchCategory() {
        lifecycleScope.launch {
//            val response = withContext(Dispatchers.IO) {
//                NetworkClient.youtubeApiCategories.getCategoryList(
//                    key = NetworkClient.AUTH_KEY,
//                    part = "snippet",
//                    regionCode = "KR"
//                )
//            }
//            Log.d("response","${response}")
//            adapterCategoty.setCategoryItems(response.items)
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
        binding.hoemCategory.adapter = adapterCategory

        binding.homeChannel.layoutManager = layoutManagerChannel
        binding.homeChannel.adapter = adapterChannel


    }

    private fun setupCategoryRV() {
        adapterCategory = CategoryRVAdapter (
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
            onItemClick = { position ->
                val MostvideoId = position.snippet.title
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

