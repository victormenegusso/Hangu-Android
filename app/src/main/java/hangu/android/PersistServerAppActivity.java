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

    private EditText editTextName;
    private EditText editTextURL;
    private EditText editTextScriptStart;
    private EditText editTextScriptStop;
    private Spinner spinnerHanguSocket;

    private Map<Integer, Integer> spinnerMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_server_app);

        editTextName = (EditText) findViewById(R.id.editText_name);
        editTextURL = (EditText) findViewById(R.id.editText_url);
        editTextScriptStart = (EditText) findViewById(R.id.editText_scriptStart);
        editTextScriptStop = (EditText) findViewById(R.id.editText_scriptStop);
        spinnerHanguSocket = (Spinner) findViewById(R.id.spinner_hanguSocket);

        initSpinnerHanguSocket();
    }

    public void initSpinnerHanguSocket() {

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
        ServerApp server = new ServerApp();
        ServerAppDAO dao = new ServerAppDAO(this);

        server.setName( editTextName.getText().toString() );
        server.setUrl( editTextURL.getText().toString() );
        server.setPathProcessStart( editTextScriptStart.getText().toString() );
        server.setPathProcessStop( editTextScriptStop.getText().toString() );

        server.setHanguSocket( new HanguSocket() );
        server.getHanguSocket().setId( spinnerMap.get( spinnerHanguSocket.getSelectedItemPosition() ) );
        
        dao.open();
        dao.insert(server);
        dao.close();

    }
}
