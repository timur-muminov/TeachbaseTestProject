package com.teachbaseTestProject.app_dependencies.remote.entity.movie_detail

import com.google.gson.annotations.Expose
import com.teachbaseTestProject.app_dependencies.remote.entity.movie.PosterDTO
import com.teachbaseTestProject.app_dependencies.remote.entity.movie.RatingDTO
import com.teachbaseTestProject.app_dependencies.remote.entity.movie.VotesDTO

data class MovieDetailDTO(
    @Expose val id: Int,
    @Expose val name: String?,
    @Expose val logo: LogoDTO?,
    @Expose val poster: PosterDTO?,
    @Expose val rating: RatingDTO?,
    @Expose val votes: VotesDTO?,
    @Expose val year: String?,
    @Expose val type: String?,
    @Expose val persons: List<PersonDTO>?,
    @Expose val spokenLanguages: LanguageDTO?,
    @Expose val description: String?
)