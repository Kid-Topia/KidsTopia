package com.kidstopia.kidstopia.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kidstopia.kidstopia.R
import com.kidstopia.kidstopia.databinding.FragmentVideoDetailBinding
import com.kidstopia.kidstopia.model.formatDecimal
import com.kidstopia.kidstopia.viewmodel.VideoDetailViewModel
import com.kidstopia.kidstopia.viewmodel.VideoDetailViewModelFactory

class VideoDetailFragment : Fragment() {
    private var videoId: String? = null
    private var channelId: String? = null

    private val binding by lazy {
        FragmentVideoDetailBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<VideoDetailViewModel> {
        VideoDetailViewModelFactory(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoId = it.getString("VideoId")
            channelId = it.getString("ChannelId")
        }
        videoId?.let { viewModel.fetchVideoData(it) }
        channelId?.let { viewModel.fetchChannelData(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideNavigationView(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideNavigationView(false)
        viewModel.videoData.removeObservers(viewLifecycleOwner)
        viewModel.channelData.removeObservers(viewLifecycleOwner)
    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragmentManager.popBackStack()

    }

    private fun setupObservers() {
        viewModel.videoData.observe(viewLifecycleOwner) { data ->
            val snippet = data.items[0].snippet
            var url = snippet.thumbnails.maxres?.url
            if (snippet.thumbnails.maxres == null) {
                url = snippet.thumbnails.high.url
            }
            with(binding) { // 받아온 동영상 정보로 View 설정
                tvChannelName.text = snippet.channelTitle
                tvTitle.text = snippet.title
                tvDescription.text = snippet.description
                Glide.with(ivThumbnail.context)
                    .load(url)
                    .into(ivThumbnail)
            }
            videoId?.let { viewModel.insertOrUpdateVideo(it) }
        }
        viewModel.channelData.observe(viewLifecycleOwner) { data ->
            val snippet = data.items[0].snippet
            var url = snippet.thumbnails.maxres?.url
            if (snippet.thumbnails.maxres == null) {
                url = snippet.thumbnails.high.url
            }
            with(binding) { // 받아온 동영상 정보로 View 설정
                val subscribeNumber = formatDecimal(data.items[0].statistics.subscriberCount.toInt())
                tvChannelName.text = "구독자 수 : $subscribeNumber"
                tvTitle.text = snippet.title
                tvDescription.text = snippet.description
                Glide.with(ivThumbnail.context)
                    .load(url)
                    .into(ivThumbnail)
                btnLikeImg.visibility = View.GONE
                btnShareImg.visibility = View.GONE
            }
        }
    }

    private fun initListener() = with(binding) {

        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(VideoDetailFragment())
                .commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnPlay.setOnClickListener {
            val urlVideo = "http://www.youtube.com/watch?v=${videoId}"
            val urlChannel = "http://www.youtube.com/channel/${channelId}"

            if (videoId != null) {
                Toast.makeText(context, R.string.toast_detailfragment_play1, Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlVideo)))
            } else {
                Toast.makeText(context, R.string.toast_detailfragment_play2, Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlChannel)))
            }
        }

        btnShareImg.setOnClickListener { // 공유 버튼 클릭 시 실행 (미구현)
            Toast.makeText(context, R.string.toast_detailfragment_share, Toast.LENGTH_SHORT).show()
        }

        binding.btnLikeImg.setOnClickListener {
            videoId?.let { viewModel.updateLikeStatus(it, requireContext()) }
        }
    }

    private fun hideNavigationView(bool: Boolean) {
        val nav = activity?.findViewById<BottomNavigationView>(R.id.nav)
        if (bool) {
            nav?.visibility = View.GONE
        } else nav?.visibility = View.VISIBLE
    }
}