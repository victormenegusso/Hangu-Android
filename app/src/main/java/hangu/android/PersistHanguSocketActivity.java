package hangu.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import hangu.android.dao.HanguSocketDAO;
import hangu.android.entity.HanguSocket;

public class PersistHanguSocketActivity extends AppCompatActivity {

    public static final String IN_HANGU_SOCKET = "IN_HANGU_SOCKET";

    private EditText editTextHost;
    private EditText editTextPort;
    private HanguSocket hanguSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_hangu_socket);

        bindInterface();
        getExtras();
        loadInterface();
    }

    private void getExtras(){
        hanguSocket = (HanguSocket) getIntent().getSerializableExtra(IN_HANGU_SOCKET);
    }

    private void bindInterface(){
        editTextHost = (EditText) findViewById( R.id.editText_host );
        editTextPort = (EditText) findViewById( R.id.editText_port );
    }

    private void loadInterface(){
        if(hanguSocket != null) {
            editTextHost.setText(hanguSocket.getHost());
            editTextPort.setText(Integer.toString(hanguSocket.getPort()));
        }
    }

    private void a(){
        setResult(RESULT_OK);
        finish();
    }


    public void save(View v){
        HanguSocketDAO dao = new HanguSocketDAO(this);

        boolean isInsert = false;
        if(hanguSocket == null) {
            hanguSocket = new HanguSocket();
            isInsert = true;
        }

        hanguSocket.setHost( editTextHost.getText().toString() );
        hanguSocket.setPort( Integer.parseInt( editTextPort.getText().toString() ) );


        dao.open();
        Log.d("--",""+isInsert);
        if(isInsert)
            dao.insert(hanguSocket);
        else
            dao.update(hanguSocket);
        dao.close();

        a();
    }
}
