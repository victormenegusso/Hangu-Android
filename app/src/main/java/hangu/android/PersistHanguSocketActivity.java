package hangu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import hangu.android.dao.HanguSocketDAO;
import hangu.android.entity.HanguSocket;

public class PersistHanguSocketActivity extends AppCompatActivity {

    public static final String IN_HANGU_SOCKET = "IN_HANGU_SOCKET";
    public static final String OUT_HANGU_SOCKET = "OUT_HANGU_SOCKET";

    public static final int RESULT_EDIT_OK = 2;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_persist_hangu_socket, menu);
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

    public void save(){
        HanguSocketDAO dao = new HanguSocketDAO(this);

        boolean isInsert = false;
        if(hanguSocket == null) {
            hanguSocket = new HanguSocket();
            isInsert = true;
        }

        hanguSocket.setHost( editTextHost.getText().toString() );
        hanguSocket.setPort( Integer.parseInt( editTextPort.getText().toString() ) );


        dao.open();
        if(isInsert)
            dao.insert(hanguSocket);
        else {
            dao.update(hanguSocket);

            Intent it = new Intent();
            it.putExtra(OUT_HANGU_SOCKET, hanguSocket);
            setResult(RESULT_EDIT_OK,it);
        }
        dao.close();

        finish();
    }
}
