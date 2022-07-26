package io.github.alirzaev.movies.data.source

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.alirzaev.movies.data.models.Actor
import io.github.alirzaev.movies.data.source.remote.response.ConfigurationResponse
import io.github.alirzaev.movies.data.source.remote.response.PaginatedResponse
import io.github.alirzaev.movies.data.models.Genre
import io.github.alirzaev.movies.data.models.Movie
import io.github.alirzaev.movies.data.models.MovieDetails
import io.github.alirzaev.movies.data.source.remote.ApiKeyInterceptor
import io.github.alirzaev.movies.data.source.remote.TheMovieDbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

object MoviesRepository {

    private val json = Json { ignoreUnknownKeys = true }

    private const val posterSize = "w500"

    private const val backdropSize = "w1280"

    private const val profileSize = "w185"

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(ApiKeyInterceptor())
                .build()
        )
        .build()

    private val api: TheMovieDbApi = retrofit.create()

    private var configuration: ConfigurationResponse? = null

    private var genres: Map<Int, Genre>? = null

    private suspend fun getConfig(): ConfigurationResponse = withContext(Dispatchers.IO) {
        if (configuration == null) {
            configuration = api.getConfiguration()
        }

        configuration!!
    }

    suspend fun getMoviesPaginated(page: Int = 1): PaginatedResponse<Movie> =
        withContext(Dispatchers.IO) {
            getConfig()

            val genres = getGenres()

            val paginatedResponse = api.getUpcomingMovies(page)
            val movies = paginatedResponse.results.map { dto ->
                Movie(
                    dto.id,
                    dto.title,
                    dto.overview,
                    "${configuration!!.images.secureBaseUrl}${posterSize}${dto.poster}",
                    "${configuration!!.images.secureBaseUrl}${backdropSize}${dto.backdrop}",
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

    suspend fun getMovie(id: Int): MovieDetails = withContext(Dispatchers.IO) {
        getConfig()

        val movieDetails = api.getMovieById(id)
        val credits = api.getMovieCredits(id)

        MovieDetails(
            movieDetails.id,
            movieDetails.title,
            movieDetails.overview,
            "${configuration!!.images.secureBaseUrl}${posterSize}${movieDetails.poster}",
            "${configuration!!.images.secureBaseUrl}${backdropSize}${movieDetails.backdrop}",
            movieDetails.rating,
            movieDetails.voteCount,
            movieDetails.adult,
            movieDetails.runtime,
            movieDetails.genres,
            credits.cast.map {
                Actor(
                    it.id,
                    it.name,
                    "${configuration!!.images.secureBaseUrl}${profileSize}${it.profilePath}"
                )
            }
        )
    }

    private suspend fun getGenres(): Map<Int, Genre> = withContext(Dispatchers.IO) {
        if (genres == null) {
            genres = api.getGenres().genres.associateBy { it.id }
        }

        genres!!
    }
}