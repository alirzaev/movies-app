package io.github.alirzaev.movies.data.source.local

import io.github.alirzaev.movies.data.models.Actor
import io.github.alirzaev.movies.data.models.Genre
import io.github.alirzaev.movies.data.models.Movie
import io.github.alirzaev.movies.data.models.MovieDetails
import io.github.alirzaev.movies.data.source.local.room.AppDatabase
import io.github.alirzaev.movies.data.source.local.room.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomMoviesDataSource @Inject constructor(
    private val appDatabase: AppDatabase
) : LocalMoviesDataSource {

    override suspend fun getMovies(): List<Movie> = withContext(Dispatchers.IO) {
        appDatabase.movieDao().getAll().map { movieWithGenres ->
            val movie = movieWithGenres.movie

            Movie(
                movie.id,
                movie.title,
                movie.overview,
                movie.posterUrl,
                movie.backdropUrl,
                movie.rating,
                movie.voteCount,
                movie.adult,
                movieWithGenres.genres.map { Genre(it.id, it.name) }
            )
        }
    }

    override suspend fun saveMovie(movie: Movie) = withContext(Dispatchers.IO) {
        val movieDb = MovieDb(
            movie.id,
            movie.title,
            movie.overview,
            movie.poster,
            movie.backdrop,
            movie.rating,
            movie.voteCount,
            movie.adult
        )

        val genreDbs = movie.genres.map {
            GenreDb(it.id, it.name)
        }

        val crossRefs = movie.genres.map {
            MovieGenresCrossRef(movie.id, it.id)
        }

        appDatabase.runInTransaction {
            appDatabase.movieDao().insert(movieDb)
            appDatabase.genreDao().insertAll(*genreDbs.toTypedArray())
            appDatabase.movieGenresDao().insertAll(*crossRefs.toTypedArray())
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails = withContext(Dispatchers.IO) {
        appDatabase.movieDetailsDao().getMovieById(movieId).let { data ->
            val movie = data.movieDetails

            MovieDetails(
                movie.id,
                movie.title,
                movie.overview,
                movie.posterUrl,
                movie.backdropUrl,
                movie.rating,
                movie.voteCount,
                movie.adult,
                movie.runtime,
                data.genres.map { Genre(it.id, it.name) },
                data.actors.map { Actor(it.id, it.name, it.imageUrl) }
            )
        }
    }

    override suspend fun saveMovieDetails(movieDetails: MovieDetails) {
        val movieDetailsDb = MovieDetailsDb(
            movieDetails.id,
            movieDetails.title,
            movieDetails.overview,
            movieDetails.poster,
            movieDetails.backdrop,
            movieDetails.rating,
            movieDetails.voteCount,
            movieDetails.adult,
            movieDetails.runtime
        )

        val genreDbs = movieDetails.genres.map { genre ->
            GenreDb(genre.id, genre.name)
        }

        val genreCrossRefs = movieDetails.genres.map { genre ->
            MovieDetailsGenresCrossRef(
                movieDetails.id,
                genre.id
            )
        }

        val actorDbs = movieDetails.actors.map { actor ->
            ActorDb(actor.id, actor.name, actor.image)
        }

        val actorCrossRefs = movieDetails.actors.map { actor ->
            MovieDetailsActorsCrossRef(
                movieDetails.id,
                actor.id
            )
        }

        appDatabase.runInTransaction {
            appDatabase.movieDetailsDao().insert(movieDetailsDb)
            appDatabase.genreDao().insertAll(*genreDbs.toTypedArray())
            appDatabase.actorDao().insertAll(*actorDbs.toTypedArray())
            appDatabase.movieDetailsGenresDao().insertAll(*genreCrossRefs.toTypedArray())
            appDatabase.movieDetailsActorsDao().insertAll(*actorCrossRefs.toTypedArray())
        }
    }
}