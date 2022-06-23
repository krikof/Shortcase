package com.example.shortcaserc.ui.favorites

import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.shortcaserc.database.ComicDatabase
import com.example.shortcaserc.database.ComicObject
import com.example.shortcaserc.utils.ShortcaseApplication


class FavoritesRepository {

    private val comicDAO = ComicDatabase.getDatabase(ShortcaseApplication.application).comicDAO()

    val readComic: LiveData<List<ComicObject>> = comicDAO.readComic()

    fun loadAllFavorites() {
        comicDAO.getAllFavorites()
    }

    fun deleteAllFavorites() {
        comicDAO.deleteAllFavorites()
    }

    fun toast(message: String) {
        Toast.makeText(ShortcaseApplication.application, message, Toast.LENGTH_SHORT).show()
    }
}