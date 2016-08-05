package com.govt_exam_preparation;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.govt_exam_preparation.activities.Dashboard_Activity;
import com.govt_exam_preparation.model.Notification_Model;


/**
 * Created by wscubetech on 10/7/15.
 */
public class NotifyMe {

    Context context;
    String comingFrom = "", heading = "";
    NotificationManager notificationManager;

    public NotifyMe(Context context) {
        this.context = context;
        heading = context.getString(R.string.app_name);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void sendNotification(Notification_Model model) {
        int notifierId = Integer.parseInt(model.getId());
        Intent intent = new Intent(context, Dashboard_Activity.class);
        intent.putExtra("ComingFrom", "Notification");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        NotificationCompat.Builder builder = getMyNotification(intent, model.getTitle());
        notificationManager.notify(notifierId, builder.build());
    }

    public NotificationCompat.Builder getMyNotification(Intent intent, String message) {
        NotificationCompat.Builder noBuilder;

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            noBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(getNotificationIcon())
                    .setContentTitle(heading)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setTicker(message)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message).setBigContentTitle(heading))
                    .setContentIntent(pendingIntent);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                noBuilder.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }

        } else {
            noBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(getNotificationIcon())
                    .setContentTitle(heading)
                    .setTicker(message)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setContentIntent(pendingIntent);
        }

        return noBuilder;
    }

    public int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.logo_white_24 : R.mipmap.ic_launcher;
    }

}
