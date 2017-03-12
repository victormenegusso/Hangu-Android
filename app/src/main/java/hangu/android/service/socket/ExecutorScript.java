package hangu.android.service.socket;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Victor Menegusso on 12/03/17.
 */

public class ExecutorScript extends IntentService {

    public static final String ACTION = "hangu.android.service.socket.ExecutorScript";

    public static final String IN_SOCKET_HOST = "IN_SOCKET_HOST";
    public static final String IN_SOCKET_PORT = "IN_SOCKET_PORT";
    public static final String IN_SCRIPT = "IN_SCRIPT";

    public static final String OUT_STATUS = "OUT_STATUS";

    public ExecutorScript() {
        super("ExecutorScript");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.d("ExecutorScript","onHandleIntent");

        final String host = workIntent.getStringExtra(IN_SOCKET_HOST);
        final String script = workIntent.getStringExtra(IN_SCRIPT);
        final int port = workIntent.getIntExtra(IN_SOCKET_PORT,5000);


        new Thread(new Runnable(){
            public void run() {
                try{

                    Socket socketClient = new Socket(host,port);

                    InputStream inputStream = socketClient.getInputStream();
                    OutputStream outputStream = socketClient.getOutputStream();

                    DataInputStream dataInputStream = new DataInputStream(inputStream);
                    DataOutputStream dataOutInputStream = new DataOutputStream(outputStream);

                    String json="{\"service\":\"execute_script\",\"cmd\":\""+script+"\"}";
                    dataOutInputStream.writeUTF(json);
                    boolean r = dataInputStream.readBoolean();

                    Intent intent = new Intent(ACTION);
                    intent.putExtra(OUT_STATUS, r);
                    LocalBroadcastManager.getInstance( getBaseContext()).sendBroadcast(intent);

                    socketClient.close();

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
