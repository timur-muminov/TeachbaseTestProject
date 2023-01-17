package com.teachbaseTestProject.app_dependencies.remote.entity.movie

import com.google.gson.annotations.Expose

data class MovieDTO(
    @Expose val id: Int,
    @Expose val name: String?,
    @Expose val alternativeName: String?,
    @Expose val poster: PosterDTO?,
    @Expose val type: String?,
    @Expose val rating: RatingDTO?,
    @Expose val votes: VotesDTO?
)