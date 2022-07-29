package io.github.alirzaev.movies.data.source.local.room

object Contract {
    const val DATABASE_NAME = "movies.db"

    object Movies {
        const val TABLE_NAME = "movies"

        const val COLUMN_NAME_ID = "movie_id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_POSTER_URL = "poster_url"
        const val COLUMN_NAME_BACKDROP_URL = "backdrop_url"
        const val COLUMN_NAME_RATING = "rating"
        const val COLUMN_NAME_VOTE_COUNT = "vote_count"
        const val COLUMN_NAME_ADULT = "adult"
    }

    object MovieDetails {
        const val TABLE_NAME = "movie_details"

        const val COLUMN_NAME_ID = "movie_id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_POSTER_URL = "poster_url"
        const val COLUMN_NAME_BACKDROP_URL = "backdrop_url"
        const val COLUMN_NAME_RATING = "rating"
        const val COLUMN_NAME_VOTE_COUNT = "vote_count"
        const val COLUMN_NAME_ADULT = "adult"
        const val COLUMN_NAME_RUNTIME = "runtime"
    }

    object Actors {
        const val TABLE_NAME = "actors"

        const val COLUMN_NAME_ID = "actor_id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_IMAGE_URL = "image_url"
    }

    object Genres {
        const val TABLE_NAME = "genres"

        const val COLUMN_NAME_ID = "genre_id"
        const val COLUMN_NAME_NAME = "name"
    }

    object MovieGenres {
        const val TABLE_NAME = "movie_genres"

        const val COLUMN_NAME_MOVIE_ID = "movie_id"
        const val COLUMN_NAME_GENRE_ID = "genre_id"
    }

    object MovieDetailsGenres {
        const val TABLE_NAME = "movie_details_genres"

        const val COLUMN_NAME_MOVIE_ID = "movie_id"
        const val COLUMN_NAME_GENRE_ID = "genre_id"
    }

    object MovieDetailsActors {
        const val TABLE_NAME = "movie_details_actors"

        const val COLUMN_NAME_MOVIE_ID = "movie_id"
        const val COLUMN_NAME_ACTOR_ID = "actor_id"
    }
}