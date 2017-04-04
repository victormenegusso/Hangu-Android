package hangu.android.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.PersistableBundle;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import hangu.android.core.NotifierStatus;

/**
 * Created by Victor Menegusso on 26/03/17.
 * Usa o mesmo objeto para varios JOBS ( confirmar )
 */
public class ServerAppCheck extends JobService {

    public static final String IN_ID = "IN_ID";
    public static final String IN_URL = "IN_URL";

    public ServerAppCheck(){
        Log.i("ServerAppCheck", "Construtor ");
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("ServerAppCheck", "onStartJob: " + params.getJobId());

        PersistableBundle extras = params.getExtras();
        int id = extras.getInt(IN_ID);
        String url = (String) extras.get(IN_URL);
        String httpMethod = "get";

        new Thread(new InnerThread(params, id, url, httpMethod)).start();
        return true; // EXECUTE in thread
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private boolean pingURL(String url, String httpMethod) {

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
            Log.i("ServerAppCheck", url+" CODE: "+responseCode);
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return false;
        }
    }

    private class InnerThread implements Runnable{
        private JobParameters params;
        private int id;
        private String url;
        private String httpMethod;

        public InnerThread(JobParameters params, int id, String url, String httpMethod){
            this.params = params;
            this.id = id;
            this.url = url;
            this.httpMethod = httpMethod;
        }
        @Override
        public void run() {
            boolean r = pingURL(url,httpMethod);
            Log.i("ServerAppCheck", "on start job: "+params.getJobId() + r);

            if(!r) {
                NotifierStatus ns = new NotifierStatus();
                ns.notifyServerAppOffline(getApplicationContext(), id, url);
            }

            // info the system task finish
            jobFinished(params, false);
        }
    }

}