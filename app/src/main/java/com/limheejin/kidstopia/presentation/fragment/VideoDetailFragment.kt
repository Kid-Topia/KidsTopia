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
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDatabase
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import com.limheejin.kidstopia.presentation.network.NetworkClient
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

    private lateinit var dataList: PopularData
    private lateinit var deferred: Deferred<PopularData>

    private val binding by lazy {
        FragmentVideoDetailBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        MyFavoriteVideoDatabase.getDatabase(requireContext()).getDao()
    }
    private val snippet by lazy {
        dataList.items[0].snippet
    }
    private val dateString by lazy {
        LocalDateTime.now().toString()
    }


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
        initListenerCoroutineScope()
    }

    private fun initData() { // bundle로 받아온 videoId로 Video API에서 동영상 정보 받아오기
        deferred = CoroutineScope(Dispatchers.IO).async {
            return@async NetworkClient.youtubeApiVideo.getVideoData(
                NetworkClient.AUTH_KEY,
                "snippet, contentDetails",
                videoId?:""
            )
        }
    }

    private fun initView() = lifecycleScope.launch {
        dataList = deferred.await() // 받아온 동영상 정보 처리가 끝난 후에 dataList에 할당

        with(binding){ // 받아온 동영상 정보로 View 설정
            tvChannelName.text = snippet.channelTitle
            tvTitle.text = snippet.title
            tvDescription.text = snippet.description
            Glide.with(ivThumbnail.context)
                .load(snippet.thumbnails.medium.url)
                .into(ivThumbnail)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val classify = videoId?.let { dao.getVideoClassify(it) }
            val isLikedDate = videoId?.let { dao.getVideoLikedDate(it) }

            if (classify != null) {
                dao.insertVideo( // DAO에 isVisited 동영상 정보 저장
                    MyFavoriteVideoEntity(
                        videoId?:"", snippet.title, snippet.channelTitle, snippet.thumbnails.high.url, dateString, classify, isLikedDate
                    )
                )
            } else {
                dao.insertVideo( // DAO에 isVisited 동영상 정보 저장
                    MyFavoriteVideoEntity(
                        videoId?:"", snippet.title, snippet.channelTitle, snippet.thumbnails.high.url, dateString, "isVisited", null
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
                    //Toast.makeText(context, R.string.toast_detailfragment_dislike, Toast.LENGTH_SHORT)
                    dao.insertVideo( // DAO에 isVisited 동영상 정보 저장
                        MyFavoriteVideoEntity(
                            videoId ?: "",
                            snippet.title,
                            snippet.channelTitle,
                            snippet.thumbnails.high.url,
                            date,
                            "isVisited",
                            null
                        )
                    )
                } else {
                    //Toast.makeText(context, R.string.toast_detailfragment_like, Toast.LENGTH_SHORT).show()
                    dao.insertVideo( // DAO에 isVisited 동영상 정보 저장
                        MyFavoriteVideoEntity(
                            videoId ?: "",
                            snippet.title,
                            snippet.channelTitle,
                            snippet.thumbnails.high.url,
                            date,
                            "isLiked",
                            dateString
                        )
                    )
                }
            }
        }
    }

    private fun initListener() = with(binding) {

        btnPlay.setOnClickListener {
            Toast.makeText(context, R.string.toast_detailfragment_play, Toast.LENGTH_SHORT).show()
        }

        btnShareImg.setOnClickListener { // 공유 버튼 클릭 시 실행 (미구현)
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