package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.FragmentSearchBinding
import com.limheejin.kidstopia.presentation.adapter.RVSearchAdapter
import com.limheejin.kidstopia.viewmodel.SearchVideoViewModelFactory
import com.limheejin.kidstopia.viewmodel.SearchViewModel

class SearchFragment : Fragment() {
    // 뷰모델 생성
    private val viewModel by viewModels<SearchViewModel> {
        SearchVideoViewModelFactory()
    }
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
        setupSearch()
        setupEasySearchButton()
        setupObservers()

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
                    .replace(R.id.fl, videoDetailFragment)
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

    private fun setupSearch() {

        //        // 검색어가 갱신될 때마다 자동 검색
//        binding.etSearch.addTextChangedListener { editable ->
//            val query = editable.toString()
//            if (query.isNotEmpty()) {
//                viewModel.searchVideos(query)
//            }
//        }

        // 검색 버튼없이 키보드의 엔터로 작동 (imeOptions="actionSearch", inputType="text" 로 설정 후 구현)
        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                val query = binding.etSearch.text.toString()
                if (query.isNotEmpty()) {
                    viewModel.searchVideos(query)
                } else {
                    Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                true
            } else false
        }

//        // 검색 버튼(btnSearch)을 추가한다면 아래 코드 사용 가능
//        binding.btnSearch.setOnClickListener {
//            val query = binding.etSearch.text.toString()
//            if (query.isNotEmpty()){
//                viewModel.searchVideos(query)
//            } else {
//                Toast.makeText(requireContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
//            }
//        }

    }

    private fun setupEasySearchButton() {
        binding.btnSearchKids.setOnClickListener {
            binding.etSearch.setText("키즈")
            viewModel.searchVideos("키즈")
        }

        binding.btnSearchSleepingmusic.setOnClickListener {
            binding.etSearch.setText("수면음악")
            viewModel.searchVideos("수면음악")
        }

        binding.btnSearchDongyo.setOnClickListener {
            binding.etSearch.setText("동요")
            viewModel.searchVideos("동요")
        }
    }

    private fun setupObservers() {
        viewModel.getSearchData.observe(viewLifecycleOwner) { searchItems ->
            searchAdapter.setItems(searchItems)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

}

