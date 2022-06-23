package com.example.shortcaserc.comicAPI

import com.example.shortcaserc.database.ComicObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicAPI {
    @GET("/{comicNumber}/info.0.json")
    suspend fun getSpecificComic(
        @Path("comicNumber") number: Int
    ): Response<ComicObject>
}