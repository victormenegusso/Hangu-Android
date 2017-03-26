package hangu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import hangu.android.core.SchedulerCheck;
import hangu.android.dao.WebAppDAO;
import hangu.android.entity.ServerApp;
import hangu.android.entity.WebApp;

public class PersistWebAppActivity extends AppCompatActivity {

    public static final String IN_WEB_APP = "IN_WEB_APP";
    public static final String OUT_WEB_APP = "OUT_WEB_APP";

    public static final int RESULT_EDIT_OK = 2;

    private EditText editTextName;
    private EditText editTextURL;
    private Spinner spinnerHttpMethod;
    private Spinner spinnerPeriod;
    private WebApp webApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_web_app);

        bindInterface();
        getExtras();
        loadInterface();
    }

    private void getExtras(){
        webApp = (WebApp) getIntent().getSerializableExtra(IN_WEB_APP);
    }
    private void bindInterface(){
        editTextName = (EditText) findViewById( R.id.editText_name );
        editTextURL = (EditText) findViewById( R.id.editText_url );
        spinnerHttpMethod = (Spinner) findViewById( R.id.spinner_http_method );
        spinnerPeriod = (Spinner) findViewById( R.id.spinner_period );
    }

    private void loadInterface(){
        if(webApp != null) {
            editTextName.setText( webApp.getName() );
            editTextURL.setText( webApp.getUrl() );
        }

        initSpinnerHttpMethod();
        initSpinnerPeriod();
    }

    private void initSpinnerHttpMethod() {
        String[] spinnerArray = new String[2];

        spinnerArray[0] = "GET";
        spinnerArray[1] = "HEAD";

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHttpMethod.setAdapter(adapter);
        spinnerHttpMethod.setSelection(0);
    }

    private void initSpinnerPeriod() {
        String[] spinnerArray = new String[6];

        spinnerArray[0] = "-";
        spinnerArray[1] = "10 s";
        spinnerArray[2] = "30 s";
        spinnerArray[3] = "1 min";
        spinnerArray[4] = "5 min";
        spinnerArray[5] = "30 min";

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriod.setAdapter(adapter);
        spinnerPeriod.setSelection(0);
    }

    private long getSpinnerPeriodValue(){
        int ind = spinnerPeriod.getSelectedItemPosition();
        switch (ind){
            case 0 : return 0;
            case 1 : return 10000;
            case 2 : return 30000;
            case 3 : return 60000;
            case 4 : return 300000;
            case 5 : return 1800000;
        }
        return 0;
    }


    public void save(View v){
        boolean isInsert = false;

        if(webApp == null){
            webApp = new WebApp();
            isInsert = true;
        }
        WebAppDAO dao = new WebAppDAO(this);

        webApp.setName( editTextName.getText().toString() );
        webApp.setUrl( editTextURL.getText().toString() );

        webApp.setHttpMethod( spinnerHttpMethod.getSelectedItem().toString() );
        webApp.setCheckInPeriod( getSpinnerPeriodValue() );

        dao.open();
        if(isInsert)
            dao.insert(webApp);
        else {
            dao.update(webApp);

            SchedulerCheck.getScheduler().scheduleWebApps(this,webApp);

            Intent it = new Intent();
            it.putExtra(OUT_WEB_APP, webApp);
            setResult(RESULT_EDIT_OK,it);
        }

        dao.close();

        finish();
    }
}
