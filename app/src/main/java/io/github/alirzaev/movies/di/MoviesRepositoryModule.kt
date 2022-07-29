package io.github.alirzaev.movies.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.alirzaev.movies.data.source.MoviesRepository
import io.github.alirzaev.movies.data.source.MoviesRepositoryImpl
import io.github.alirzaev.movies.data.source.remote.ImageUrlBuilder
import io.github.alirzaev.movies.data.source.remote.TheMovieDbApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesRepositoryModule {

    @Binds
    abstract fun provideMoviesRepository(
        repositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    companion object {
        @Provides
        @Singleton
        fun provideImageUrlBuilder(
            api: TheMovieDbApi
        ) = ImageUrlBuilder(api)
    }
}