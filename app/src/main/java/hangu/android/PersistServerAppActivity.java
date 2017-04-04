package hangu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hangu.android.core.SchedulerCheck;
import hangu.android.dao.HanguSocketDAO;
import hangu.android.dao.Preferences;
import hangu.android.dao.ServerAppDAO;
import hangu.android.entity.HanguSocket;
import hangu.android.entity.ServerApp;
import hangu.android.hangu.android.view.converter.PeriodConverter;

public class PersistServerAppActivity extends AppCompatActivity {

    public static final String IN_SERVER_APP = "IN_SERVER_APP";
    public static final String OUT_SERVER_APP = "OUT_SERVER_APP";

    public static final int RESULT_EDIT_OK = 2;

    private EditText editTextName;
    private EditText editTextURL;
    private EditText editTextLogPath;
    private EditText editTextScriptStart;
    private EditText editTextScriptStop;
    private Spinner spinnerHanguSocket;
    private Spinner spinnerPeriod;

    private ServerApp serverApp;

    private Map<Integer, Integer> spinnerMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_server_app);

        bindInterface();
        getExtras();
        loadInterface();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_persist_server_app, menu);
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
        serverApp = (ServerApp) getIntent().getSerializableExtra(IN_SERVER_APP);
    }

    private void bindInterface(){
        editTextName = (EditText) findViewById(R.id.editText_name);
        editTextURL = (EditText) findViewById(R.id.editText_url);
        editTextLogPath = (EditText) findViewById(R.id.editText_log_path);
        editTextScriptStart = (EditText) findViewById(R.id.editText_scriptStart);
        editTextScriptStop = (EditText) findViewById(R.id.editText_scriptStop);
        spinnerHanguSocket = (Spinner) findViewById(R.id.spinner_hanguSocket);
        spinnerPeriod = (Spinner) findViewById( R.id.spinner_period );
    }

    private void loadInterface(){

        initSpinnerPeriod();
        initSpinnerHanguSocket();

        if(serverApp != null) {
            editTextName.setText(serverApp.getName());
            editTextURL.setText(serverApp.getUrl());
            editTextLogPath.setText(serverApp.getPathFileLog());
            editTextScriptStart.setText(serverApp.getPathProcessStart());
            editTextScriptStop.setText(serverApp.getPathProcessStop());

            spinnerPeriod.setSelection( PeriodConverter.getIndexFromValue(serverApp.getCheckInPeriod()) );
        }
        else{
            spinnerPeriod.setSelection(0);
        }
    }

    private void initSpinnerPeriod() {
        String[] spinnerArray;

        spinnerArray = PeriodConverter.getArrayValues();
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriod.setAdapter(adapter);
    }

    private void initSpinnerHanguSocket() {

        HanguSocketDAO dao = new HanguSocketDAO(this);
        dao.open();
        List<HanguSocket> hanguSockets = dao.list();
        dao.close();

        String[] spinnerArray = new String[hanguSockets.size()];
        spinnerMap = new HashMap<>();
        for (int i = 0; i < hanguSockets.size(); i++)
        {
            spinnerMap.put(i,hanguSockets.get(i).getId());
            spinnerArray[i] = hanguSockets.get(i).getHost();
        }

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHanguSocket.setAdapter(adapter);
    }

    public void save(){
        ServerAppDAO dao = new ServerAppDAO(this);
        boolean isInsert = false;

        if(serverApp == null){
            serverApp = new ServerApp();
            isInsert = true;
        }

        serverApp.setName( editTextName.getText().toString() );
        serverApp.setUrl( editTextURL.getText().toString() );
        serverApp.setPathFileLog( editTextLogPath.getText().toString() );
        serverApp.setPathProcessStart( editTextScriptStart.getText().toString() );
        serverApp.setPathProcessStop( editTextScriptStop.getText().toString() );

        serverApp.setCheckInPeriod( PeriodConverter.getValueFromIndex( spinnerPeriod.getSelectedItemPosition() ) );

        serverApp.setHanguSocket( new HanguSocket() );
        serverApp.getHanguSocket().setId( spinnerMap.get( spinnerHanguSocket.getSelectedItemPosition() ) );
        
        dao.open();
        if(isInsert)
            dao.insert(serverApp);
        else{
            dao.update(serverApp);
            Intent it = new Intent();
            it.putExtra(OUT_SERVER_APP, serverApp);
            setResult(RESULT_EDIT_OK,it);
        }

        if(serverApp.getCheckInPeriod() == 0){
            SchedulerCheck.getScheduler().cancelScheduleServerApp(this,serverApp.getId());
        }else if(Preferences.getPreferences(this).getMonitorStatus()){
            SchedulerCheck.getScheduler().scheduleServerApps(this,serverApp);
        }

        dao.close();

        finish();
    }
}
