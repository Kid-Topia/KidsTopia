package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.FragmentSearchBinding
import com.limheejin.kidstopia.model.SearchItems
import com.limheejin.kidstopia.presentation.adapter.RVSearchAdapter
import com.limheejin.kidstopia.presentation.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.lang.Exception

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: RVSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setEasySearchButton()

        binding.etSearch.addTextChangedListener { editable ->
            val query = editable.toString()
            if (query.isNotEmpty()) {
                searchVideos(query)
            }
            // MVVM 적용하고나서 다시 여쭤보기
        }


//        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                val query = binding.etSearch.text.toString()
//                if (query.isNotEmpty()){
//                    searchVideos(query)
//                }
//                return@setOnEditorActionListener true
//            } else {
//                false
//            }
//        }

    }

    private fun setupRecyclerView() {
        // 리니어 레이아웃 매니저 생성 및 설정
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewSearch.layoutManager = layoutManager

        // 어댑터 인스턴스 생성
        searchAdapter = RVSearchAdapter(
            onItemClick = { position ->
                val videoId = position.id.videoId // 선택한 비디오의 유튜브 비디오 ID값
                val videoDetailFragment = VideoDetailFragment()
                val bundle = Bundle() // 일단 번들로 구현
                bundle.putString("VideoId", videoId)
                videoDetailFragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, videoDetailFragment)
                    .addToBackStack(null)
                    .commit()
            },
            onLongClick = { position ->
                // 아이템 롱클릭 시 수행, 시간이 남으면 구현하기
                true
            }
        )

        // 리사이클러뷰에 어댑터 설정
        binding.recyclerviewSearch.adapter = searchAdapter
    }

    private fun setEasySearchButton() {
        binding.btnSearchKids.setOnClickListener {
            binding.etSearch.setText("키즈")
            searchVideos("키즈")
        }

        binding.btnSearchSleepingmusic.setOnClickListener {
            binding.etSearch.setText("수면음악")
            searchVideos("수면음악")
        }

        binding.btnSearchDongyo.setOnClickListener {
            binding.etSearch.setText("동요")
            searchVideos("동요")
        }
    }

    private fun searchVideos(query: String) {
        lifecycleScope.launch {
//            try {
                val searchItems = getSearchResults(query)
                searchAdapter.setItems(searchItems)
//            } catch (e: Exception) {
//                handleException(e) // 다양한 케이스의 예외처리를 위해 만듦
//            }
        }

    }

//    private fun handleException(exception: Exception) {
//        val errorMessage = when (exception) {
//            is IOException -> "IO Exception 오류가 발생하였습니다."
//            is HttpException -> {
//                when (exception.code()) {
//                    400 -> "Bad Request 오류 발생"
//                    401 -> "Unauthorized 오류 발생"
//                    404 -> "not found 오류 발생"
//                    else -> "알 수 없는 오류가 발생하였습니다."
//                }
//            }
//
//            else -> "알 수 없는 오류가 발생하였습니다."
//        }
//        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
//    }

    private suspend fun getSearchResults(query: String): List<SearchItems> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkClient.youtubeApiSearch.getSearchList(
                    key = NetworkClient.AUTH_KEY,
                    part = "snippet",
                    safeSearch = "strict",
                    type = "video",
                    maxResults = 3, // 데이터 아끼기 위해 일단 3개
                    query = query,
                    videoCategoryId = "15" // Pets & Animals
                )
                response.items
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

}


