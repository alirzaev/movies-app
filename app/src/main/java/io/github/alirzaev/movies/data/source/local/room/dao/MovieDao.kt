package io.github.alirzaev.movies.data.source.local.room.dao

import androidx.room.*
import io.github.alirzaev.movies.data.source.local.room.entities.MovieDb
import io.github.alirzaev.movies.data.source.local.room.MovieWithGenres

@Dao
interface MovieDao {

    @Transaction
    @Query("select * from movies")
    fun getAll(): List<MovieWithGenres>

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    fun insert(movie: MovieDb)
}