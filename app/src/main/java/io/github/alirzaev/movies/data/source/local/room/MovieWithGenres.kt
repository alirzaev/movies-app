package io.github.alirzaev.movies.data.source.local.room

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import io.github.alirzaev.movies.data.source.local.room.entities.GenreDb
import io.github.alirzaev.movies.data.source.local.room.entities.MovieDb
import io.github.alirzaev.movies.data.source.local.room.entities.MovieGenresCrossRef

data class MovieWithGenres(
    @Embedded
    val movie: MovieDb,

    @Relation(
        parentColumn = Contract.Movies.COLUMN_NAME_ID,
        entityColumn = Contract.Genres.COLUMN_NAME_ID,
        associateBy = Junction(MovieGenresCrossRef::class)
    )
    val genres: List<GenreDb>
)
