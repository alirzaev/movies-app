package io.github.alirzaev.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.alirzaev.movies.data.source.local.LocalMoviesDataSource
import io.github.alirzaev.movies.data.source.local.RoomMoviesDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {

    @Binds
    abstract fun provideLocalDataSource(
        localDataSource: RoomMoviesDataSource
    ): LocalMoviesDataSource
}