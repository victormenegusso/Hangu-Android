package hangu.android.service.socket;
import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.UUID;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * Created by victor menegusso on 04/03/17.
 */

public class ReaderLog extends IntentService {


    public static final String ACTION = "hangu.android.service.socket.ReaderLog";
    public ReaderLog() {
        super("ReaderLog");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.d("ReaderLog","onHandleIntent");

        String dataString = workIntent.getDataString();
        //Log.d("ReaderLog","dataString");

        new Thread(new Runnable(){
            public void run() {
                int bytesAvailable;
                String id = UUID.randomUUID().toString();
                try{
                    System.out.println("Começando o cliente");

                    String txtReturn="";
                    Socket socket = new Socket("192.168.25.178",5000);

                    InputStream inStream = socket.getInputStream();
                    OutputStream outStream = socket.getOutputStream();

                    DataInputStream buffer_entrada = new DataInputStream(inStream);
                    DataOutputStream buffer_saida = new DataOutputStream(outStream);


                    while(true){
                        /*
                        //Log.d("ReaderLog", "while");
                        txtReturn += buffer_entrada.readUTF();
                        //Log.d("ReaderLog", txtReturn);

                        if(buffer_entrada.available() == 0) {
                            Log.d("ReaderLog", "sendBroadcast");
                            Intent intent = new Intent(ACTION);
                            intent.putExtra("read", txtReturn);
                            LocalBroadcastManager.getInstance( getBaseContext()).sendBroadcast(intent);
                            txtReturn = "";

                            //Thread.sleep(1000);
                        }

                        */

                        Log.d("while",id);
                        Thread.sleep(5000);
                    }

                    //Thread.sleep(20000);
                    //socket.close();

                }
                catch (Exception e) {
                    e.printStackTrace();
                }



                /*
                while(true)
                {
                    Log.d("ReaderLog", "thread");
                    try {
                        Thread.sleep(1000);
                        Intent intent = new Intent("ReaderLog");
                        intent.putExtra("read", ""+ UUID.randomUUID().toString());
                        LocalBroadcastManager.getInstance( getBaseContext()).sendBroadcast(intent);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                */

            }
        }).start();
    }
}