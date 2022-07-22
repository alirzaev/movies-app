package io.github.alirzaev.movies.data.source

import android.content.Context
import io.github.alirzaev.movies.data.models.Actor
import io.github.alirzaev.movies.data.models.Genre
import io.github.alirzaev.movies.data.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import io.github.alirzaev.movies.data.dto.Actor as ActorDto
import io.github.alirzaev.movies.data.dto.Movie as MovieDto

object MoviesRepository {

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun getMovies(context: Context): List<Movie> = withContext(Dispatchers.IO) {
        val rawMovies = context.assets.open("data.json").bufferedReader().use {
            it.readText()
        }
        val genres = getGenres(context).associateBy { it.id }
        val actors = getActors(context).associateBy { it.id }

        json.decodeFromString<List<MovieDto>>(rawMovies).map { dto ->
            val movieGenres = dto.genreIds.map {
                genres[it]!!
            }
            val movieActors = dto.actorIds.map {
                actors[it]!!
            }

            Movie(
                dto.id,
                dto.title,
                dto.overview,
                dto.poster,
                dto.backdrop,
                dto.rating,
                dto.voteCount,
                dto.adult,
                dto.runtime,
                movieGenres,
                movieActors
            )
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun getGenres(context: Context): List<Genre> = withContext(Dispatchers.IO) {
        val rawActors = context.assets.open("genres.json").bufferedReader().use {
            it.readText()
        }

        json.decodeFromString(rawActors)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun getActors(context: Context): List<Actor> = withContext(Dispatchers.IO) {
        val rawActors = context.assets.open("people.json").bufferedReader().use {
            it.readText()
        }

        json.decodeFromString<List<ActorDto>>(rawActors).map { dto ->
            Actor(dto.id, dto.name, dto.profile)
        }
    }
}