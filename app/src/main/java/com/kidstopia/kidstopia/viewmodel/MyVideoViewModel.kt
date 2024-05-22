package com.kidstopia.kidstopia.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.kidstopia.kidstopia.model.database.MyFavoriteVideoDatabase
import com.kidstopia.kidstopia.model.database.MyFavoriteVideoEntity
import com.kidstopia.kidstopia.repository.RoomRepository
import com.kidstopia.kidstopia.repository.RoomRepositoryImpl
import kotlinx.coroutines.launch

class MyVideoViewModel(private val repository: RoomRepository) : ViewModel() {

    private val _items: MutableLiveData<MutableList<MyFavoriteVideoEntity>> = MutableLiveData()
    val items: LiveData<MutableList<MyFavoriteVideoEntity>> get() = _items

    fun getItems() = viewModelScope.launch {
        _items.value = repository.getAllVideo()
    }
}

class MyVideoViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val repository =
        RoomRepositoryImpl(MyFavoriteVideoDatabase.getDatabase(context).getDao())

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        return MyVideoViewModel(
            repository
        ) as T
    }
}