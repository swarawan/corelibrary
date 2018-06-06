package com.swarawan.corelibrary.firebase.messaging

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.swarawan.corelibrary.R

/**
 * Created by Rio Swarawn on 6/6/18.
 */
class NotificationHelper(val context: Context) {

    private var notificationBuilder: NotificationCompat.Builder? = null
    private var notificationManager: NotificationManagerCompat? = null

    init {
        notificationManager = NotificationManagerCompat.from(context)
        notificationBuilder = NotificationCompat.Builder(context).apply {
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            setVibrate(longArrayOf(100, 300, 500))
        }
    }

    fun showNotification(notificationId: Int, title: String, message: String,
                         requestCode: Int, timestamp: Long, intentToLaunch: Intent) {
        val builder: NotificationCompat.Builder? = createNotification(title, message, requestCode, timestamp, intentToLaunch)
        notificationManager?.let {
            it.notify(notificationId, builder?.build())
        }
    }

    fun createNotification(title: String, message: String, requestCode: Int, timestamp: Long, intentToLaunch: Intent,
                           actions: List<NotificationCompat.Action>? = null): NotificationCompat.Builder? {

        actions?.let {
            addNotificationAction(actions)
        }

        val pendingIntent = PendingIntent.getActivity(context, requestCode, intentToLaunch, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder?.let {
            return it.setSmallIcon(R.drawable.notification_res_id).apply {
                setContentTitle(title)
                setTicker(message)
                setContentText(message)
                priority = NotificationCompat.PRIORITY_MAX
                setWhen(timestamp)
                setShowWhen((timestamp > 0))
                setAutoCancel(true)
                setContentIntent(pendingIntent)
            }
        }
        return null
    }

    fun addNotificationAction(actions: List<NotificationCompat.Action>? = null) {
        when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP -> {
                actions?.let {
                    if (actions.isNotEmpty()) {
                        actions.forEach {
                            notificationBuilder?.addAction(it)
                        }
                    }
                }
            }
            else -> return
        }
    }
}