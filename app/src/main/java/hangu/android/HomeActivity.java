package hangu.android;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import hangu.android.core.SchedulerCheck;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openWebApp(View view) {
        Intent intent = new Intent(this, WebAppActivity.class);
        startActivity(intent);
    }

    public void openListWebApps(View view) {
        Intent intent = new Intent(this, ListWebAppsActivity.class);
        startActivity(intent);
    }

    public void openServerApp(View view) {
        Intent intent = new Intent(this, PersistServerAppActivity.class);
        startActivity(intent);
    }

    public void openListServerApps(View view) {
        Intent intent = new Intent(this, ListServerAppsActivity.class);
        startActivity(intent);
    }


    public void openHanguSocket(View view) {
        Intent intent = new Intent(this, PersistHanguSocketActivity.class);
        startActivity(intent);
    }

    public void openListHanguSockets(View view) {
        Intent intent = new Intent(this, ListHanguSocketsActivity.class);
        startActivity(intent);
    }



    public void scheduler(View view) {
        SchedulerCheck.getScheduler().scheduleAll(this);
    }

    public void schedulerCancel(View view) {
        SchedulerCheck.getScheduler().cancelAllSchedule(this);
    }

    public void notificar(View view){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.myrect)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, HomeActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(HomeActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    /*
    private void agendar(int id){
        ComponentName mServiceComponent = new ComponentName(this, JobSchedulerService.class);
        JobInfo.Builder builder = new JobInfo.Builder(id, mServiceComponent);

        //JobInfo.Builder builder = new JobInfo.Builder(id, mServiceComponent)
                //.setBackoffCriteria(5000,JobInfo.BACKOFF_POLICY_LINEAR)
                //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                //.setPeriodic(5000);

        builder.setPersisted(true);
        builder.setBackoffCriteria(5000,JobInfo.BACKOFF_POLICY_LINEAR);
        builder.setPeriodic( 2000 );
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        //builder.setRequiresCharging(true);

        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        if( mJobScheduler.schedule( builder.build() ) <= 0 ) {
            Log.d("deu","merrca");
        }
    }
    */
}
