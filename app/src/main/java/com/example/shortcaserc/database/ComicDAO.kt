package com.example.shortcaserc.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ComicDAO {

    // Inserts comic to favorites
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComicObject(comic: ComicObject)

    // Removes specific comic from favorites
    @Query("DELETE FROM comic_table where num = :id")
    fun removeComicObject(id: Long)

    // Fetches all comics from favorites
    @Query("SELECT * FROM comic_table")
    fun getAllFavorites(): List<ComicObject>

    // Deletes all favorites from favorites
    @Query("DELETE FROM comic_table")
    fun deleteAllFavorites()

    //Live data func
    @Query("SELECT * FROM comic_table")
    fun readComic(): LiveData<List<ComicObject>>
}