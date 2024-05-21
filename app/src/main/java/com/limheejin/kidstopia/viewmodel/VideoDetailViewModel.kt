package com.limheejin.kidstopia.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.PopularSnippet
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDatabase
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import com.limheejin.kidstopia.presentation.network.NetworkClient
import com.limheejin.kidstopia.repository.MyVideoRepository
import com.limheejin.kidstopia.repository.MyVideoRepositoryImpl
import com.limheejin.kidstopia.repository.Repository
import com.limheejin.kidstopia.repository.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class VideoDetailViewModel(
    private val databaseRepository: MyVideoRepository,
    private val networkRepository: Repository
): ViewModel() {
    private val _videoData = MutableLiveData<PopularData>()
    val videoData: LiveData<PopularData> get() = _videoData
    fun fetchVideoData(videoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = networkRepository.getVideo(
                NetworkClient.AUTH_KEY,
                "snippet, contentDetails",
                videoId
            )
            _videoData.postValue(data)
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
                    MyFavoriteVideoEntity(
                        videoId,
                        snippet?.title,
                        snippet?.channelTitle,
                        snippet?.thumbnails?.high?.url,
                        dateString,
                        classify,
                        isLikedDate
                    )
                )
            } else {
                databaseRepository.insertVideo(
                    MyFavoriteVideoEntity(
                        videoId ?: "",
                        snippet?.title,
                        snippet?.channelTitle,
                        snippet?.thumbnails?.high?.url,
                        dateString,
                        "isVisited",
                        null
                    )
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
                    MyFavoriteVideoEntity(
                        videoId ?: "",
                        snippet?.title,
                        snippet?.channelTitle,
                        snippet?.thumbnails?.high?.url,
                        date,
                        "isVisited",
                        null
                    )
                )
                Toast.makeText(context, R.string.toast_detailfragment_dislike, Toast.LENGTH_SHORT).show()
            } else {
                databaseRepository.insertVideo( // DAO에 isVisited 동영상 정보 저장
                    MyFavoriteVideoEntity(
                        videoId ?: "",
                        snippet?.title,
                        snippet?.channelTitle,
                        snippet?.thumbnails?.high?.url,
                        date,
                        "isLiked",
                        dateString
                    )
                )
                Toast.makeText(context, R.string.toast_detailfragment_like, Toast.LENGTH_SHORT).show()
            }
        }
    }
}


class VideoDetailViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val databaseRepository = MyVideoRepositoryImpl(MyFavoriteVideoDatabase.getDatabase(context).getDao())
    private val networkRepository = RepositoryImpl(
        NetworkClient.youtubeApiVideo,
        NetworkClient.youtubeApiSearch
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