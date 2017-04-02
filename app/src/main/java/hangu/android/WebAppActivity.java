package hangu.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import java.io.Serializable;
import hangu.android.core.SchedulerCheck;
import hangu.android.dao.WebAppDAO;
import hangu.android.entity.Status;
import hangu.android.entity.WebApp;
import hangu.android.service.HttpConnector;

/**
 * @author Victor Menegusso
 */
public class WebAppActivity extends AppCompatActivity {

    public static final String IN_WEB_APP = "IN_WEB_APP";
    public static final String IN_WEB_APP_ID = "IN_WEB_APP_ID";
    public static final String OUT_WEB_APP = "OUT_WEB_APP";
    public static final String OUT_WEB_APP_ID = "OUT_WEB_APP_ID";

    public static final int RESULT_DELETE_OK = 2;
    public static final int RESULT_EDIT_OK = 3;
    public static final int REQUEST_EDIT = 1;

    private TextView textViewName;
    private TextView textViewURL;
    private TextView textViewHttpMethod;
    private TextView textViewCheck;
    private TextView textViewStatus;

    private boolean hasEdit;
    private WebApp webApp;
    private InnerReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_app_main);

        hasEdit = false;
        receiver = new InnerReceiver();

        getExtras();
        bindInterface();
        loadInterface();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_web_app, menu);
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
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(HttpConnector.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        check();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_EDIT && resultCode == PersistWebAppActivity.RESULT_EDIT_OK){
            hasEdit = true;

            webApp = (WebApp) data.getSerializableExtra( PersistWebAppActivity.OUT_WEB_APP );
            loadInterface();
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        if( hasEdit ){
            Intent it = new Intent();
            it.putExtra(OUT_WEB_APP, webApp);

            setResult(RESULT_OK, it);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void getExtras() {

        if (getIntent().getSerializableExtra(IN_WEB_APP) != null) {
            webApp = (WebApp) getIntent().getSerializableExtra(IN_WEB_APP);
        } else {
            int id = (int) getIntent().getSerializableExtra(IN_WEB_APP_ID);
            WebAppDAO dao = new WebAppDAO(this);
            dao.open();
            webApp = dao.get(id);
            dao.close();
        }
    }

    private void bindInterface() {
        textViewName = (TextView) findViewById(R.id.textView_name);
        textViewURL = (TextView) findViewById(R.id.textView_url);
        textViewHttpMethod = (TextView) findViewById(R.id.textView_httpMethod);
        textViewCheck = (TextView) findViewById(R.id.textView_check);
        textViewStatus = (TextView) findViewById(R.id.textView_status);
    }

    private void loadInterface() {
        textViewName.setText(webApp.getName());
        textViewURL.setText(webApp.getUrl());
        textViewHttpMethod.setText(webApp.getHttpMethod());
        textViewCheck.setText(Long.toString(webApp.getCheckInPeriod()));
        updateInterfaceStatus();
    }

    public void edit() {
        Intent intent = new Intent(this, PersistWebAppActivity.class);
        intent.putExtra(PersistWebAppActivity.IN_WEB_APP, (Serializable) webApp);
        startActivityForResult(intent,REQUEST_EDIT);
    }

    public void delete() {
        WebAppDAO dao = new WebAppDAO(this);

        dao.open();
        dao.remove(webApp.getId());
        dao.close();

        SchedulerCheck.getScheduler().cancelScheduleWebApp(this,webApp.getId());

        //
        Intent it = new Intent();
        it.putExtra(OUT_WEB_APP_ID, webApp.getId());

        setResult(RESULT_DELETE_OK, it);

        finish();
    }

    public void check(){
        Intent intent = new Intent(this, HttpConnector.class);
        intent.putExtra(HttpConnector.IN_URL,webApp.getUrl());
        startService(intent);
    }

    private void updateInterfaceStatus(){
        if(webApp.getStatus() == Status.ONLINE)
            textViewStatus.setText("ONLINE");
        else if(webApp.getStatus() == Status.OFFLINE)
            textViewStatus.setText("OFFLINE");
        else if(webApp.getStatus() == Status.ONLINE)
            textViewStatus.setText("Conectando...");
    }

    private class InnerReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {

            String url;
            boolean isCon;

            url = intent.getStringExtra(HttpConnector.OUT_URL);
            isCon = intent.getBooleanExtra(HttpConnector.OUT_ISCONNECTED,false);
            if(isCon)
                webApp.setStatus(Status.ONLINE);
            else
                webApp.setStatus(Status.OFFLINE);

            updateInterfaceStatus();
        }
    }
}
