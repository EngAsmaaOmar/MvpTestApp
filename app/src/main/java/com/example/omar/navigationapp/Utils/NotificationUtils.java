package com.example.omar.navigationapp.Utils;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Config;
import android.util.Patterns;

import com.example.omar.navigationapp.R;
import com.example.omar.navigationapp.app.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.regex.Pattern;

/**
 * Created by SaTa on 12/29/2017.
 */

public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();
    private Context mContext;

    public NotificationUtils(Context mcontext) {
        this.mContext = mcontext;
    }

    public void showNotificationMessage(String title, String Message, String timestamp, Intent intent) {
        showNotificationMessage(title, Message, timestamp, intent, null);
    }

    public void showNotificationMessage(String title, String Message, String timestamp, Intent intent, String imageurl) {

        if (TextUtils.isEmpty(Message))
            return;


        final int icon = R.mipmap.ic_launcher;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);


        if (imageurl != null && imageurl.length() > 4 && Patterns.WEB_URL.matcher(imageurl).matches()) {

            Bitmap bitmap = getBitmapFromURL(imageurl);

            if (bitmap != null) {
                showBigNotification(bitmap, builder, icon, title, Message, timestamp, pendingIntent);
            } else {
                showSmallNotification(builder, icon, title, Message, timestamp, pendingIntent);

            }
        } else {
            showSmallNotification(builder, icon, title, Message, timestamp, pendingIntent);

        }
    }



    public void showBigNotification(Bitmap bitmap, NotificationCompat.Builder builder, int icon, String title, String Message, String timestamp, PendingIntent pendingIntent) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(Message).toString());
        bigPictureStyle.bigPicture(bitmap);
        Notification notification;
        notification = builder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setStyle(bigPictureStyle)
                .setWhen(getTimeMilliSec(timestamp))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(Message)
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(config.NOTIFICATION_ID_BIG_IMAGE, notification);


    }


    public void showSmallNotification(NotificationCompat.Builder builder, int icon, String title, String Message, String timestamp, PendingIntent pendingIntent) {

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine(Message);

        Notification notification;
        notification = builder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setWhen(getTimeMilliSec(timestamp))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(Message)
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(config.NOTIFICATION_ID, notification);


    }


    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    private Bitmap getBitmapFromURL(String imageurl) {

        try {
            URL url = new URL(imageurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }





    private long getTimeMilliSec(String timestamp) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss ");
        try {
            Date date = dateFormat.parse(timestamp);
            return date.getTime();
        } catch (ParseException e) {
            e.getStackTrace();
        }

        return 0;
    }
}













