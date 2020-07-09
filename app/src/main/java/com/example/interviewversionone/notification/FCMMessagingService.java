package com.example.interviewversionone.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.interviewversionone.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.example.interviewversionone.notification.App.FCM_CHANNEL_ID;


public class FCMMessagingService extends FirebaseMessagingService {
    public static final String TAG ="MyTag";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,"OnMessageRecived");
        Log.d(TAG,"OnMessageRecived: Message Received from"+remoteMessage.getFrom());
        if(remoteMessage.getNotification()!=null)
        {
            String title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();


            Notification notification = new NotificationCompat.Builder(this, FCM_CHANNEL_ID)
                    .setSmallIcon(R.drawable.common_full_open_on_phone)
                    .setContentTitle(title)
                    .setContentText(body)
                    .build();

            NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1002,notification);

        }

        if(remoteMessage.getData().size() > 0)
        {
            Log.d(TAG,"OnMessageRecived: Data"+remoteMessage.getData().toString());

        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d(TAG,"OnMessageRecived");
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG,"NewToken: Recived");
    }
}
