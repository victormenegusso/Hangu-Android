package hangu.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import hangu.android.service.socket.ReaderLog;

public class SocketReaderLog extends AppCompatActivity {

    public static final String IN_SOCKET_HOST = "IN_SOCKET_HOST";
    public static final String IN_SOCKET_PORT = "IN_SOCKET_PORT";
    public static final String IN_PATH = "IN_PATH";

    private InnerReceiver receiver = null;
    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_reader_log);

        txtView = (TextView)this.findViewById(R.id.socket_reader_log_txtView_log);

        String socketHost = getIntent().getStringExtra(IN_SOCKET_HOST);
        int socketPort = getIntent().getIntExtra(IN_SOCKET_PORT,5000);
        String path = getIntent().getStringExtra(IN_PATH);

        receiver = new InnerReceiver();
        Intent intent = new Intent(this, ReaderLog.class);
        intent.putExtra(ReaderLog.IN_SOCKET_HOST, socketHost);
        intent.putExtra(ReaderLog.IN_SOCKET_PORT,socketPort);
        intent.putExtra(ReaderLog.IN_PATH,path);

        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(ReaderLog.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private class InnerReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            String returno = intent.getStringExtra(ReaderLog.OUT_TXT_READ);
            txtView.setText( txtView.getText() + returno );
        }
    }

}
