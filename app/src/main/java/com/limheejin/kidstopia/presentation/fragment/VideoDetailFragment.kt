package com.limheejin.kidstopia.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.FragmentVideoDetailBinding
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDatabase
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import com.limheejin.kidstopia.presentation.adapter.VisitedPageAdapter
import com.limheejin.kidstopia.presentation.network.NetworkClient
import com.limheejin.kidstopia.viewmodel.VideoDetailViewModel
import com.limheejin.kidstopia.viewmodel.VideoDetailViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val ARG_PARAM1 = "VideoId"
private const val ARG_PARAM2 = "param2"

class VideoDetailFragment : Fragment() {
    private var videoId: String? = null
    private var param2: String? = null
    private val binding by lazy {
        FragmentVideoDetailBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<VideoDetailViewModel> {
        VideoDetailViewModelFactory(requireContext())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoId = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        videoId?.let { viewModel.fetchVideoData(it) }
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
        initObserve()
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideNavigationView(false)
        viewModel.videoData.removeObservers(viewLifecycleOwner)
    }

    private fun initObserve() {
        viewModel.videoData.observe(viewLifecycleOwner) { data ->
            val snippet = data.items[0].snippet
            binding.tvChannelName.text = snippet.channelTitle
            binding.tvTitle.text = snippet.title
            binding.tvDescription.text = snippet.description
            Glide.with(binding.ivThumbnail.context)
                .load(snippet.thumbnails.medium.url)
                .into(binding.ivThumbnail)

<<<<<<< HEAD
        CoroutineScope(Dispatchers.IO).launch {
            val classify = videoId?.let { dao.getVideoClassify(it) }
            val isLikedDate = videoId?.let { dao.getVideoLikedDate(it) }

            if (classify != null) {
                dao.insertVideo( // DAO에 isVisited 동영상 정보 저장
                    MyFavoriteVideoEntity(
                        videoId?:"", snippet.title, snippet.channelTitle, snippet.thumbnails.maxres.url, dateString, classify, isLikedDate
                    )
                )
            } else {
                dao.insertVideo( // DAO에 isVisited 동영상 정보 저장
                    MyFavoriteVideoEntity(
                        videoId?:"", snippet.title, snippet.channelTitle, snippet.thumbnails.maxres.url, dateString, "isVisited", null
                    )
                )
            }
            Log.d("checkDb", "${dao.getAllVideo()}")
        }
    }
    private fun initListenerCoroutineScope() = lifecycleScope.launch {
        binding.btnLikeImg.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val isLikedDate = videoId?.let { dao.getVideoLikedDate(it) }
                val date = videoId?.let { it1 -> dao.getVideoDate(it1) }
                if (isLikedDate != null) {
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        run {
                            Toast.makeText(context, R.string.toast_detailfragment_dislike, Toast.LENGTH_SHORT).show()
                        }
                    }, 0)
                    dao.insertVideo( // DAO에 isVisited 동영상 정보 저장
                        MyFavoriteVideoEntity(
                            videoId ?: "",
                            snippet.title,
                            snippet.channelTitle,
                            snippet.thumbnails.maxres.url,
                            date,
                            "isVisited",
                            null
                        )
                    )
                } else {
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        run {
                            Toast.makeText(context, R.string.toast_detailfragment_like, Toast.LENGTH_SHORT).show()
                        }
                    }, 0)
                    dao.insertVideo( // DAO에 isVisited 동영상 정보 저장
                        MyFavoriteVideoEntity(
                            videoId ?: "",
                            snippet.title,
                            snippet.channelTitle,
                            snippet.thumbnails.maxres.url,
                            date,
                            "isLiked",
                            dateString
                        )
                    )
                }
            }
=======
            videoId?.let { viewModel.insertOrUpdateVideo(it) }
>>>>>>> 6c162808708b695817f736f0a952407f05ac2e06
        }
    }



    private fun initListener() = with(binding) {
        btnPlay.setOnClickListener {
            Toast.makeText(context, R.string.toast_detailfragment_play, Toast.LENGTH_SHORT).show()
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=${videoId}")))
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VideoDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}