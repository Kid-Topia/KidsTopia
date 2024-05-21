package com.limheejin.kidstopia.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDatabase
import com.limheejin.kidstopia.presentation.network.NetworkClient
import com.limheejin.kidstopia.repository.MyVideoRepository
import com.limheejin.kidstopia.repository.MyVideoRepositoryImpl
import com.limheejin.kidstopia.repository.Repository
import com.limheejin.kidstopia.repository.RepositoryImpl

class HomeViewModel(
    private val databaseRepository: MyVideoRepository,
    private val networkRepository: Repository
): ViewModel()  {




}

class HomeViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val databaseRepository = MyVideoRepositoryImpl(MyFavoriteVideoDatabase.getDatabase(context).getDao())
    private val networkRepository = RepositoryImpl(
        NetworkClient.youtubeApiVideo,
        NetworkClient.youtubeApiSearch
    )
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {

        return HomeViewModel(
            databaseRepository,
            networkRepository
        ) as T
    }
}