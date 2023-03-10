package com.teachbaseTestProject.app_dependencies.repository

import com.teachbaseTestProject.app_dependencies.local.entity.movie_detail.LocalMovieDetailDTO
import com.teachbaseTestProject.app_dependencies.remote.entity.movie_detail.MovieDetailDTO
import com.teachbaseTestProject.app_dependencies.remote.entity.movie_detail.PersonDTO
import com.teachbaseTestProject.app_dependencies.repository.model.LocalDataSource
import com.teachbaseTestProject.app_dependencies.repository.model.RemoteDataSource
import com.teachbaseTestProject.entities.movie.MovieDetail
import com.teachbaseTestProject.entities.movie.Person
import com.teachbaseTestProject.movie_detail.model.MovieDetailRepository
import kotlinx.coroutines.flow.Flow

class MovieDetailRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MovieDetailRepository {

    override fun movieDetailFlow(movieId: Int): Flow<MovieDetail?> =
        localDataSource.getMovieDetailByRemoteId(movieId)

    override suspend fun refreshMovieDetailById(movieId: Int) {
        val result = remoteDataSource.getMovieById(movieId)
        localDataSource.updateMovieDetail(result.toLocalMovieDetail())
    }
}

private fun MovieDetailDTO.toLocalMovieDetail() = LocalMovieDetailDTO(
    remoteId = id,
    name = name,
    logo = logo?.url,
    imageUrl = poster?.url,
    rate = rating?.kp,
    votes = votes?.kp,
    year = year,
    genre = type,
    persons = persons?.map { it.toPerson() },
    spokenLanguages = spokenLanguages?.map { it?.nameEn },
    description = description
)

private fun PersonDTO.toPerson() =
    Person(
        id = id,
        photo = photo,
        enName = enName,
        rusName = name,
        enProfession = enProfession
    )


