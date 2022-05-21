package com.example.thebeast;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "Messaging";
    NotificationManager notificationManager;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference userRef = firebaseFirestore.collection("User");
    private FirebaseAuth mAuth;
    Notification notification;
    String noteTitle, noteMessage, noteType, imageURL;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

       /* if(remoteMessage != null){

            String title  = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            notifyUser(title, body);
        }  */


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            notifyUser(title,body);
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void notifyUser(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId =  getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        @SuppressLint("ResourceAsColor")
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.oberkoerper)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setColor(Color.CYAN)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // For android Oreo and above  notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Fcm notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 , notificationBuilder.build());
    }




    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        super.onNewToken(token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

        Map<String,Object> updates = new HashMap<>();
        updates.put("token",token);

        mAuth = FirebaseAuth.getInstance();
        userRef.document(mAuth.getCurrentUser().getUid()).update(updates)
                .addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            if(CurrentUser.getCurrentUser() != null){
                                CurrentUser.getCurrentUser().setToken(token);
                            }
                            Log.w(TAG, "Token konnt nicht dem CurrentUser gesetzt werden, da Current User null ist.");
                        }else{
                            Log.w(TAG, "Token konnte nicht geupdatet werden" + task.getException());
                        }
                    }
                });
    }
}
