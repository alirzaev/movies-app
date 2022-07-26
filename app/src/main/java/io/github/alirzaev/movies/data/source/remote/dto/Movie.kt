package io.github.alirzaev.movies.data.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val poster: String?,
    @SerialName("backdrop_path")
    val backdrop: String?,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("vote_average")
    val rating: Float,
    val adult: Boolean,
    @SerialName("genre_ids")
    val genreIds: List<Int>
)
