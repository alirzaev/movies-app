package io.github.alirzaev.movies.data.source.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import io.github.alirzaev.movies.data.source.local.room.Contract

@Entity(
    tableName = Contract.MovieDetailsActors.TABLE_NAME,
    primaryKeys = [
        Contract.MovieDetailsActors.COLUMN_NAME_MOVIE_ID,
        Contract.MovieDetailsActors.COLUMN_NAME_ACTOR_ID
    ]
)
data class MovieDetailsActorsCrossRef(
    @ColumnInfo(name = Contract.MovieDetailsActors.COLUMN_NAME_MOVIE_ID)
    val movieId: Int,

    @ColumnInfo(name = Contract.MovieDetailsActors.COLUMN_NAME_ACTOR_ID)
    val actorId: Int
)
