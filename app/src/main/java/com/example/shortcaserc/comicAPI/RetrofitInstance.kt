package com.example.shortcaserc.comicAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ComicAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://xkcd.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ComicAPI::class.java)
    }
}