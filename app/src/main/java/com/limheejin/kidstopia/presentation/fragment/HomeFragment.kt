package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.FragmentHomeBinding
import com.limheejin.kidstopia.presentation.adapter.CategoryAdapter
import com.limheejin.kidstopia.presentation.adapter.ChannelAdapter
import com.limheejin.kidstopia.presentation.adapter.MostPopularVideoAdapter
import com.limheejin.kidstopia.presentation.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterMostPopular: MostPopularVideoAdapter
    private lateinit var adapterCategory: CategoryAdapter
    private lateinit var adapterChannel: ChannelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
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
        Log.d("channelList", channelList)
        fetchChannel(channelList)
    }

    private fun fetchChannel(channelList: String) = lifecycleScope.launch {
        val response = withContext(Dispatchers.IO) {
            NetworkClient.youtubeApiChannels.getChannels(
                key = NetworkClient.AUTH_KEY, part = "snippet", id = channelList
            )
        }
        adapterChannel.setItemsChannel(response.items)
    }

//    private fun fetchCategory() {
//        lifecycleScope.launch {
////            val response = withContext(Dispatchers.IO) {
////                NetworkClient.youtubeApiCategories.getCategoryList(
////                    key = NetworkClient.AUTH_KEY,
////                    part = "snippet",
////                    regionCode = "KR"
////                )
////            }
////            Log.d("response","${response}")
////            adapterCategoty.setCategoryItems(response.items)
//        }
//    }

    private fun setupRecyclerView() {
        // 수평 스크롤
        val layoutManagerMostPopular = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        val layoutManagerCategory = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        val layoutManagerChannel = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        //어댑터 연결

        binding.homeMostVidio.layoutManager = layoutManagerMostPopular
        binding.homeMostVidio.adapter = adapterMostPopular

        binding.hoemCategory.layoutManager = layoutManagerCategory
        binding.hoemCategory.adapter = adapterCategory

        binding.homeChannel.layoutManager = layoutManagerChannel
        binding.homeChannel.adapter = adapterChannel


    }

    private fun setupCategoryRV() {
        adapterCategory = CategoryAdapter(onItemClick = { position ->
            val mostVideoId = position.id
            val videoDetailFragment = VideoDetailFragment()
            val bundle = Bundle()
            bundle.putString("VideoId", mostVideoId)
            videoDetailFragment.arguments = bundle
            parentFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_up, R.anim.none, R.anim.none, R.anim.slide_down
            ).replace(R.id.fl, videoDetailFragment).addToBackStack(null).commit()
        })
    }

    private fun setupMostPopularRV() {
        adapterMostPopular = MostPopularVideoAdapter(onItemClick = { position ->
            val mostVideoId = position.id
            val videoDetailFragment = VideoDetailFragment()
            val bundle = Bundle()
            bundle.putString("VideoId", mostVideoId)
            videoDetailFragment.arguments = bundle
            parentFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_up, R.anim.none, R.anim.none, R.anim.slide_down
            ).replace(R.id.fl, videoDetailFragment).addToBackStack(null).commit()
        })
    }

    private fun setupChannelRV() {
        adapterChannel = ChannelAdapter(onItemClick = { position ->
            val MostvideoId = position.snippet.title
            val videoDetailFragment = VideoDetailFragment()
            val bundle = Bundle()
            bundle.putString("VideoId", MostvideoId)
            videoDetailFragment.arguments = bundle
            parentFragmentManager.beginTransaction().setCustomAnimations(

                R.anim.slide_up, R.anim.none, R.anim.none, R.anim.slide_down
            ).replace(R.id.fl, videoDetailFragment).addToBackStack(null).commit()

        })
    }

    private fun setupSpinner() {
        val spinner: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(), R.array.spinner_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                when(position) {
                    0 -> { selectCategory("10") }
                    1 -> { selectCategory("15") }
                    2 -> { selectCategory("20") }
                    3 -> { selectCategory("28") }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun selectCategory(categoryId: String){
        setupMostPopularRV()
        setupChannelRV()
        setupCategoryRV()
        setupRecyclerView()
        fetchMostPopularVideos()
        fetchCategoryIdVideo(categoryId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

