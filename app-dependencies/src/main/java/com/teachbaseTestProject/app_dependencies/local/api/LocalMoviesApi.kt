package com.teachbaseTestProject.app_dependencies.local.api

import androidx.room.*
import com.teachbaseTestProject.app_dependencies.local.entity.movie.LocalMovieDTO
import com.teachbaseTestProject.app_dependencies.local.entity.movie_detail.LocalMovieDetailDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalMoviesApi {

    @Query("SELECT * FROM movies WHERE localId = (:localId)")
    fun getMoviesByFilters(localId: String): Flow<List<LocalMovieDTO>>

    /*@Query("SELECT * FROM movies WHERE remoteId = (:remoteId)")
    fun getMovieByRemoteId(remoteId: Int): LocalMovieDTO?*/

    @Insert(LocalMovieDTO::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(localMovieDTO: LocalMovieDTO)


    @Query("SELECT * FROM moviesDetail WHERE remoteId = (:remoteId)")
    fun getMovieDetailByRemoteId(remoteId: Int): Flow<LocalMovieDetailDTO?>

    @Insert(LocalMovieDetailDTO::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(localMovieDetailDTO: LocalMovieDetailDTO)

    @Query("SELECT * FROM movies")
    fun getAll(): List<LocalMovieDTO>

}