package io.github.alirzaev.movies.features.moviedetails

import io.github.alirzaev.movies.data.models.MovieDetails

data class MovieDetailsUiState(
    val isLoading: Boolean = false,
    val movie: MovieDetails? = null,
    val error: Throwable? = null
)
