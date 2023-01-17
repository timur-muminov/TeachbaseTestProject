package com.teachbaseTestProject.movie

data class MovieDetail(
    val id: Int,
    val name: String?,
    val logo: String?,
    val imageUrl: String?,
    val rate: String?,
    val votes: String?,
    val year: String?,
    val genre: String?,
    val persons: List<String?>?,
    val spokenLanguages: String?,
    val description: String?
)