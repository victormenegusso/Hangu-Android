package hangu.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hangu.android.dao.HanguSocketDAO;
import hangu.android.dao.ServerAppDAO;
import hangu.android.entity.HanguSocket;
import hangu.android.entity.ServerApp;

public class PersistServerAppActivity extends AppCompatActivity {
    public static final String IN_SERVER_APP = "IN_SERVER_APP";

    private EditText editTextName;
    private EditText editTextURL;
    private EditText editTextScriptStart;
    private EditText editTextScriptStop;
    private Spinner spinnerHanguSocket;

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

    private void getExtras(){
        serverApp = (ServerApp) getIntent().getSerializableExtra(IN_SERVER_APP);
    }

    private void bindInterface(){
        editTextName = (EditText) findViewById(R.id.editText_name);
        editTextURL = (EditText) findViewById(R.id.editText_url);
        editTextScriptStart = (EditText) findViewById(R.id.editText_scriptStart);
        editTextScriptStop = (EditText) findViewById(R.id.editText_scriptStop);
        spinnerHanguSocket = (Spinner) findViewById(R.id.spinner_hanguSocket);
    }

    private void loadInterface(){
        if(serverApp != null) {
            editTextName.setText(serverApp.getName());
            editTextURL.setText(serverApp.getName());
            editTextScriptStart.setText(serverApp.getName());
            editTextScriptStop.setText(serverApp.getName());
        }

        initSpinnerHanguSocket();
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

    public void save(View v){
        ServerAppDAO dao = new ServerAppDAO(this);
        boolean isInsert = false;

        if(serverApp == null){
            serverApp = new ServerApp();
            isInsert = true;
        }

        serverApp.setName( editTextName.getText().toString() );
        serverApp.setUrl( editTextURL.getText().toString() );
        serverApp.setPathProcessStart( editTextScriptStart.getText().toString() );
        serverApp.setPathProcessStop( editTextScriptStop.getText().toString() );

        serverApp.setHanguSocket( new HanguSocket() );
        serverApp.getHanguSocket().setId( spinnerMap.get( spinnerHanguSocket.getSelectedItemPosition() ) );
        
        dao.open();
        if(isInsert)
            dao.insert(serverApp);
        else
            dao.update(serverApp);
        dao.close();

    }
}
