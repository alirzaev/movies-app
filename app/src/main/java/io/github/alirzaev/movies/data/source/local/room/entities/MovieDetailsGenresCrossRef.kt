package io.github.alirzaev.movies.data.source.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import io.github.alirzaev.movies.data.source.local.room.Contract

@Entity(
    tableName = Contract.MovieDetailsGenres.TABLE_NAME,
    primaryKeys = [
        Contract.MovieDetailsGenres.COLUMN_NAME_MOVIE_ID,
        Contract.MovieDetailsGenres.COLUMN_NAME_GENRE_ID
    ]
)
data class MovieDetailsGenresCrossRef(
    @ColumnInfo(name = Contract.MovieDetailsGenres.COLUMN_NAME_MOVIE_ID)
    val movieId: Int,

    @ColumnInfo(name = Contract.MovieDetailsGenres.COLUMN_NAME_GENRE_ID)
    val genreId: Int
)