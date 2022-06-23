package com.example.shortcaserc.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shortcaserc.database.ComicObject
import kotlinx.coroutines.launch
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.Dispatchers


class HomeViewModel : ViewModel() {
    private val mainRepository: HomeRepository = HomeRepository()

    val currentComicInView: MutableLiveData<ComicObject> by lazy {
        MutableLiveData<ComicObject>()
    }

    // ComicCardView functions
    fun initFirstComic() {
        viewModelScope.launch {
            mainRepository.initFirstComic {
                currentComicInView.postValue(it)
            }
        }
    }

    fun explainComic(callback: (ComicObject) -> Unit) {
        viewModelScope.launch {
            mainRepository.favoriteComic(currentComicInView.value!!)
        }
    }

    fun favoriteComic() {
        viewModelScope.launch(Dispatchers.IO) {
           if (currentComicInView != null) {
               mainRepository.favoriteComic(currentComicInView.value!!)
           }
        }
    }

    fun shareComic(callback: (ComicObject) -> Unit) {
        viewModelScope.launch {
            callback(currentComicInView.value!!)
        }
    }

    fun simulateNotification(callback: (ComicObject) -> Unit) {
        viewModelScope.launch {
            callback(currentComicInView.value!!)
        }
    }


    // Fragment functions
    fun previousComic() {
        viewModelScope.launch {
            mainRepository.loadPreviousComic {
                currentComicInView.postValue(it)
            }
        }
    }

    fun nextComic() {
        viewModelScope.launch {
            mainRepository.loadNextComic {
                currentComicInView.postValue(it)
            }
        }
    }

    // Check for internet connectivity
    fun checkForInternetConnectivity(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            mainRepository.isOnline {
                callback(it)
            }
        }
    }

    // ViewModel factory
    class Factory(
        owner: SavedStateRegistryOwner
    ) : AbstractSavedStateViewModelFactory(owner, null) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return HomeViewModel() as T
        }
    }
}