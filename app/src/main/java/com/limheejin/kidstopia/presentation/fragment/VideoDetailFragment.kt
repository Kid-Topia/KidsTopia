package com.limheejin.kidstopia.presentation.fragment

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.FragmentVideoDetailBinding
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDatabase
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import com.limheejin.kidstopia.presentation.network.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDateTime

private const val ARG_PARAM1 = "VideoId"
private const val ARG_PARAM2 = "param2"

class VideoDetailFragment : Fragment() {
    private var videoId: String? = null
    private var param2: String? = null

    private val binding by lazy {
        FragmentVideoDetailBinding.inflate(layoutInflater)
    }

    private lateinit var dataList: PopularData
    private lateinit var dao: MyFavoriteVideoDAO
    private lateinit var deferred: Deferred<PopularData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoId = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initData() {
        deferred = CoroutineScope(Dispatchers.IO).async {
            return@async NetworkClient.youtubeApiVideo.getVideoData(
                NetworkClient.AUTH_KEY,
                "snippet, contentDetails",
                "9fdr8ah-4A4" // videoId
            )
        }
        dao = MyFavoriteVideoDatabase.getDatabase(requireContext()).getDao()
    }

    private fun initView() = lifecycleScope.launch {
        dataList = deferred.await()
        val snippet = dataList.items[0].snippet

        with(binding){
            tvChannelName.text = snippet.channelTitle
            tvTitle.text = snippet.title
            tvDescription.text = snippet.description
            Glide.with(ivThumbnail.context)
                .load(snippet.thumbnails.medium.url)
                .into(ivThumbnail)
        }
    }

    private fun initListener() = with(binding) {

        btnPlay.setOnClickListener {
            Toast.makeText(context, R.string.toast_detailfragment_play, Toast.LENGTH_SHORT).show()
        }

        btnLikeImg.setOnClickListener {
            val id = dataList.items[0].id
            val channelTitle = dataList.items[0].snippet.channelTitle
            val title = dataList.items[0].snippet.title
            val thumbnails = dataList.items[0].snippet.thumbnails.high.url
            val date = LocalDateTime.now()
            val classify = "isLiked"
            var selector = btnLikeImg.isSelected

            selector = selector != true

            if (selector) {
                Toast.makeText(context, R.string.toast_detailfragment_like, Toast.LENGTH_SHORT)
                    .show()
                CoroutineScope(Dispatchers.IO).launch {
                    dao.insertVideo(
                        MyFavoriteVideoEntity(id,title,channelTitle,thumbnails,date.toString(),classify
                        )
                    )
                    Log.d("checkDb", "${dao.getAllVideo()}")
                }
            } else {
                Toast.makeText(context, R.string.toast_detailfragment_dislike, Toast.LENGTH_SHORT)
                    .show()
                CoroutineScope(Dispatchers.IO).launch {
                    dao.deleteVideo(id)
                    Log.d("checkDb", "${dao.getAllVideo()}")
                }
            }

        }

        btnShareImg.setOnClickListener {
            Toast.makeText(context, R.string.toast_detailfragment_share, Toast.LENGTH_SHORT).show()
        }
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