package edu.fpt.shose_app.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

import edu.fpt.shose_app.Activity.HomeActivity;
import edu.fpt.shose_app.Activity.MyApplication;
import edu.fpt.shose_app.R;

public class FirebaseMessReceiver extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        if(message.getNotification() !=null){
//            SendNotification(message.getNotification().getTitle(),message.getNotification().getBody());
            showNotification(message.getNotification().getTitle(),message.getNotification().getBody());
        }
        super.onMessageReceived(message);
    }
    @SuppressLint("MissingPermission")
    private void SendNotification(String t, String b){
        Bitmap bitmap = BitmapFactory.decodeResource( getResources(), R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle(t)
                .setContentText(b)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bitmap)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getNotificationId(), notification);

        /*NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null){
            notificationManager.notify(getNotificationId(), notification);
        }*/
    }
    private int getNotificationId(){
        return (int) new Date().getTime();
    }

    private void showNotification(String t,String b) {
        Intent intent =  new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"noti")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT));
        builder = builder.setContent(customLocatin(t,b));
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("noti","shoseapp",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0,builder.build());
    }

    private RemoteViews customLocatin(String title, String body){
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),R.layout.item_location);
        remoteViews.setTextViewText(R.id.titlelocatin,title);
        remoteViews.setTextViewText(R.id.bodyLocation,body);
        remoteViews.setImageViewResource(R.id.imageLocation,R.drawable.notifications_24);
        return remoteViews;
    }
}
