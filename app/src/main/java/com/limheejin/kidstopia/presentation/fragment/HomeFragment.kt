package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.limheejin.kidstopia.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManagerMost: LinearLayoutManager
    private lateinit var layoutManagerCategotry: LinearLayoutManager
    private lateinit var layoutManagerChannel: LinearLayoutManager

    private lateinit var adapterMost: MostFragmentAdapter
    private lateinit var adapterCategoty: CategoryFragmentAdapter
    private lateinit var adapterChannel: ChannelFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root

        // 수평 스크롤
        layoutManagerMost = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        layoutManagerCategotry = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        layoutManagerChannel = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)

        //어뎁터 연결
        adapterMost = MostFragmentAdapter(requireContext())
        adapterCategoty = CategoryFragmentAdapter(requireContext())
        adapterChannel = ChannelFragmentAdapter(requireContext())

        binding.mostVidio.layoutManager = layoutManagerMost
        binding.mostVidio.adapter = adapterMost

        binding.category.layoutManager = layoutManagerCategotry
//        binding.category.adapter = adapterCategoty

        binding.channel.layoutManager = layoutManagerChannel
//        binding.channel.adapter = adapterChannel


        return view
    }

}