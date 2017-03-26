package hangu.android.core;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import hangu.android.dao.WebAppDAO;
import hangu.android.entity.WebApp;
import hangu.android.service.WebAppCheck;

/**
 * Created by Victor Menegussoon 26/03/17.
 */

public class SchedulerCheck {
    private static SchedulerCheck scheduler;
    private SchedulerCheck(){}

    public void scheduleAll(Context context){
        scheduleAllWebApps(context);
    }

    public void cancelAllSchedule(Context context){
        JobScheduler mJobScheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE );
        mJobScheduler.cancelAll();
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

    public boolean scheduleWebApps(Context context, WebApp webApp){

        ComponentName mServiceComponent = new ComponentName(context, WebAppCheck.class);
        JobInfo.Builder builder = new JobInfo.Builder(webApp.getId(), mServiceComponent);
        PersistableBundle pb = new PersistableBundle();

        pb.putString(WebAppCheck.IN_URL,webApp.getUrl());
        pb.putString(WebAppCheck.IN_HTTPMETHOD,webApp.getHttpMethod());

        builder.setPersisted(true);
        builder.setPeriodic( webApp.getCheckInPeriod() );
        builder.setExtras(pb);

        JobScheduler mJobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        return mJobScheduler.schedule( builder.build() ) <= 0;
    }

    public static SchedulerCheck getScheduler() {
        if(scheduler == null)
            scheduler = new SchedulerCheck();
        return scheduler;
    }
}
