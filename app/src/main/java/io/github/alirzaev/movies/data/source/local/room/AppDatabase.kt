package io.github.alirzaev.movies.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.alirzaev.movies.data.source.local.room.dao.*
import io.github.alirzaev.movies.data.source.local.room.entities.*

@Database(
    entities = [
        ActorDb::class,
        GenreDb::class,
        MovieDb::class,
        MovieDetailsDb::class,
        MovieGenresCrossRef::class,
        MovieDetailsGenresCrossRef::class,
        MovieDetailsActorsCrossRef::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun actorDao(): ActorDao

    abstract fun genreDao(): GenreDao

    abstract fun movieDao(): MovieDao

    abstract fun movieDetailsDao(): MovieDetailsDao

    abstract fun movieGenresDao(): MovieGenresDao

    abstract fun movieDetailsGenresDao(): MovieDetailsGenresDao

    abstract fun movieDetailsActorsDao(): MovieDetailsActorsDao
}