package com.zairussalamdev.gitbox.services.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.zairussalamdev.gitbox.R
import com.zairussalamdev.gitbox.ui.main.MainActivity

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val ALARM_ID = 1001
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val TIME_FORMAT = "HH:mm"

        private const val NOTIFICATION_ID = 1002
        private const val NOTIFICATION_CHANNEL_ID = "channel_1"
        private const val NOTIFICATION_CHANNEL_NAME = "gitbox_notification_ch1"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        showReminderNotification(context as Context)
    }

    fun setNotificationAlarm(context: Context, time: String) {

    }

    private fun showReminderNotification(context: Context) {
        with(context) {
            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.github_logo)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.github_logo))
                .setContentTitle(resources.getString(R.string.app_name))
                .setContentText(resources.getString(R.string.notification_content))
                .setAutoCancel(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
                builder.setChannelId(NOTIFICATION_CHANNEL_ID)
                notificationManager.createNotificationChannel(channel)
            }
            val notification = builder.build()
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}