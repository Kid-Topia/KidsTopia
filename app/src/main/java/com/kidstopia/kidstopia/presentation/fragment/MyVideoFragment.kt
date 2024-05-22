package com.kidstopia.kidstopia.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidstopia.kidstopia.R
import com.kidstopia.kidstopia.databinding.FragmentMyVideoBinding
import com.kidstopia.kidstopia.model.database.MyFavoriteVideoEntity
import com.kidstopia.kidstopia.presentation.adapter.MyFavoriteVideoAdapter
import com.kidstopia.kidstopia.presentation.adapter.VisitedPageAdapter
import com.kidstopia.kidstopia.viewmodel.MyVideoViewModel
import com.kidstopia.kidstopia.viewmodel.MyVideoViewModelFactory
import kotlinx.coroutines.launch


class MyVideoFragment : Fragment() {
    private lateinit var binding: FragmentMyVideoBinding
    private lateinit var items: MutableList<MyFavoriteVideoEntity>
    private lateinit var visitedPageAdapter: VisitedPageAdapter
    private lateinit var myFavoriteVideoAdapter: MyFavoriteVideoAdapter
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

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            getItems()
        }
    }

    private fun getItems() = lifecycleScope.launch {
        myVideoViewModel.getItems()
    }

    private fun initRv() {
        myVideoViewModel.items.observe(viewLifecycleOwner) { viewModelItems ->
            items = viewModelItems
            visitedPageAdapter = VisitedPageAdapter(items)
            myFavoriteVideoAdapter =
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
                setFragment(id)
            }
        }
    }

    private fun setupMyFavoriteVideoAdapterClickListener(adapter: MyFavoriteVideoAdapter) {
        adapter.itemClick = object : MyFavoriteVideoAdapter.ItemClick {
            override fun itemClick(id: String) {
                setFragment(id)
            }
        }
    }

    private fun setFragment(id: String) {
        val videoDetailFragment = VideoDetailFragment()
        val bundle = Bundle()
        bundle.putString("VideoId", id)
        videoDetailFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_up, R.anim.none, R.anim.none, R.anim.slide_down)
            .replace(R.id.fl, videoDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}