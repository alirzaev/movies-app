package io.github.alirzaev.movies.data.source.local

import io.github.alirzaev.movies.data.models.Movie
import io.github.alirzaev.movies.data.models.MovieDetails

interface LocalMoviesDataSource {

    suspend fun getMovies(): List<Movie>

    suspend fun saveMovie(movie: Movie)

    suspend fun getMovieDetails(movieId: Int): MovieDetails

    suspend fun saveMovieDetails(movieDetails: MovieDetails)
}