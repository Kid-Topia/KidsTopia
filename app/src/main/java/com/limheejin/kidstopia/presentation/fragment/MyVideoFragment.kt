package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.FragmentMyVideoBinding
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import com.limheejin.kidstopia.presentation.adapter.MyFavoriteVideoAdapter
import com.limheejin.kidstopia.presentation.adapter.VisitedPageAdapter
import com.limheejin.kidstopia.viewmodel.MyVideoViewModel
import com.limheejin.kidstopia.viewmodel.MyVideoViewModelFactory

import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MyVideoFragment : Fragment() {
    private lateinit var binding: FragmentMyVideoBinding
    private val myVideoViewModel by viewModels<MyVideoViewModel> {
        MyVideoViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyVideoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getItems()
        initRv()
    }

    fun getItems() = lifecycleScope.launch {
        myVideoViewModel.getItems()
    }

    fun initRv() {
        myVideoViewModel.items.observe(viewLifecycleOwner) { items ->
            val visitedPageAdapter = VisitedPageAdapter(items)
            val myFavoriteVideoAdapter =
                MyFavoriteVideoAdapter(items.filter { it.classify == "isLiked" }.toMutableList())

            setupVisitedPageAdapterClickListener(visitedPageAdapter)
            setupMyFavoriteVideoAdapterClickListener(myFavoriteVideoAdapter)

            binding.apply {
                favoriteVideoRv.adapter = myFavoriteVideoAdapter
                visitedPageRv.adapter = visitedPageAdapter
                favoriteVideoRv.layoutManager = LinearLayoutManager(requireContext())
                visitedPageRv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun setupVisitedPageAdapterClickListener(adapter: VisitedPageAdapter) {
        adapter.itemClick = object : VisitedPageAdapter.ItemClick {
            override fun itemClick(id: String) {
                val videoId = id // 선택한 비디오의 유튜브 비디오 ID값
                val videoDetailFragment = VideoDetailFragment()
                val bundle = Bundle() // 일단 번들로 구현
                bundle.putString("VideoId", videoId)
                videoDetailFragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_up, R.anim.none, R.anim.none, R.anim.slide_down)
                    .replace(R.id.fl, videoDetailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun setupMyFavoriteVideoAdapterClickListener(adapter: MyFavoriteVideoAdapter) {
        adapter.itemClick = object : MyFavoriteVideoAdapter.ItemClick {
            override fun itemClick(id: String) {
                val videoId = id // 선택한 비디오의 유튜브 비디오 ID값
                val videoDetailFragment = VideoDetailFragment()
                val bundle = Bundle() // 일단 번들로 구현
                bundle.putString("VideoId", videoId)
                videoDetailFragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_up, R.anim.none, R.anim.none, R.anim.slide_down)
                    .replace(R.id.fl, videoDetailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}