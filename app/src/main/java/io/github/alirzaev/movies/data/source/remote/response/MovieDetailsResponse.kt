package io.github.alirzaev.movies.data.source.remote.response

import io.github.alirzaev.movies.data.models.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
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
    val runtime: Int,
    @SerialName("genres")
    val genres: List<Genre>
)
