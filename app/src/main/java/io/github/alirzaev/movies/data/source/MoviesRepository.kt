package io.github.alirzaev.movies.data.source

import io.github.alirzaev.movies.data.models.Movie
import io.github.alirzaev.movies.data.models.MovieDetails
import io.github.alirzaev.movies.data.source.remote.response.PaginatedResponse

interface MoviesRepository {

    suspend fun getMovies(page: Int = 1): Result<PaginatedResponse<Movie>>

    suspend fun getCachedMovies(): Result<List<Movie>>

    suspend fun getMovie(id: Int): Result<MovieDetails>

    suspend fun saveMovies(movies: List<Movie>): Result<Unit>

    suspend fun getCachedMovie(movieId: Int): Result<MovieDetails>

    suspend fun saveMovieDetails(movieDetails: MovieDetails): Result<Unit>
}