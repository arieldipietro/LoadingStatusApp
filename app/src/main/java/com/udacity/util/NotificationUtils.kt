package com.udacity.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.udacity.DetailActivity
import com.udacity.MainActivity
import com.udacity.R

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(nameAndStatus: NameAndStatus, messageBody: String, applicationContext: Context){

    //Intent to open DetailActivity when the user clicks the notification
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
        .putExtra("nameAndStatus", nameAndStatus)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext, applicationContext.getString(R.string.download_notification_channel_id))
            .setSmallIcon(R.drawable.baseline_cloud_download_24)
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setOnlyAlertOnce(true)
            //cancel when user clicks
        .setAutoCancel(true)
            //adding the button
        .addAction(
            R.drawable.baseline_cloud_download_24,
            applicationContext.getString(R.string.notification_button),
            contentPendingIntent)

        notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications(){
    cancelAll()
}