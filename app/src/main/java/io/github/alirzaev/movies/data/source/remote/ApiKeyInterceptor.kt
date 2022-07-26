package io.github.alirzaev.movies.data.source.remote

import io.github.alirzaev.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(url)
                .build()
        )
    }
}