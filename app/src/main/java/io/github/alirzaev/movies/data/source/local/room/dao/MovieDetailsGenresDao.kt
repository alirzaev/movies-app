package io.github.alirzaev.movies.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.alirzaev.movies.data.source.local.room.entities.MovieDetailsGenresCrossRef

@Dao
interface MovieDetailsGenresDao {

    @Query("select * from movie_details_genres")
    fun getAll(): List<MovieDetailsGenresCrossRef>

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    fun insertAll(vararg movieDetailsGenres: MovieDetailsGenresCrossRef)
}