package io.github.alirzaev.movies.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.alirzaev.movies.data.source.local.room.entities.MovieGenresCrossRef

@Dao
interface MovieGenresDao {

    @Query("select * from movie_genres")
    fun getAll(): List<MovieGenresCrossRef>

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    fun insertAll(vararg movieGenre: MovieGenresCrossRef)
}