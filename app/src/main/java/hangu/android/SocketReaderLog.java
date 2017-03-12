package hangu.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import hangu.android.entity.ServerApp;
import hangu.android.entity.WebApp;
import hangu.android.service.socket.ReaderLog;

public class SocketReaderLog extends AppCompatActivity {

    private ServerApp serverApp;

    private InnerReceiver receiver = null;
    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SocketReaderLog","oncreate");
        setContentView(R.layout.activity_socket_reader_log);


        txtView = (TextView)this.findViewById(R.id.socket_reader_log_txtView_log);

        receiver = new InnerReceiver();
        Intent intent = new Intent(this, ReaderLog.class);

        intent.putExtra(ReaderLog.IN_SOCKET_HOST, "192.168.25.178");
        intent.putExtra(ReaderLog.IN_SOCKET_PORT,5000);
        intent.putExtra(ReaderLog.IN_PATH,"/home/victor/Downloads/apache-tomcat-8.5.11/logs/catalina.out");

        startService(intent);

        /*
        if(receiver == null) {
            Log.d("tail","receiver == null");
            receiver = new InnerReceiver();

            IntentFilter filter = new IntentFilter(ReaderLog.ACTION);
            LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

            Intent intent = new Intent(this, ReaderLog.class);
            startService(intent);
        }else{
            Log.d("tail","receiver != null");
        }
        */

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SocketReaderLog","onResume");

        IntentFilter filter = new IntentFilter(ReaderLog.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SocketReaderLog","onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private class InnerReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {




            Log.d("SocketReaderLog","onReceive");
            //Log.d("tail",intent.getAction());
            String returno = intent.getStringExtra(ReaderLog.OUT_TXT_READ);
            txtView.setText( txtView.getText() + returno );
            /*
            String fileName = intent.getStringExtra("fileName");
            try {
                InputStream inputStream = getBaseContext().openFileInput(fileName);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                imgWeb.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            */
        }
    }

}
