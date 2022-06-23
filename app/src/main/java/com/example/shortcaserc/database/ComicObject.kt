package com.example.shortcaserc.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comic_table")
data class ComicObject(
    val alt: String,
    val day: String,
    val img: String,
    val link: String,
    val month: String,
    val news: String,
    @PrimaryKey
    var num: Int,
    val safe_title: String,
    val title: String,
    val transcript: String,
    val year: String,
    var localImage: Bitmap?
) {
    // @PrimaryKey(autoGenerate = true)
    // var id: Int = 0
}