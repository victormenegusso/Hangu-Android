package hangu.android.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            connection.setRequestMethod(httpMethod);
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
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
            Log.i("WebAppCheck", "on start job: " + r);

            // info the system task finish
            jobFinished( params, false );
        }
    }

}