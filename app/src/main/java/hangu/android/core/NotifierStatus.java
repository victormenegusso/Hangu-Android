package hangu.android.core;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import hangu.android.HomeActivity;
import hangu.android.R;
import hangu.android.ServerAppActivity;
import hangu.android.WebAppActivity;
import hangu.android.entity.ServerApp;

/**
 * Created by victor on 26/03/17.
 */

public class NotifierStatus {

    public void notifyWebAppOffline(Context context, int id, String url){
        Log.d("NotifierStatus", id+" "+url);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.myrect)
                        .setContentTitle("Web App Offine")
                        .setContentText("Web App:" +url);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, WebAppActivity.class);
        resultIntent.putExtra(WebAppActivity.IN_WEB_APP_ID,id);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(WebAppActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        Integer.parseInt("1"+id),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(Integer.parseInt("1"+id), mBuilder.build());
    }


    public void notifyServerAppOffline(Context context, int id, String url){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.myrect)
                        .setContentTitle("Server App Offine")
                        .setContentText("Server App:" +url);

        Intent resultIntent = new Intent(context, ServerAppActivity.class);
        resultIntent.putExtra(ServerAppActivity.IN_SERVER_APP_ID,id);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ServerAppActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        Integer.parseInt("2"+id),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(Integer.parseInt("2"+id), mBuilder.build());
    }

}
