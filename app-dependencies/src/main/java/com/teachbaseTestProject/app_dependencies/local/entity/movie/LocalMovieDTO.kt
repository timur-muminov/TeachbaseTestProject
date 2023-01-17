package com.teachbaseTestProject.app_dependencies.local.entity.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class LocalMovieDTO(
    @PrimaryKey val remoteId: Int,
    @ColumnInfo(name = "localId") val localId: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "secondName") val secondName: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "genre") val genre: String?,
    @ColumnInfo(name = "rate") val rate: String?,
    @ColumnInfo(name = "votes") val votes: String?
)