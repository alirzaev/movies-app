package io.github.alirzaev.movies.data.source.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import io.github.alirzaev.movies.data.source.local.room.Contract

@Entity(
    tableName = Contract.MovieGenres.TABLE_NAME,
    primaryKeys = [
        Contract.MovieGenres.COLUMN_NAME_MOVIE_ID,
        Contract.MovieGenres.COLUMN_NAME_GENRE_ID
    ]
)
data class MovieGenresCrossRef(
    @ColumnInfo(name = Contract.MovieGenres.COLUMN_NAME_MOVIE_ID)
    val movieId: Int,

    @ColumnInfo(name = Contract.MovieGenres.COLUMN_NAME_GENRE_ID)
    val genreId: Int
)
