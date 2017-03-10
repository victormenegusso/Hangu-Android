package hangu.android.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Victor Menegusso on 09/03/17.
 */

public class HttpConnector extends IntentService {

    public static final String ACTION = "hangu.android.service.HttpConnector";

    public static final String IN_URL = "IN_URL";
    public static final String IN_HTTPMETHOD = "IN_HTTPMETHOD";
    public static final String IN_TIMEOUT = "IN_TIMEOUT";

    public static final String OUT_ISCONNECTED = "OUT_ISCONNECTED";
    public static final String OUT_URL = "OUT_URL";

    public HttpConnector() {
        super("HttpConnector");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.d("HttpConnector","onHandleIntent");

        String url = workIntent.getStringExtra(IN_URL);
        String httpMethod = getHttpMethod(workIntent);
        int timeout = workIntent.getIntExtra(IN_TIMEOUT,1000);

        Intent intent = new Intent(ACTION);
        intent.putExtra(OUT_URL, url);
        intent.putExtra(OUT_ISCONNECTED, pingURL(url,httpMethod,timeout));
        LocalBroadcastManager.getInstance( getBaseContext()).sendBroadcast(intent);
    }

    private boolean pingURL(String url, String httpMethod, int timeout) {

        if(url.startsWith("http"))
            url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.
        else
            url = "http://"+url;
        try {

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod(httpMethod);
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return false;
        }
    }

    private String getHttpMethod(Intent workIntent){
        String httpMethod = workIntent.getStringExtra(IN_HTTPMETHOD);
        if(httpMethod != null){
            return httpMethod;
        }

        return "GET";
    }

}