package com.swarawan.corelibrary.firebase.messaging;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.swarawan.corelibrary.R;

import java.util.List;

/**
 * Created by rioswarawan on 3/8/18.
 */

public class NotificationHelper {

    private Context context;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManagerCompat notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;

        notificationManager = NotificationManagerCompat.from(context);
        notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        notificationBuilder.setVibrate(new long[]{100, 300, 500});
    }

    public Integer uniqueNotificationid() {
        return ((int) System.currentTimeMillis());
    }

    public void showNotification(Integer notificationId, String title, String message, Integer requestCode, Long timestamp, Intent intentToLaunch) {
        NotificationCompat.Builder builder = createNotification(title, message, requestCode, timestamp, intentToLaunch);
        notificationManager.notify(notificationId, builder.build());
    }

    private NotificationCompat.Builder createNotification(String title, String message, Integer requestCode, Long timestamp, Intent intentToLaunch) {
        return createNotification(title, message, requestCode, timestamp, intentToLaunch, null);
    }

    private NotificationCompat.Builder createNotification(String title, String message, Integer requestCode, Long timestamp,
                                                          Intent intentToLaunch, List<NotificationCompat.Action> actions) {
        if (null != actions) {
            addNotificationActions(actions);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intentToLaunch, PendingIntent.FLAG_UPDATE_CURRENT);
        return notificationBuilder.setSmallIcon(R.drawable.notification_res_id)
                .setContentTitle(title)
                .setTicker(message)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setWhen(timestamp)
                .setShowWhen((timestamp > 0))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }

    private void addNotificationActions(List<NotificationCompat.Action> actions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;
        if (actions.size() > 0) {
            for (NotificationCompat.Action action : actions) {
                notificationBuilder.addAction(action);
            }
        }
    }
}
