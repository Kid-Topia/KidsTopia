package com.kidstopia.kidstopia.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidstopia.kidstopia.R
import com.kidstopia.kidstopia.databinding.FragmentHomeBinding
import com.kidstopia.kidstopia.presentation.adapter.CategoryAdapter
import com.kidstopia.kidstopia.presentation.adapter.ChannelAdapter
import com.kidstopia.kidstopia.presentation.adapter.MostPopularVideoAdapter
import com.kidstopia.kidstopia.viewmodel.HomeViewModel
import com.kidstopia.kidstopia.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterMostPopular: MostPopularVideoAdapter
    private lateinit var adapterCategory: CategoryAdapter
    private lateinit var adapterChannel: ChannelAdapter

    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getItems()
        setupSpinner()
        setupObservers()
        setupRecyclerView()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getItems() = lifecycleScope.launch {
        viewModel.getPopularData()
    }

    private fun setupSpinner() {
        val spinner: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(), R.array.spinner_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> getQuery("뽀로로 다시보기") // 뽀로로
                    1 -> getQuery("핑크퐁 다시보기") // 핑크퐁
                    2 -> getQuery("노리q 동물") // 동물 : 노리q
                    3 -> getQuery("주니토니") // 음악 : 주니토니
                    4 -> getQuery("예림tv") // 동화 : 예림tv
                    5 -> getQuery("깨비키즈 과학") // 과학 : 깨비키즈 과학
                    6 -> getQuery("아이들교실") // 교육 : 아이들교실
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    private fun getQuery(query: String) = lifecycleScope.launch {
        viewModel.getSearchData(query)
    }

    private fun setupObservers() {
        viewModel.getSearchData.observe(viewLifecycleOwner) { searchData ->
            adapterCategory.setCategoryItems(searchData)
        }

        viewModel.getChannelData.observe(viewLifecycleOwner) { channelData ->
            adapterChannel.setItemsChannel(channelData)
        }

        viewModel.getPopularData.observe(viewLifecycleOwner) { popularData ->
            adapterMostPopular.setItems(popularData)

        }
    }

    private fun setupRecyclerView() {
        // onItemClick 구현
        adapterMostPopular = MostPopularVideoAdapter(onItemClick = { position ->
            setFragment(position.id, "VideoId")
        })
        adapterCategory = CategoryAdapter(onItemClick = { position ->
            setFragment(position.id.videoId, "VideoId")
        })
        adapterChannel = ChannelAdapter(onItemClick = { position ->
            setFragment(position.id, "ChannelId")
        })

        //어댑터 연결
        binding.apply {
            homeMostVidio.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            homeMostVidio.adapter = adapterMostPopular
            homeCategory.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            homeCategory.adapter = adapterCategory
            homeChannel.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
            .add(R.id.fl, videoDetailFragment)
            .addToBackStack(null)
            .commit()
    }

}