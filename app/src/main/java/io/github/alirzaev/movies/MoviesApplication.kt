package io.github.alirzaev.movies

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MoviesApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()
        val work = PeriodicWorkRequestBuilder<RefreshMoviesWorker>(8, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        WorkManager
            .getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                RefreshMoviesWorker.WORK_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                work
            )
        Log.i(TAG, "Refresh movies work enqueued")
    }

    companion object {
        private val TAG = MoviesApplication::class.java.simpleName
    }
}