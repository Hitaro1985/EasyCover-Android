package com.insurance.easycover.shared;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.insurance.easycover.R;
import com.insurance.easycover.shared.ui.activities.HomeActivity;
import com.insurance.easycover.shared.ui.activities.SplashActivity;

import naveed.khakhrani.miscellaneous.base.BaseActivity;

/**
 * Created by PDC100 on 3/22/2018.
 */

public class NotificationFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log.i(String.valueOf("Notification Message Content"), remoteMessage.getData().get("message"));
        Log.i(String.valueOf("Notification Message Content"), remoteMessage.getNotification().getBody());
        //if (remoteMessage.getNotification().getBody().equals("New Notification")) {
        Intent intent = new Intent("update-message");
        intent.putExtra("message", remoteMessage.getNotification().getBody());
        showNotification(remoteMessage.getNotification().getBody());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        //}
    }

    private void showNotification(String message) {
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "4655")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentTitle("FCM Test")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (manager != null) {
            manager.notify(0,builder.build());
        }
    }
}
