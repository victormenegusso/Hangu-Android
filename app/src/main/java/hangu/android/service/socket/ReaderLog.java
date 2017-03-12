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

    public static final String IN_SOCKET_HOST = "IN_SOCKET_HOST";
    public static final String IN_SOCKET_PORT = "IN_SOCKET_PORT";
    public static final String IN_PATH = "IN_PATH";

    public static final String OUT_TXT_READ = "OUT_STATUS";

    public ReaderLog() {
        super("ReaderLog");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.d("ReaderLog","onHandleIntent");

        final String host = workIntent.getStringExtra(IN_SOCKET_HOST);
        final String path = workIntent.getStringExtra(IN_PATH);
        final int port = workIntent.getIntExtra(IN_SOCKET_PORT,5000);

        new Thread(new Runnable(){
            public void run() {
                try{
                    int bytesAvailable;
                    String txtReturn="";

                    Socket socketClient = new Socket(host,port);

                    InputStream inputStream = socketClient.getInputStream();
                    OutputStream outputStream = socketClient.getOutputStream();

                    DataInputStream dataInputStream = new DataInputStream(inputStream);
                    DataOutputStream dataOutInputStream = new DataOutputStream(outputStream);

                    String json="{\"service\":\"read_log\",\"path\":\""+path+"\"}";
                    dataOutInputStream.writeUTF(json);


                    while(true){

                        txtReturn += dataInputStream.readUTF();
                        //Log.d("ReaderLog", txtReturn);

                        if(dataInputStream.available() == 0) {
                            Log.d("ReaderLog", "sendBroadcast");
                            Intent intent = new Intent(ACTION);
                            intent.putExtra(OUT_TXT_READ, txtReturn);
                            LocalBroadcastManager.getInstance( getBaseContext()).sendBroadcast(intent);
                            txtReturn = "";
                        }


                    }

                    //socketClient.close();

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}


        /*
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
/*
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
/*
            }
        }).start();*/
