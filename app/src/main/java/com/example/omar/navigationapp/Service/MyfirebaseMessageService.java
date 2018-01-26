package com.example.omar.navigationapp.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.omar.navigationapp.Activity.FirebaseActivity;
import com.example.omar.navigationapp.R;
import com.example.omar.navigationapp.Utils.NotificationUtils;
import com.example.omar.navigationapp.app.config;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SaTa on 12/29/2017.
 */

public class MyfirebaseMessageService extends FirebaseMessagingService {
    private final static String TAG = MyfirebaseInstanceIdService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "from" + remoteMessage);
  //        if (remoteMessage == null) ;


        if (remoteMessage.getNotification() != null) {
          Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());

        }

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Loading" + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);

            } catch (Exception e) {
                Log.e(TAG, "Exeption" + e.getMessage());
            }

        }

    }


    public void handleNotification(String message) {
        if (NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Intent PushNotification = new Intent(config.PUSH_NOTIFICATION);
            PushNotification.putExtra("message", message);

            LocalBroadcastManager.getInstance(this).sendBroadcast(PushNotification);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());


        } else {

        }

    }

    public void handleDataMessage(JSONObject json) {

        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());

            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), FirebaseActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void showNotificationMessageWithBigImage(Context applicationContext, String title, String message, String timestamp, Intent intent, String imageUrl) {

        notificationUtils = new NotificationUtils(applicationContext);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timestamp, intent, imageUrl);
    }

    private void showNotificationMessage(Context applicationContext, String title, String message, String timestamp, Intent intent) {
        notificationUtils = new NotificationUtils(applicationContext);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timestamp, intent);

    }


}


