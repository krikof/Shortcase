package com.example.shortcaserc.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.shortcaserc.comicAPI.RetrofitInstance
import com.example.shortcaserc.database.ComicDAO
import com.example.shortcaserc.database.ComicDatabase
import com.example.shortcaserc.database.ComicObject
import com.example.shortcaserc.utils.ShortcaseApplication


class HomeRepository {
    // Comic DAO
    private val comicDAO: ComicDAO =
        ComicDatabase.getDatabase(ShortcaseApplication.application.applicationContext).comicDAO()

    // Holds info of where we currently are in the comic trail.
    var currentComicCounter: Int = 1

    // Inits the first comic onCreate
    suspend fun initFirstComic(callback: (ComicObject) -> Unit) {
        val response = RetrofitInstance.api.getSpecificComic(currentComicCounter)

        if (response.isSuccessful && response.body() != null) {
            callback(response.body()!!)
        }
    }

    suspend fun loadPreviousComic(callback: (ComicObject) -> Unit) {
        if (currentComicCounter > 1) {
            currentComicCounter--
        }

        val response = RetrofitInstance.api.getSpecificComic(currentComicCounter)

        if (response.isSuccessful && response.body() != null) {
            callback(response.body()!!)
        }
    }

    suspend fun loadNextComic(callback: (ComicObject) -> Unit) {
        currentComicCounter++

        val response = RetrofitInstance.api.getSpecificComic(currentComicCounter)

        if (response.isSuccessful && response.body() != null) {
            callback(response.body()!!)
        } else {
            currentComicCounter--
        }
    }

    suspend fun favoriteComic(comicObject: ComicObject) {
        comicObject.localImage = getBitMap(comicObject)
        comicDAO.insertComicObject(comicObject)
    }

    // Opens share dialogue
    suspend fun shareComic(comicObject: ComicObject) {

    }

    // Simulates notifications for PoC purpose
    suspend fun simulateNotification(comicObject: ComicObject) {

    }

    // Generating local bitmap for DB
    private suspend fun getBitMap(comicObject: ComicObject): Bitmap {
        val url = comicObject.img

        val loading = ImageLoader(ShortcaseApplication.application)
        val request = ImageRequest.Builder(ShortcaseApplication.application)
            .data(url)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    // Check for internet connectivity
    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(callback: (Boolean) -> Unit) {
        val context = ShortcaseApplication.application

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    callback(true)
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    callback(true)
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    callback(true)
                }
            }
        }
        callback(false)
    }
}