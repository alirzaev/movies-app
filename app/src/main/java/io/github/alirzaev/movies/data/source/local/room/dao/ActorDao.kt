package io.github.alirzaev.movies.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.alirzaev.movies.data.source.local.room.entities.ActorDb

@Dao
interface ActorDao {

    @Query("select * from actors")
    fun getAll(): List<ActorDb>

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    fun insertAll(vararg actors: ActorDb)
}