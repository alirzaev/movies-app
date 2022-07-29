package io.github.alirzaev.movies.data.source.remote

import io.github.alirzaev.movies.data.source.remote.response.ConfigurationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageUrlBuilder @Inject constructor(private val api: TheMovieDbApi) {

    private var config: ConfigurationResponse? = null

    private val baseUrl: String?
        get() = config?.images?.secureBaseUrl

    private val posterSize: String
        get() = config?.images?.posterSizes?.find { it == POSTER_SIZE } ?: DEFAULT_SIZE

    private val backdropSize: String
        get() = config?.images?.posterSizes?.find { it == BACKDROP_SIZE } ?: DEFAULT_SIZE

    private val profileSize: String
        get() = config?.images?.profileSizes?.find { it == PROFILE_SIZE } ?: DEFAULT_SIZE

    private suspend fun loadConfig() = withContext(Dispatchers.IO) {
        if (config != null) {
            return@withContext
        }

        config = api.getConfiguration()
    }

    private fun buildUrl(url: String?, path: String?, size: String): String? {
        if (url == null || path == null) {
            return null
        }

        return "$url$size$path"
    }

    suspend fun getPosterUrl(path: String?): String? {
        loadConfig()

        return buildUrl(baseUrl, path, posterSize)
    }

    suspend fun getBackdropUrl(path: String?): String? {
        loadConfig()

        return buildUrl(baseUrl, path, backdropSize)
    }

    suspend fun getProfileUrl(path: String?): String? {
        loadConfig()

        return buildUrl(baseUrl, path, profileSize)
    }

    companion object {
        private const val POSTER_SIZE = "w500"

        private const val BACKDROP_SIZE = "w1280"

        private const val PROFILE_SIZE = "w185"

        private const val DEFAULT_SIZE = "original"
    }
}