package io.github.alirzaev.movies.data.models

data class Movie(
    val name: String,
    val rating: Double,
    val ageRestriction: Int,
    val poster: Int,
    val tags: List<String>,
    val reviews: List<String>,
    val runningTime: Int,
    val actors: List<Actor>,
    val liked: Boolean
)
