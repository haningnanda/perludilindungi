package com.example.android.perludilindungi.bookmark

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize
import com.example.android.perludilindungi.network.Faskes

data class BookmarkData(
    val id: String,
    val dataFaskes: Faskes
)

@Entity
data class DbBookmark(
    @PrimaryKey val id: String,
    @ColumnInfo(name="faskesjson") val faskesJson : String
)
