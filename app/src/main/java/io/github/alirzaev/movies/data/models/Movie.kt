package io.github.alirzaev.movies.data.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String?,
    val backdrop: String?,
    val rating: Float,
    val voteCount: Int,
    val adult: Boolean,
    val genres: List<Genre>
)