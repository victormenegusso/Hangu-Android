package hangu.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import java.io.Serializable;

import hangu.android.core.SchedulerCheck;
import hangu.android.dao.ServerAppDAO;
import hangu.android.entity.ServerApp;
import hangu.android.entity.Status;
import hangu.android.hangu.android.view.converter.PeriodConverter;
import hangu.android.service.HttpConnector;
import hangu.android.service.socket.ExecutorScript;

public class ServerAppActivity extends AppCompatActivity {

    public static final String IN_SERVER_APP = "IN_SERVER_APP";
    public static final String IN_SERVER_APP_ID = "IN_SERVER_APP_ID";
    public static final String OUT_SERVER_APP = "OUT_SERVER_APP";
    public static final String OUT_SERVER_APP_ID = "OUT_SERVER_APP_ID";

    public static final int RESULT_DELETE_OK = 2;
    public static final int RESULT_EDIT_OK = 3;
    public static final int REQUEST_EDIT = 1;

    private ServerApp serverApp;
    private InnerReceiver receiver = null;
    private InnerReceiverStatus receiverStatus;
    private boolean hasEdit;

    private TextView textViewName;
    private TextView textViewURL;
    private TextView textViewStatus;
    private TextView textViewLogPath;;
    private TextView textViewScriptStart;
    private TextView textViewScriptStop;
    private TextView textViewHanguSocket;
    private TextView textViewCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_app);

        hasEdit = false;
        receiver = new InnerReceiver();
        receiverStatus = new InnerReceiverStatus();

        getExtras();
        bindInterface();
        loadInterface();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_server_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                delete();
                return true;

            case R.id.action_edit:
                edit();
                return true;

            case R.id.action_check:
                check();
                return true;

            case R.id.action_log:
                openSocketReaderLog();
                return true;

            case R.id.action_stop:
                executeStop();
                return true;

            case R.id.action_start:
                executeStart();
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(ExecutorScript.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        IntentFilter filter2 = new IntentFilter(HttpConnector.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiverStatus, filter2);

        check();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiverStatus);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_EDIT && resultCode == PersistServerAppActivity.RESULT_EDIT_OK){
            hasEdit = true;

            serverApp = (ServerApp) data.getSerializableExtra( PersistServerAppActivity.OUT_SERVER_APP );
            loadInterface();
        }
    }

    private void getExtras() {

        if (getIntent().getSerializableExtra(IN_SERVER_APP) != null) {
            serverApp = (ServerApp) getIntent().getSerializableExtra(IN_SERVER_APP);
        } else {
            int id = (int) getIntent().getSerializableExtra(IN_SERVER_APP_ID);
            ServerAppDAO dao = new ServerAppDAO(this);
            dao.open();
            serverApp = dao.get(id);
            dao.close();
        }
    }

    private void bindInterface() {
        textViewName = (TextView) findViewById(R.id.textView_name);
        textViewURL = (TextView) findViewById(R.id.textView_url);

        textViewStatus = (TextView) findViewById(R.id.textView_status);
        textViewLogPath = (TextView) findViewById(R.id.textView_log_path);
        textViewScriptStart = (TextView) findViewById(R.id.textView_scriptStart);
        textViewScriptStop = (TextView) findViewById(R.id.textView_scriptStop);
        textViewHanguSocket = (TextView) findViewById(R.id.textView_hanguSocket);
        textViewCheck = (TextView) findViewById(R.id.textView_check);
    }

    private void loadInterface() {
        textViewName.setText(serverApp.getName());
        textViewURL.setText(serverApp.getUrl());
        textViewLogPath.setText(serverApp.getPathFileLog());
        textViewScriptStart.setText(serverApp.getPathProcessStart());
        textViewScriptStop.setText(serverApp.getPathProcessStop());
        textViewHanguSocket.setText(serverApp.getHanguSocket().getHost());
        textViewCheck.setText(PeriodConverter.getLabelFromValue( serverApp.getCheckInPeriod()));
        updateInterfaceStatus();
    }

    private void updateInterfaceStatus(){
        if(serverApp.getStatus() == Status.ONLINE)
            textViewStatus.setText("ONLINE");
        else if(serverApp.getStatus() == Status.OFFLINE)
            textViewStatus.setText("OFFLINE");
        else if(serverApp.getStatus() == Status.WAIT_CONNECTION)
            textViewStatus.setText("Conectando...");
    }

    public void executeStart() {
        Intent intent = new Intent(this, ExecutorScript.class);
        intent.putExtra(ExecutorScript.IN_SOCKET_HOST, serverApp.getHanguSocket().getHost());
        intent.putExtra(ExecutorScript.IN_SOCKET_PORT, serverApp.getHanguSocket().getPort());
        intent.putExtra(ExecutorScript.IN_SCRIPT, serverApp.getPathProcessStart());
        startService(intent);
    }

    public void executeStop() {
        Intent intent = new Intent(this, ExecutorScript.class);
        intent.putExtra(ExecutorScript.IN_SOCKET_HOST, serverApp.getHanguSocket().getHost());
        intent.putExtra(ExecutorScript.IN_SOCKET_PORT, serverApp.getHanguSocket().getPort());
        intent.putExtra(ExecutorScript.IN_SCRIPT, serverApp.getPathProcessStop());
        startService(intent);
    }

    public void openSocketReaderLog() {
        Intent intent = new Intent(this, SocketReaderLog.class);
        intent.putExtra(SocketReaderLog.IN_SOCKET_HOST, serverApp.getHanguSocket().getHost());
        intent.putExtra(SocketReaderLog.IN_SOCKET_PORT, serverApp.getHanguSocket().getPort());
        intent.putExtra(SocketReaderLog.IN_PATH, serverApp.getPathFileLog());

        startActivity(intent);
    }

    public void edit() {
        Intent intent = new Intent(this, PersistServerAppActivity.class);
        intent.putExtra(PersistServerAppActivity.IN_SERVER_APP, (Serializable) serverApp);
        startActivityForResult(intent,REQUEST_EDIT);
    }

    public void delete() {
        ServerAppDAO dao = new ServerAppDAO(this);

        dao.open();
        dao.remove(serverApp.getId());
        dao.close();

        SchedulerCheck.getScheduler().cancelScheduleServerApp(this,serverApp.getId());

        //
        Intent it = new Intent();
        it.putExtra(OUT_SERVER_APP_ID, serverApp.getId());
        setResult(RESULT_DELETE_OK, it);

        finish();
    }

    public void check(){
        Intent intent = new Intent(this, HttpConnector.class);
        intent.putExtra(HttpConnector.IN_URL,serverApp.getUrl());
        startService(intent);
    }

    private class InnerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("ServerAppActivity", "onReceive");
        }
    }

    private class InnerReceiverStatus extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {

            String url;
            boolean isCon;

            url = intent.getStringExtra(HttpConnector.OUT_URL);
            isCon = intent.getBooleanExtra(HttpConnector.OUT_ISCONNECTED,false);
            if(isCon)
                serverApp.setStatus(Status.ONLINE);
            else
                serverApp.setStatus(Status.OFFLINE);

            updateInterfaceStatus();
        }
    }

}
