package com.example.techworld;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MassageService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        receivedMassage(remoteMessage.getNotification().getBody());
    }



    public void receivedMassage(String massage){
        PendingIntent pi = PendingIntent.getActivity(this,0, new Intent(this, MainViewActivity.class),0);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Tech World Tune Post")
                .setContentText(massage)
                .setContentIntent(pi)
                .setTicker("You got a new massage for Ali ")
                .setAutoCancel(true)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,notification);


    }
}
