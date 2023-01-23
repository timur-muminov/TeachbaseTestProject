package com.teachbaseTestProject.app_dependencies.remote.entity.movie_detail

import com.google.gson.annotations.Expose

data class PersonDTO(
    @Expose val id: Int,
    @Expose val photo: String?,
    @Expose val enName: String?,
    @Expose val name: String?,
    @Expose val enProfession: String?
)
