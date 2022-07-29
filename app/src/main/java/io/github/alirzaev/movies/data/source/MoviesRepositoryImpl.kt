package io.github.alirzaev.movies.data.source

import io.github.alirzaev.movies.data.models.Actor
import io.github.alirzaev.movies.data.models.Movie
import io.github.alirzaev.movies.data.models.MovieDetails
import io.github.alirzaev.movies.data.source.local.LocalMoviesDataSource
import io.github.alirzaev.movies.data.source.remote.ImageUrlBuilder
import io.github.alirzaev.movies.data.source.remote.TheMovieDbApi
import io.github.alirzaev.movies.data.source.remote.response.PaginatedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: TheMovieDbApi,
    private val localMoviesDataSource: LocalMoviesDataSource,
    private val imageUrlBuilder: ImageUrlBuilder
) : MoviesRepository {

    override suspend fun getMovies(page: Int): Result<PaginatedResponse<Movie>> =
        withContext(Dispatchers.IO) {
            runCatching {
                val genres = api.getGenres().genres.associateBy { it.id }

                val paginatedResponse = api.getUpcomingMovies(page)
                val movies = paginatedResponse.results.map { dto ->
                    Movie(
                        dto.id,
                        dto.title,
                        dto.overview,
                        imageUrlBuilder.getPosterUrl(dto.poster),
                        imageUrlBuilder.getBackdropUrl(dto.backdrop),
                        dto.rating,
                        dto.voteCount,
                        dto.adult,
                        dto.genreIds.mapNotNull { genreId -> genres[genreId] }
                    )
                }

                PaginatedResponse(
                    paginatedResponse.page,
                    movies,
                    paginatedResponse.totalPages,
                    paginatedResponse.totalResults
                )
            }
        }

    override suspend fun getCachedMovies(): Result<List<Movie>> = withContext(Dispatchers.IO) {
        runCatching { localMoviesDataSource.getMovies() }
    }

    override suspend fun getMovie(id: Int): Result<MovieDetails> = withContext(Dispatchers.IO) {
        runCatching {
            val movie = api.getMovieById(id)
            val credits = api.getMovieCredits(id)

            MovieDetails(
                movie.id,
                movie.title,
                movie.overview,
                imageUrlBuilder.getPosterUrl(movie.poster),
                imageUrlBuilder.getBackdropUrl(movie.backdrop),
                movie.rating,
                movie.voteCount,
                movie.adult,
                movie.runtime,
                movie.genres,
                credits.cast.map { actor ->
                    Actor(
                        actor.id,
                        actor.name,
                        imageUrlBuilder.getProfileUrl(actor.profilePath)
                    )
                }
            )
        }
    }

    override suspend fun saveMovies(movies: List<Movie>): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                movies.forEach {
                    localMoviesDataSource.saveMovie(it)
                }
            }
        }

    override suspend fun getCachedMovie(movieId: Int): Result<MovieDetails> =
        withContext(Dispatchers.IO) {
            runCatching {
                localMoviesDataSource.getMovieDetails(movieId)
            }
        }

    override suspend fun saveMovieDetails(movieDetails: MovieDetails): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                localMoviesDataSource.saveMovieDetails(movieDetails)
            }
        }
}