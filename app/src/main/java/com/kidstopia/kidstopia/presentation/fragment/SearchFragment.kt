package com.kidstopia.kidstopia.presentation.fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidstopia.kidstopia.R
import com.kidstopia.kidstopia.databinding.FragmentSearchBinding
import com.kidstopia.kidstopia.presentation.adapter.RVSearchAdapter
import com.kidstopia.kidstopia.viewmodel.SearchVideoViewModelFactory
import com.kidstopia.kidstopia.viewmodel.SearchViewModel

class SearchFragment : Fragment() {
    private val viewModel by viewModels<SearchViewModel> {
        SearchVideoViewModelFactory()
    }
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: RVSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                val videoId = position.id.videoId
                val videoDetailFragment = VideoDetailFragment()
                val bundle = Bundle()
                bundle.putString("VideoId", videoId)
                videoDetailFragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_up,
                        R.anim.none,
                        R.anim.none,
                        R.anim.slide_down
                    )
                    .add(R.id.fl, videoDetailFragment)
                    .addToBackStack(null)
                    .commit()
            },
            onLongClick = { position ->
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
        binding.etSearch.setOnEditorActionListener { _, actionId, event ->
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

