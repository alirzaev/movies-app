package io.github.alirzaev.movies.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.alirzaev.movies.data.source.remote.ApiKeyInterceptor
import io.github.alirzaev.movies.data.source.remote.TheMovieDbApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val json = Json { ignoreUnknownKeys = true }

    private val contentType = "application/json".toMediaType()

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ApiKeyLoggingOkHttpClient

    @Provides
    @ApiKeyLoggingOkHttpClient
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(ApiKeyInterceptor())
        .build()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideTheMovieDbApi(
        @ApiKeyLoggingOkHttpClient client: OkHttpClient
    ): TheMovieDbApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(client)
        .build()
        .create()
}