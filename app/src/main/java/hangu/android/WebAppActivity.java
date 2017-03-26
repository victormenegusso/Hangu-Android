package hangu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import hangu.android.core.SchedulerCheck;
import hangu.android.dao.WebAppDAO;
import hangu.android.entity.WebApp;

public class WebAppActivity extends AppCompatActivity {

    public static final String IN_WEB_APP = "IN_WEB_APP";
    public static final String IN_WEB_APP_ID = "IN_WEB_APP_ID";
    public static final String OUT_WEB_APP = "OUT_WEB_APP";
    public static final String OUT_WEB_APP_ID = "OUT_WEB_APP_ID";

    public static final int RESULT_DELETE_OK = 2;
    public static final int RESULT_EDIT_OK = 3;
    public static final int REQUEST_EDIT = 1;

    private boolean hasEdit;

    private TextView textViewName;
    private TextView textViewURL;
    private TextView textViewHttpMethod;
    private TextView textViewCheck;

    private WebApp webApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_app_main);

        hasEdit = false;

        getExtras();
        bindInterface();
        loadInterface();
    }

    private void getExtras() {

        if (getIntent().getSerializableExtra(IN_WEB_APP) != null) {
            webApp = (WebApp) getIntent().getSerializableExtra(IN_WEB_APP);
        } else {
            int id = (int) getIntent().getSerializableExtra(IN_WEB_APP_ID);
            Log.d("WebAppActivity",""+id);
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
    }

    private void loadInterface() {
        textViewName.setText(webApp.getName());
        textViewURL.setText(webApp.getUrl());
        textViewHttpMethod.setText(webApp.getHttpMethod());
        textViewCheck.setText(Long.toString(webApp.getCheckInPeriod()));
    }

    public void edit(View view) {
        Intent intent = new Intent(this, PersistWebAppActivity.class);
        intent.putExtra(PersistWebAppActivity.IN_WEB_APP, (Serializable) webApp);
        //startActivity(intent);
        startActivityForResult(intent,REQUEST_EDIT);
    }

    public void delete(View view) {
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
        Log.d("teste","onBackPressed");
        if( hasEdit ){
            Intent it = new Intent();
            it.putExtra(OUT_WEB_APP, webApp);

            setResult(RESULT_OK, it);
        }
    }


    @Override
    public void finish() {
        super.finish();
        Log.d("teste","finish");
    }

}
