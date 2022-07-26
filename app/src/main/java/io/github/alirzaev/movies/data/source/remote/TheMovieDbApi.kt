package io.github.alirzaev.movies.data.source.remote

import io.github.alirzaev.movies.data.source.remote.dto.Movie
import io.github.alirzaev.movies.data.source.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1
    ): PaginatedResponse<Movie>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") id: Int
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") id: Int
    ): MovieCreditsResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse
}