package hangu.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;

import hangu.android.dao.ServerAppDAO;
import hangu.android.entity.ServerApp;
import hangu.android.service.socket.ExecutorScript;
import hangu.android.service.socket.ReaderLog;

public class ServerAppActivity extends AppCompatActivity {

    private ServerApp serverApp;

    private InnerReceiver receiver = null;

    private TextView txtViewName;
    private TextView txtViewURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_app);

        ServerAppDAO dao = new ServerAppDAO();
        serverApp = dao.list().get(0);

        txtViewName = (TextView) findViewById(R.id.txtView_name);
        txtViewURL = (TextView) findViewById(R.id.txtView_url);

        txtViewName.setText(serverApp.getName());
        txtViewURL.setText(serverApp.getHost());

        receiver = new InnerReceiver();
    }

    public void executeStart(View view){
        Intent intent = new Intent(this, ExecutorScript.class);
        intent.putExtra(ExecutorScript.IN_SOCKET_HOST, "192.168.25.178");
        intent.putExtra(ExecutorScript.IN_SOCKET_PORT,5000);
        intent.putExtra(ExecutorScript.IN_SCRIPT,serverApp.getPathProcessStart());
        startService(intent);
    }

    public void executeStop(View view){
        Intent intent = new Intent(this, ExecutorScript.class);
        intent.putExtra(ExecutorScript.IN_SOCKET_HOST, "192.168.25.178");
        intent.putExtra(ExecutorScript.IN_SOCKET_PORT,5000);
        intent.putExtra(ExecutorScript.IN_SCRIPT,serverApp.getPathProcessStop());
        startService(intent);
    }

    public void openSocketReaderLog(View view){
        Log.d("ServerAppActivity","openSocketReaderLog");
        Intent intent = new Intent(this, SocketReaderLog.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ServerAppActivity","onResume");

        IntentFilter filter = new IntentFilter(ExecutorScript.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ServerAppActivity","onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private class InnerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("ServerAppActivity", "onReceive");

        }
    }

}
