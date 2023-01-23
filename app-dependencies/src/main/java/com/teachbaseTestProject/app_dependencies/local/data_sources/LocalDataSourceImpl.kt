package com.teachbaseTestProject.app_dependencies.local.data_sources

import android.util.Log
import com.teachbaseTestProject.app_dependencies.local.api.LocalMoviesApi
import com.teachbaseTestProject.app_dependencies.local.entity.movie.LocalMovieDTO
import com.teachbaseTestProject.app_dependencies.local.entity.movie_detail.LocalMovieDetailDTO
import com.teachbaseTestProject.app_dependencies.local.localId_builder.LocalIdBuilder
import com.teachbaseTestProject.app_dependencies.remote.entity.movie.MovieDTO
import com.teachbaseTestProject.app_dependencies.remote.entity.movie.MoviesDTO
import com.teachbaseTestProject.app_dependencies.repository.model.LocalDataSource
import com.teachbaseTestProject.entities.filter.MovieFilter
import com.teachbaseTestProject.entities.movie.Movie
import com.teachbaseTestProject.entities.movie.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
    private val localMoviesApi: LocalMoviesApi,
    private val localIdBuilder: LocalIdBuilder,
) : LocalDataSource {

    override fun getMoviesByFilters(movieFilter: MovieFilter): Flow<List<Movie>> {
        val localId = localIdBuilder.buildLocalIdByFilters(movieFilter)
        return localMoviesApi.getMoviesByFilters(localId).map { it.toMovieList() }.flowOn(Dispatchers.IO)
    }

    override fun getMovieDetailByRemoteId(remoteId: Int): Flow<MovieDetail?> =
        localMoviesApi.getMovieDetailByRemoteId(remoteId).map {
            val o = it?.toMovieDetail()
            Log.e("taaaag", o.toString())
            return@map o
        }.flowOn(Dispatchers.IO)


    override suspend fun updateMovies(movieFilter: MovieFilter, moviesDTO: MoviesDTO) = withContext(Dispatchers.IO) {
        moviesDTO.docs.forEach {
            localMoviesApi.insertMovie(it.toLocalMovieDTO(movieFilter))
        }
    }

    override suspend fun updateMovieDetail(localMovieDetailDTO: LocalMovieDetailDTO) = withContext(Dispatchers.IO) {
        Log.e("taaaag", localMovieDetailDTO.toString())
        localMoviesApi.insertMovieDetail(localMovieDetailDTO)
    }

    private fun List<LocalMovieDTO>.toMovieList() = map { it.toMovie() }
    private fun LocalMovieDTO.toMovie() = Movie(
        id = remoteId,
        name = name,
        secondName = secondName,
        imageUrl = imageUrl,
        genre = genre,
        rate = rate,
        votes = votes,
    )

    private fun LocalMovieDetailDTO.toMovieDetail() = MovieDetail(
        id = remoteId,
        name = name,
        logo = logo,
        imageUrl = imageUrl,
        rate = rate,
        votes = votes,
        year = year,
        genre = genre,
        persons = persons,
        spokenLanguages = spokenLanguages,
        description = description
    )

    private fun MovieDTO.toLocalMovieDTO(movieFilter: MovieFilter) = LocalMovieDTO(
        remoteId = id,
        localId = localIdBuilder.buildLocalIdByFilters(movieFilter),
        name = name,
        secondName = alternativeName,
        imageUrl = poster?.previewUrl,
        genre = type,
        rate = rating?.kp,
        votes = votes?.kp
    )
}