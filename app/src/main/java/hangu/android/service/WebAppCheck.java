package hangu.android.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import hangu.android.HomeActivity;
import hangu.android.R;
import hangu.android.core.NotifierStatus;

/**
 * Created by Victor Menegusso on 26/03/17.
 */

public class WebAppCheck extends JobService {

    public static final String IN_URL = "IN_URL";
    public static final String IN_HTTPMETHOD = "IN_HTTPMETHOD";

    private String url;
    private String httpMethod;

    /*
    private Handler mJobHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage( Message msg ) {

            boolean r = pingURL();
            Log.i("WebAppCheck", "on start job: " + r);

            // info the system task finish
            jobFinished( (JobParameters) msg.obj, false );
            return true;
        }

    } );
    */

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("WebAppCheck", "onStartJob: " + params.getJobId());
        loadExtras(params);
        //mJobHandler.sendMessage( Message.obtain( mJobHandler, 1, params ) );
        new Thread(new InnerThread(params)).start();
        return true; // EXECUTE in thread
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("TESTE", "onStopJob: " + params.getJobId());
        //mJobHandler.removeMessages( 2 );
        return true;
    }

    private void loadExtras(JobParameters params){
        PersistableBundle extras = params.getExtras();
        url = (String) extras.get(IN_URL);
        httpMethod = (String) extras.get(IN_HTTPMETHOD);
    }

    private boolean pingURL() {

        if(url.startsWith("http"))
            url = url.replaceFirst("^https", "http");
        else
            url = "http://"+url;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(7000);
            connection.setReadTimeout(7000);
            connection.setRequestMethod(httpMethod);
            int responseCode = connection.getResponseCode();
            Log.i("WebAppCheck", url+" CODE: "+responseCode);
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            //exception.printStackTrace();
            Log.i("WebAppCheck", url+" IOException: ");
            return false;
        }
    }

    private class InnerThread implements Runnable{
        JobParameters params;

        public InnerThread(JobParameters params){
            this.params = params;
        }
        @Override
        public void run() {
            boolean r = pingURL();
            Log.i("WebAppCheck", "on start job: "+params.getJobId() + r);

            if(!r) {
                NotifierStatus ns = new NotifierStatus();
                ns.notifyWebAppOffline(getApplicationContext(), url);
            }

            // info the system task finish
            jobFinished(params, false);
        }
    }

}