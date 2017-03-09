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

import hangu.android.service.socket.ReaderLog;

public class SocketReaderLog extends AppCompatActivity {

    private InnerReceiver receiver = null;

    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tail","oncreate");
        setContentView(R.layout.activity_socket_reader_log);

        txtView = (TextView)this.findViewById(R.id.socket_reader_log_txtView_log);

        receiver = new InnerReceiver();
        Intent intent = new Intent(this, ReaderLog.class);
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
        Log.d("tail","onResume");

        IntentFilter filter = new IntentFilter(ReaderLog.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tail","onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private class InnerReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {




            Log.d("tail","onReceive");
            //Log.d("tail",intent.getAction());
            String returno = intent.getStringExtra("read");
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
