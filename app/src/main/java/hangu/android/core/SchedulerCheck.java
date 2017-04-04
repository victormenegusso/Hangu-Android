package hangu.android.core;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;

import hangu.android.dao.ServerAppDAO;
import hangu.android.dao.WebAppDAO;
import hangu.android.entity.ServerApp;
import hangu.android.entity.WebApp;
import hangu.android.service.WebAppCheck;
import hangu.android.service.ServerAppCheck;

/**
 * Created by Victor Menegussoon 26/03/17.
 * jobId -> startsWith = 1 WebApp / 2 Server
 */

public class SchedulerCheck {
    private static SchedulerCheck scheduler;
    private SchedulerCheck(){}

    public void scheduleAll(Context context){
        scheduleAllWebApps(context);
        scheduleAllServerApps(context);
    }

    public void scheduleAllWebApps(Context context){
        WebAppDAO dao = new WebAppDAO(context);
        dao.open();
        for(WebApp webApp : dao.list()){
            if(webApp.getCheckInPeriod() != 0)
                scheduleWebApps(context,webApp);
        }
        dao.close();
    }

    public void scheduleAllServerApps(Context context){
        ServerAppDAO dao = new ServerAppDAO(context);
        dao.open();
        for(ServerApp serverApp : dao.list()){
            if(serverApp.getCheckInPeriod() != 0)
                scheduleServerApps(context,serverApp);
        }
        dao.close();
    }

    public boolean scheduleWebApps(Context context, WebApp webApp){

        ComponentName mServiceComponent = new ComponentName(context, WebAppCheck.class);
        JobInfo.Builder builder = new JobInfo.Builder(generateIDWebApp( webApp.getId() ), mServiceComponent);
        PersistableBundle pb = new PersistableBundle();

        pb.putInt(WebAppCheck.IN_ID, webApp.getId());
        pb.putString(WebAppCheck.IN_URL,webApp.getUrl());
        pb.putString(WebAppCheck.IN_HTTPMETHOD,webApp.getHttpMethod());

        builder.setPersisted(true);
        builder.setPeriodic( webApp.getCheckInPeriod() );
        builder.setExtras(pb);

        JobScheduler mJobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        return mJobScheduler.schedule( builder.build() ) <= 0;
    }

    public boolean scheduleServerApps(Context context, ServerApp serverApp){

        ComponentName mServiceComponent = new ComponentName(context, ServerAppCheck.class);
        JobInfo.Builder builder = new JobInfo.Builder(generateIDServerApp( serverApp.getId() ), mServiceComponent);
        PersistableBundle pb = new PersistableBundle();

        pb.putInt(ServerAppCheck.IN_ID, serverApp.getId());
        pb.putString(ServerAppCheck.IN_URL,serverApp.getUrl());

        builder.setPersisted(true);
        builder.setPeriodic( serverApp.getCheckInPeriod() );
        builder.setExtras(pb);

        JobScheduler mJobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        return mJobScheduler.schedule( builder.build() ) <= 0;
    }

    public void cancelAllSchedule(Context context){
        JobScheduler mJobScheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE );
        mJobScheduler.cancelAll();
    }

    public void cancelScheduleWebApp(Context context, int webAppID){
        int id = generateIDWebApp(webAppID);
        JobScheduler mJobScheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE );
        mJobScheduler.cancel(id);
    }

    public void cancelScheduleServerApp(Context context, int serverAppID){
        int id = generateIDServerApp(serverAppID);
        JobScheduler mJobScheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE );
        mJobScheduler.cancel(id);
    }

    public static SchedulerCheck getScheduler() {
        if(scheduler == null)
            scheduler = new SchedulerCheck();
        return scheduler;
    }

    /**
     * startsWith = 1
     */
    private int generateIDWebApp(int webAppID){
        int id;
        id = Integer.parseInt("1"+webAppID);
        return id;
    }

    /**
     * startsWith = 2
     */
    private int generateIDServerApp(int serverAppID){
        int id;
        id = Integer.parseInt("2"+serverAppID);
        return id;
    }

}
