package io.github.alirzaev.movies.data.source.local.room

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import io.github.alirzaev.movies.data.source.local.room.entities.*

data class MovieDetailsWithGenresAndActors(
    @Embedded
    val movieDetails: MovieDetailsDb,

    @Relation(
        parentColumn = Contract.MovieDetails.COLUMN_NAME_ID,
        entityColumn = Contract.Genres.COLUMN_NAME_ID,
        associateBy = Junction(MovieDetailsGenresCrossRef::class)
    )
    val genres: List<GenreDb>,

    @Relation(
        parentColumn = Contract.MovieDetails.COLUMN_NAME_ID,
        entityColumn = Contract.Actors.COLUMN_NAME_ID,
        associateBy = Junction(MovieDetailsActorsCrossRef::class)
    )
    val actors: List<ActorDb>
)
