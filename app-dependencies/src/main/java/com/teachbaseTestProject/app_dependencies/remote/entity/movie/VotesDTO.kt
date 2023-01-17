package com.teachbaseTestProject.app_dependencies.remote.entity.movie

import com.google.gson.annotations.Expose

data class VotesDTO(
    @Expose val kp: String?,
    @Expose val imdb: String?
)