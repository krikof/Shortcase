package com.example.shortcaserc.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shortcaserc.database.ComicObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    private val favoritesRepository: FavoritesRepository = FavoritesRepository()

    val readComic: LiveData<List<ComicObject>> = favoritesRepository.readComic

    fun deleteAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesRepository.deleteAllFavorites()
        }
    }
}