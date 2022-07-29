package io.github.alirzaev.movies.data.source.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.alirzaev.movies.data.source.local.room.Contract

@Entity(
    tableName = Contract.MovieDetails.TABLE_NAME
)
data class MovieDetailsDb(
    @PrimaryKey
    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_ID)
    val id: Int,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_TITLE)
    val title: String,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_OVERVIEW)
    val overview: String,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_POSTER_URL)
    val posterUrl: String?,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_BACKDROP_URL)
    val backdropUrl: String?,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_RATING)
    val rating: Float,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_VOTE_COUNT)
    val voteCount: Int,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_ADULT)
    val adult: Boolean,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_RUNTIME)
    val runtime: Int
)
