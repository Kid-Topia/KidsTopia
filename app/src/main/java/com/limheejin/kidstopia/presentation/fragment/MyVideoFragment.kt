package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.FragmentMyVideoBinding
import com.limheejin.kidstopia.presentation.adapter.MyFavoriteVideoAdapter
import com.limheejin.kidstopia.presentation.adapter.VisitedPageAdapter
import com.limheejin.kidstopia.viewmodel.MyVideoViewModel
import com.limheejin.kidstopia.viewmodel.MyVideoViewModelFactory
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyVideoFragment : Fragment() {
    private lateinit var binding: FragmentMyVideoBinding
    private val myVideoViewModel by viewModels<MyVideoViewModel> {
        MyVideoViewModelFactory(requireContext())
    }

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

    private fun getItems() = lifecycleScope.launch {
        myVideoViewModel.getItems()
    }

    private fun initRv() {
        myVideoViewModel.items.observe(viewLifecycleOwner) { items ->
            val visitedPageAdapter = VisitedPageAdapter(items)
            val myFavoriteVideoAdapter =
                MyFavoriteVideoAdapter(items.filter { it.classify == "isLiked" }.toMutableList())

            myFavoriteVideoAdapter.itemClick = object : MyFavoriteVideoAdapter.ItemClick {
                override fun itemClick(id: String) {
                    setFragment(id)
                }
            }

            visitedPageAdapter.itemClick = object : VisitedPageAdapter.ItemClick {
                override fun itemClick(id: String) {
                    setFragment(id)
                }
            }

            binding.apply {
                favoriteVideoRv.adapter = myFavoriteVideoAdapter
                visitedPageRv.adapter = visitedPageAdapter
                favoriteVideoRv.layoutManager = LinearLayoutManager(requireContext())
                visitedPageRv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun setFragment(id: String) {
        val videoDetailFragment = VideoDetailFragment()
        val bundle = Bundle() // 일단 번들로 구현
        bundle.putString("VideoId", id)
        videoDetailFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_up, R.anim.none, R.anim.none, R.anim.slide_down)
            .replace(R.id.fl, videoDetailFragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyVideoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}