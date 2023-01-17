package com.teachbaseTestProject.app_dependencies.remote.entity.movie

import com.google.gson.annotations.Expose

data class PosterDTO(
    @Expose val url: String,
    @Expose val previewUrl: String
)