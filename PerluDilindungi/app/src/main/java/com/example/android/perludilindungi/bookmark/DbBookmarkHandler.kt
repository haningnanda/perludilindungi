package com.example.android.perludilindungi.bookmark

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DbBookmark::class], version = 1)
abstract class DbBookmarkHandler: RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    companion object{
        private var INSTANS: DbBookmarkHandler? =null
        private var INSTANS2 : DbBookmarkHandler? =null
        fun getInstance(context: Context?, allowMainThread: Boolean = false): DbBookmarkHandler{
            if(INSTANS == null && context != null){
                INSTANS2 = Room.databaseBuilder(
                    context,
                    DbBookmarkHandler::class.java,
                    "dbbookmark"
                ).allowMainThreadQueries().build()
                INSTANS = Room.databaseBuilder(
                    context,
                    DbBookmarkHandler::class.java,
                    "dbbookmark"
                ).build()
            }

            if(allowMainThread){
                return INSTANS2 as DbBookmarkHandler
            }
            return INSTANS as DbBookmarkHandler
        }
    }
}