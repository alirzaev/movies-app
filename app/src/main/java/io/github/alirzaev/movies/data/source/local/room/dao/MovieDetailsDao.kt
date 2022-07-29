package io.github.alirzaev.movies.data.source.local.room.dao

import androidx.room.*
import io.github.alirzaev.movies.data.source.local.room.MovieDetailsWithGenresAndActors
import io.github.alirzaev.movies.data.source.local.room.entities.MovieDetailsDb

@Dao
interface MovieDetailsDao {

    @Transaction
    @Query("select * from movie_details where movie_id = :movieId")
    fun getMovieById(movieId: Int): MovieDetailsWithGenresAndActors

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    fun insert(movie: MovieDetailsDb)
}