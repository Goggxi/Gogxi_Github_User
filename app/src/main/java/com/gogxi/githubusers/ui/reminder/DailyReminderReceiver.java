package com.gogxi.githubusers.ui.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.gogxi.githubusers.R;
import com.gogxi.githubusers.ui.home.HomeActivity;

import java.util.Calendar;

public class DailyReminderReceiver extends BroadcastReceiver {
    private final int DAILY_ID = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        DailyReminder(context);
    }

    private void DailyReminder(Context context) {
        int req = 19;
        String id = "chanelFirst";
        String name = "DailyReminder";
        String title = context.getString(R.string.reminder_daily);
        CharSequence message = context.getString(R.string.reminder_daily_message);

        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent chPendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(req, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, id)
                .setContentIntent(chPendingIntent)
                .setSmallIcon(R.drawable.ic_notifications)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(id);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        if (notificationManager != null) {
            notificationManager.notify(DAILY_ID, builder.build());
        }
    }

    public void dailyReminderOn(Context context) {
        dailyReminderOff(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (alarmManager != null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(context));
        }
    }

    public void dailyReminderOff(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, DailyReminderReceiver.class);
        return PendingIntent.getBroadcast(context, DAILY_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
