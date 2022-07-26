package io.github.alirzaev.movies.features.movies

import io.github.alirzaev.movies.data.models.Movie

data class MoviesListUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: Throwable? = null
)
