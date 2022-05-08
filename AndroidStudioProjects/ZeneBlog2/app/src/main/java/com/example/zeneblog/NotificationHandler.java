package com.example.zeneblog;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private NotificationManager notificationManager;
    private Context context;
    private static final String channelID = "regist";


    public NotificationHandler(Context context) {
        this.notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.context = context;
        createChannel();
    }
    public void createChannel(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            return;
        }
        NotificationChannel notificationChannel = new NotificationChannel(channelID, "regist",NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setDescription("notification from MusicBlog");
        this.notificationManager.createNotificationChannel(notificationChannel);
    }
    public void send(String message){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,channelID)
                .setContentTitle("Welcome to the Music Blog")
                .setContentText(message+" has successfully registered!")
                .setSmallIcon(R.drawable.notif_note_icon);
        this.notificationManager.notify(0, mBuilder.build());
    }
}
