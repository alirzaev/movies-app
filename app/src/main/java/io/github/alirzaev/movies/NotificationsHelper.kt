package io.github.alirzaev.movies

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import io.github.alirzaev.movies.data.models.Movie

private const val CHANNEL_ID = "ChannelId"

private const val CHANNEL_NAME = "Default channel"

private const val CHANNEL_DESCRIPTION = "Default channel"

object NotificationHelper {

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationForOreo(
        context: Context,
        movie: Movie,
        pendingIntent: PendingIntent? = null
    ): Notification {
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.createNotificationChannel(createChannel())

        return createNotification(context, movie, pendingIntent)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun createNotificationForMovie(
        context: Context,
        movie: Movie
    ) {
        val notifyIntent =
            Intent(Intent.ACTION_VIEW, "io.github.alirzaev.movies://movie/${movie.id}".toUri())

        val notifyPendingIntent = PendingIntent.getActivity(
            context,
            0,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val createNotification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationForOreo(
                context,
                movie,
                notifyPendingIntent
            ) else createNotification(
                context,
                movie,
                notifyPendingIntent
            )
        NotificationManagerCompat.from(context)
            .notify(NOTIFICATION_ID, createNotification)
    }

    private fun createNotification(
        context: Context,
        movie: Movie,
        pendingIntent: PendingIntent? = null
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.title_movie_notification))
            .setContentText(context.getString(R.string.content_movie_notification, movie.title))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            .also { notificationBuilder ->
                if (pendingIntent != null) {
                    notificationBuilder.setContentIntent(pendingIntent)
                }
            }
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(): NotificationChannelCompat {
        return NotificationChannelCompat.Builder(
            CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_DEFAULT
        )
            .setName(CHANNEL_NAME)
            .setDescription(CHANNEL_DESCRIPTION)
            .setVibrationEnabled(true)
            .build()
    }

    private const val NOTIFICATION_ID = 1
}