package io.github.alirzaev.movies.data.source.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.alirzaev.movies.data.source.local.room.Contract

@Entity(
    tableName = Contract.Genres.TABLE_NAME
)
data class GenreDb(
    @PrimaryKey
    @ColumnInfo(name = Contract.Genres.COLUMN_NAME_ID)
    val id: Int,

    @ColumnInfo(name = Contract.Genres.COLUMN_NAME_NAME)
    val name: String
)
