package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.FragmentVideoDetailBinding
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import com.limheejin.kidstopia.presentation.network.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class VideoDetailFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private val binding by lazy {
        FragmentVideoDetailBinding.inflate(layoutInflater)
    }

    private lateinit var dataList: PopularData
    private lateinit var dao: MyFavoriteVideoDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
//        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initData()
//        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initData() = lifecycleScope.launch {
        dataList = NetworkClient.youtubeApiPopularVideo.getPopularVideoList(
            NetworkClient.AUTH_KEY,
            "snippet, contentDetails",
            "chart",
            10
        )
        val id = dataList.items[0].id
        val channelId = dataList.items[0].snippet.channelId
        val title = dataList.items[0].snippet.title
        val thumbnails = dataList.items[0].snippet.thumbnails.high.url
        val date = LocalDateTime.now()
        val classify = "isLiked"

        val snippet = dataList.items[0].snippet

        binding.tvChannelName.text = snippet.channelId
        binding.tvTitle.text = snippet.title
        binding.tvDescription.text = snippet.description
        Glide.with(binding.ivThumbnail.context)
            .load(snippet.thumbnails)
            .into(binding.ivThumbnail)

        CoroutineScope(Dispatchers.IO).launch {
            dao.insertVideo(
                MyFavoriteVideoEntity(
                    id,
                    title,
                    channelId,
                    thumbnails,
                    date.toString(),
                    classify
                )
            )
            Log.d("checkDb", "${dao.getAllVideo()}")
        }
    }

    private fun initView() = with(binding) {

        CoroutineScope(Dispatchers.IO).launch {

            dataList = NetworkClient.youtubeApiVideo.getVideoData(
                NetworkClient.AUTH_KEY,
                "snippet, contentDetails",
                "6COmYeLsz4c"
            )

            val snippet = dataList.items[0].snippet

            tvChannelName.text = snippet.channelId
            tvTitle.text = snippet.title
            tvDescription.text = snippet.description
            Glide.with(ivThumbnail.context)
                .load(snippet.thumbnails)
                .into(ivThumbnail)
        }

    }

    private fun initListener() = with(binding) {

        btnPlay.setOnClickListener {
            Toast.makeText(context, R.string.toast_detailfragment_play, Toast.LENGTH_SHORT).show()
        }

        btnLikeImg.setOnClickListener {
            val id = dataList.items[0].id
            val channelId = dataList.items[0].snippet.channelId
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
                    dao.insertVideo(MyFavoriteVideoEntity(id,title,channelId,thumbnails,date.toString(),classify))
                    Log.d("checkDb", "${dao.getAllVideo()}")
                }
            } else {
                Toast.makeText(context, R.string.toast_detailfragment_like, Toast.LENGTH_SHORT)
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