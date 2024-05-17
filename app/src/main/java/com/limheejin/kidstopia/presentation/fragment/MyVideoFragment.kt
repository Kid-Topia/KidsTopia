package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.limheejin.kidstopia.databinding.FragmentMyVideoBinding
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
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

    lateinit var items: MutableLiveData<MutableList<MyFavoriteVideoEntity>>
    lateinit var testData : PopularData
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
        // Inflate the layout for this fragment
        binding = FragmentMyVideoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getItems()

        myVideoViewModel.items.observe(viewLifecycleOwner) { items ->
            val myFavoriteVideoAdapter =
                MyFavoriteVideoAdapter(items.filter { it.classify == "isLiked" }.toMutableList())
            val visitedPageAdapter =
                VisitedPageAdapter(items.filter { it.classify == "isVisited" }.toMutableList())

            binding.apply {
                favoriteVideoRv.adapter = myFavoriteVideoAdapter
                visitedPageRv.adapter = visitedPageAdapter
                favoriteVideoRv.layoutManager = LinearLayoutManager(requireContext())
                visitedPageRv.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    fun getItems() = lifecycleScope.launch {
        myVideoViewModel.getItems()
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