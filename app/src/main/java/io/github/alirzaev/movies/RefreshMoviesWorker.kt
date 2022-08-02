package io.github.alirzaev.movies

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.github.alirzaev.movies.data.source.MoviesRepository

@HiltWorker
class RefreshMoviesWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val moviesRepository: MoviesRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.i(TAG, "Starting refresh movies job")

        return try {
            val movies = moviesRepository.getMovies().getOrThrow().results
            moviesRepository.saveMovies(movies).getOrThrow()
            Log.i(TAG, "Movies refreshed successfully")

            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Failed to refresh movies", ex)

            Result.failure()
        }
    }

    companion object {
        private val TAG = RefreshMoviesWorker::class.java.simpleName

        const val WORK_TAG = "REFRESH_MOVIES"
    }
}