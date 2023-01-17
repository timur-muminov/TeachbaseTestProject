package com.teachbaseTestProject.app_dependencies.remote.entity.movie

import com.google.gson.annotations.Expose

data class MoviesDTO(
    @Expose val docs: List<MovieDTO>
)
