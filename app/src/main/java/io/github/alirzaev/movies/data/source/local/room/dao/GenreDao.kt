package io.github.alirzaev.movies.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.alirzaev.movies.data.source.local.room.entities.GenreDb

@Dao
interface GenreDao {

    @Query("select * from genres")
    fun getAll(): List<GenreDb>

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    fun insertAll(vararg genres: GenreDb)
}