package io.github.alirzaev.movies.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.alirzaev.movies.data.source.local.room.entities.MovieDetailsActorsCrossRef

@Dao
interface MovieDetailsActorsDao {

    @Query("select * from movie_details_actors")
    fun getAll(): List<MovieDetailsActorsCrossRef>

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    fun insertAll(vararg movieDetailsActors: MovieDetailsActorsCrossRef)
}