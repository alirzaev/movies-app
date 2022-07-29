package io.github.alirzaev.movies.data.source.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.alirzaev.movies.data.source.local.room.Contract

@Entity(
    tableName = Contract.Actors.TABLE_NAME
)
data class ActorDb(
    @PrimaryKey
    @ColumnInfo(name = Contract.Actors.COLUMN_NAME_ID)
    val id: Int,

    @ColumnInfo(name = Contract.Actors.COLUMN_NAME_NAME)
    val name: String,

    @ColumnInfo(name = Contract.Actors.COLUMN_NAME_IMAGE_URL)
    val imageUrl: String?
)
