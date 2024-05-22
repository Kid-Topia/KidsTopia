package com.kidstopia.kidstopia.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.kidstopia.kidstopia.R
import com.kidstopia.kidstopia.model.ChannelData
import com.kidstopia.kidstopia.model.PopularData
import com.kidstopia.kidstopia.model.database.MyFavoriteVideoDatabase
import com.kidstopia.kidstopia.model.database.MyFavoriteVideoEntity
import com.kidstopia.kidstopia.presentation.network.NetworkClient
import com.kidstopia.kidstopia.repository.NetworkRepository
import com.kidstopia.kidstopia.repository.NetworkRepositoryImpl
import com.kidstopia.kidstopia.repository.RoomRepository
import com.kidstopia.kidstopia.repository.RoomRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class VideoDetailViewModel(
    private val databaseRepository: RoomRepository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _videoData = MutableLiveData<PopularData>()
    val videoData: LiveData<PopularData> get() = _videoData

    private val _channelData = MutableLiveData<ChannelData>()
    val channelData: LiveData<ChannelData> get() = _channelData

    fun fetchVideoData(videoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = networkRepository.getVideo(
                NetworkClient.AUTH_KEY,
                "snippet",
                videoId
            )
            _videoData.postValue(data)
        }
    }

    fun fetchChannelData(channelId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = networkRepository.getChannel(
                NetworkClient.AUTH_KEY,
                "snippet, statistics",
                channelId
            )
            _channelData.postValue(data)
        }
    }

    fun insertOrUpdateVideo(videoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val classify = databaseRepository.getVideoClassify(videoId)
            val isLikedDate = databaseRepository.getIsLikedDate(videoId)
            val dateString = LocalDateTime.now().toString()
            val snippet = videoData.value?.items?.get(0)?.snippet
            if (classify == "isLiked") {
                databaseRepository.insertVideo(
                    MyFavoriteVideoEntity(videoId, snippet?.title, snippet?.channelTitle, snippet?.thumbnails?.high?.url, dateString, classify, isLikedDate)
                )
            } else {
                databaseRepository.insertVideo(
                    MyFavoriteVideoEntity(videoId, snippet?.title, snippet?.channelTitle, snippet?.thumbnails?.high?.url, dateString, "isVisited", null)
                )
            }
        }
    }

    fun updateLikeStatus(videoId: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val isLikedDate = databaseRepository.getIsLikedDate(videoId)
            val date = databaseRepository.getDate(videoId)
            val snippet = videoData.value?.items?.get(0)?.snippet
            val dateString = LocalDateTime.now().toString()

            if (isLikedDate != null) {
                databaseRepository.insertVideo( // DAO에 isVisited 동영상 정보 저장
                    MyFavoriteVideoEntity(videoId, snippet?.title, snippet?.channelTitle, snippet?.thumbnails?.high?.url, date, "isVisited", null)
                )
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    run {
                        Toast.makeText(context, R.string.toast_detailfragment_dislike, Toast.LENGTH_SHORT).show()
                    }
                }, 0)
            } else {
                databaseRepository.insertVideo( // DAO에 isVisited 동영상 정보 저장
                    MyFavoriteVideoEntity(videoId, snippet?.title, snippet?.channelTitle, snippet?.thumbnails?.high?.url, date, "isLiked", dateString)
                )
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    run {
                        Toast.makeText(context, R.string.toast_detailfragment_like, Toast.LENGTH_SHORT).show()
                    }
                }, 0)
            }
        }
    }
}

class VideoDetailViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val databaseRepository =
        RoomRepositoryImpl(MyFavoriteVideoDatabase.getDatabase(context).getDao())
    private val networkRepository = NetworkRepositoryImpl(
        NetworkClient.youtubeApiVideo,
        NetworkClient.youtubeApiChannel,
        NetworkClient.youtubeApiSearch,
        NetworkClient.youtubeApiPopularVideo,
        NetworkClient.youtubeApiOrderSearch
    )

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        return VideoDetailViewModel(
            databaseRepository,
            networkRepository
        ) as T
    }
}