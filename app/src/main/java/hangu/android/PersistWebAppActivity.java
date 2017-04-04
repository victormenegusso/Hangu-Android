package hangu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import hangu.android.core.SchedulerCheck;
import hangu.android.dao.Preferences;
import hangu.android.dao.WebAppDAO;
import hangu.android.entity.ServerApp;
import hangu.android.entity.WebApp;
import hangu.android.hangu.android.view.converter.PeriodConverter;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_persist_web_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                return true;
        }
        return false;
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
        initSpinnerHttpMethod();
        initSpinnerPeriod();

        if(webApp != null) {
            editTextName.setText( webApp.getName() );
            editTextURL.setText( webApp.getUrl() );

            if(webApp.getHttpMethod().equals("GET"))
                spinnerHttpMethod.setSelection(0);
            else
                spinnerHttpMethod.setSelection(1);

            spinnerPeriod.setSelection( PeriodConverter.getIndexFromValue(webApp.getCheckInPeriod()) );
        }
        else{
            spinnerPeriod.setSelection(0);
            spinnerHttpMethod.setSelection(0);
        }

    }

    private void initSpinnerHttpMethod() {
        String[] spinnerArray = new String[2];

        spinnerArray[0] = "GET";
        spinnerArray[1] = "HEAD";

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHttpMethod.setAdapter(adapter);
    }

    private void initSpinnerPeriod() {
        String[] spinnerArray;

        spinnerArray = PeriodConverter.getArrayValues();
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriod.setAdapter(adapter);
    }

    public void save(){
        boolean isInsert = false;

        if(webApp == null){
            webApp = new WebApp();
            isInsert = true;
        }
        WebAppDAO dao = new WebAppDAO(this);

        webApp.setName( editTextName.getText().toString() );
        webApp.setUrl( editTextURL.getText().toString() );

        webApp.setHttpMethod( spinnerHttpMethod.getSelectedItem().toString() );
        webApp.setCheckInPeriod( PeriodConverter.getValueFromIndex( spinnerPeriod.getSelectedItemPosition() ) );

        dao.open();
        if(isInsert) {
            webApp.setId( (int)dao.insert(webApp) );
        }
        else {
            dao.update(webApp);
            Intent it = new Intent();
            it.putExtra(OUT_WEB_APP, webApp);
            setResult(RESULT_EDIT_OK,it);
        }

        if(webApp.getCheckInPeriod() == 0){
            SchedulerCheck.getScheduler().cancelScheduleWebApp(this,webApp.getId());
        }else if(Preferences.getPreferences(this).getMonitorStatus()){
            SchedulerCheck.getScheduler().scheduleWebApps(this,webApp);
        }

        dao.close();

        finish();
    }
}
