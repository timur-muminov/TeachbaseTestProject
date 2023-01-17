package com.teachbaseTestProject.app_dependencies.local.entity.movie_detail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "moviesDetail")
data class LocalMovieDetailDTO(
    @PrimaryKey val remoteId: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "logo") val logo: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "rate") val rate: String?,
    @ColumnInfo(name = "votes") val votes: String?,
    @ColumnInfo(name = "year") val year: String?,
    @ColumnInfo(name = "genre") val genre: String?,

    @TypeConverters(PersonsConverter::class)
    @ColumnInfo(name = "persons") val persons: List<String?>?,
    @ColumnInfo(name = "spokenLanguages") val spokenLanguages: String?,
    @ColumnInfo(name = "description") val description: String?
)