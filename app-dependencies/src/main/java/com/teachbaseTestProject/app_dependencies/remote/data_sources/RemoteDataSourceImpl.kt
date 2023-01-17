package com.teachbaseTestProject.app_dependencies.remote.data_sources

import com.teachbaseTestProject.app_dependencies.exceptions_handler.NetworkExceptionMapper
import com.teachbaseTestProject.app_dependencies.remote.api.RemoteMoviesAPI
import com.teachbaseTestProject.app_dependencies.remote.entity.movie.MoviesDTO
import com.teachbaseTestProject.app_dependencies.remote.entity.movie_detail.MovieDetailDTO
import com.teachbaseTestProject.app_dependencies.remote.request_builder.RequestBuilder
import com.teachbaseTestProject.app_dependencies.repository.model.RemoteDataSource
import com.teachbaseTestProject.filter.MovieFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSourceImpl(
    private val requestBuilder: RequestBuilder,
    private val exceptionMapper: NetworkExceptionMapper,
    private val remoteMoviesAPI: RemoteMoviesAPI
) : RemoteDataSource {

    override suspend fun getMoviesByFilters(movieFilter: MovieFilter, page: Int): MoviesDTO =
        withContext(Dispatchers.IO) {
            val request = requestBuilder.buildRequestByFilters(movieFilter, page)
            exceptionMapper.handle { remoteMoviesAPI.searchMovie(request) }
        }


    override suspend fun getMovieById(movieId: Int): MovieDetailDTO = withContext(Dispatchers.IO) {
        val request = requestBuilder.buildRequestById(movieId)
        exceptionMapper.handle { remoteMoviesAPI.searchMovieDetail(request) }
    }


    override suspend fun getMoviesByName(movieName: String, page: Int): MoviesDTO = withContext(Dispatchers.IO) {
        val request = requestBuilder.buildRequestByName(movieName, page)
        exceptionMapper.handle { remoteMoviesAPI.searchMovie(request) }
    }
}


