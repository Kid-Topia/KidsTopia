package com.limheejin.kidstopia.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.limheejin.kidstopia.model.SearchItems
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDatabase
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import com.limheejin.kidstopia.presentation.network.NetworkClient
import com.limheejin.kidstopia.repository.MyVideoRepository
import com.limheejin.kidstopia.repository.MyVideoRepositoryImpl
import com.limheejin.kidstopia.repository.Repository
import com.limheejin.kidstopia.repository.RepositoryImpl
import kotlinx.coroutines.launch

class MyVideoViewModel(private val repository: MyVideoRepository): ViewModel() {
    private val _getItems: MutableLiveData<MutableList<MyFavoriteVideoEntity>> = MutableLiveData()
    val getItems: MutableLiveData<MutableList<MyFavoriteVideoEntity>> get() =_getItems
    fun getItems() = viewModelScope.launch {
        _getItems.value = repository.getAllVideo()
    }
    fun insert(myFavoriteVideo: MyFavoriteVideoEntity) {
        repository.insertVideo(myFavoriteVideo)
    }

    fun delete(video_id: String) {
        repository.deleteVideo(video_id)
    }
}

class MyVideoViewModelFactory(application: Application) : ViewModelProvider.Factory {
    private val repository = MyVideoRepositoryImpl(MyFavoriteVideoDatabase.getDatabase(application).getDao())
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {

        return MyVideoViewModel(
            repository
        ) as T
    }
}