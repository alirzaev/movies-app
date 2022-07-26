package io.github.alirzaev.movies.data.source.remote.response

import io.github.alirzaev.movies.data.models.Genre
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(val genres: List<Genre>)
