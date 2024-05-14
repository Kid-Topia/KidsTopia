package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.FragmentSearchBinding
import com.limheejin.kidstopia.presentation.adapter.RVSearchAdapter

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
        val items = geneateDummyItems() // 가상 데이터 설정 (나중에 API로 받아오면 실제 데이터로 대체)
        clickSearchKeyword()

    }

    private fun setupRecyclerView() {
        // 리니어 레이아웃 매니저 생성 및 설정
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewSearch.layoutManager = layoutManager

        // 어댑터 인스턴스 생성
        searchAdapter = RVSearchAdapter(
            onClick = { position ->
                // 아이템 클릭 시 수행
            },
            onLongClick = { position ->
                // 아이템 롱클릭 시 수행
                true
            }
        )

        // 리사이클러뷰에 어댑터 설정
        binding.recyclerviewSearch.adapter = searchAdapter
    }

    private fun geneateDummyItems(): List<Item> {
        return List(10) { index ->
            Item(
                title = "가정의 달 특집 핑크퐁 아기상어와...",
                context = "5월은 푸르구나 우리들은 자란다"
            )
        }
    }

    private fun clickSearchKeyword() {
        TODO("Not yet implemented")
    }

}

// 리사이클러뷰를 확인해보기 위한 임시 데이터
data class Item(
    val title: String,
    val context: String
)