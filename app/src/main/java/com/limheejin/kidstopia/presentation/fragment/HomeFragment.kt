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
        setupRecyclerView()
        fetchMostPopularVideo()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSpinner() {
        val spinner: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(), R.array.spinner_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> fetchCategoryIdVideo("뽀로로 다시보기") // 뽀로로
                    1 -> fetchCategoryIdVideo("핑크퐁 다시보기") // 핑크퐁
                    2 -> fetchCategoryIdVideo("노리q 동물") // 동물 : 노리q
                    3 -> fetchCategoryIdVideo("주니토니") // 음악 : 주니토니
                    4 -> fetchCategoryIdVideo("예림tv") // 동화 : 예림tv
                    5 -> fetchCategoryIdVideo("깨비키즈 과학") // 과학 : 깨비키즈 과학
                    6 -> fetchCategoryIdVideo("아이들교실") // 교육 : 아이들교실
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    private fun fetchMostPopularVideo() = lifecycleScope.launch {
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
    private fun fetchCategoryIdVideo(query: String) = lifecycleScope.launch {
        val response = withContext(Dispatchers.IO) {
            NetworkClient.youtubeApiOrderSearch.getSearchList(
                query = query
            )
        }
        adapterCategory.setCategoryItems(response.items)
        val channelList = response.items.joinToString { it.snippet.channelId }
        Log.d("channelList", channelList)
        fetchChannel(channelList)
    }

    private fun fetchChannel(channelList: String) = lifecycleScope.launch {
        val response = withContext(Dispatchers.IO) {
            NetworkClient.youtubeApiChannel.getChannelData(
                key = NetworkClient.AUTH_KEY, part = "snippet", id = channelList
            )
        }
        adapterChannel.setItemsChannel(response.items)
    }


    private fun setupRecyclerView() {
        // onItemClick 구현
        adapterMostPopular = MostPopularVideoAdapter(onItemClick = { position ->
            setFragment(position.id,"VideoId")
        })
        adapterCategory = CategoryAdapter(onItemClick = { position ->
            setFragment(position.id.videoId,"VideoId")
        })
        adapterChannel = ChannelAdapter(onItemClick = { position ->
            setFragment(position.id, "ChannelId")
        })

        //어댑터 연결
        binding.apply {
            homeMostVidio.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            homeMostVidio.adapter = adapterMostPopular
            homeCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            homeCategory.adapter = adapterCategory
            homeChannel.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            homeChannel.adapter = adapterChannel
        }
    }

    private fun setFragment(id: String?, param: String) {
        val videoDetailFragment = VideoDetailFragment()
        val bundle = Bundle()
        bundle.putString(param, id)
        videoDetailFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_up, R.anim.none, R.anim.none, R.anim.slide_down)
            .replace(R.id.fl, videoDetailFragment)
            .addToBackStack(null)
            .commit()
    }

}

