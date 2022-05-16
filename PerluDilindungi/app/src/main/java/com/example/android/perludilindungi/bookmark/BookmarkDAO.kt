package com.example.android.perludilindungi.bookmark

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Query("SELECT * from dbbookmark")
    fun getAllBookmark():List<DbBookmark>

    @Query("SELECT * from dbbookmark")
    fun liveGetAllBookmark():LiveData<List<DbBookmark>>

    @Insert
    fun insertBookmark(vararg bookmark: DbBookmark)

    @Query("SELECT * FROM dbbookmark where id=:id")
    fun getBookmarkById(id:String): DbBookmark?

    @Delete
    fun delete(bookmark: DbBookmark)
}