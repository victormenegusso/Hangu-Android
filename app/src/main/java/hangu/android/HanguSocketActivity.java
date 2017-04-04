package hangu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import hangu.android.entity.HanguSocket;

public class HanguSocketActivity extends AppCompatActivity {

    public static final String IN_HANGU_SOCKET = "IN_HANGU_SOCKET";
    public static final int RESULT_EDIT_OK = 3;
    public static final int REQUEST_EDIT = 1;

    private TextView textView_host;
    private TextView textView_port;

    private boolean hasEdit;
    private HanguSocket hanguSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangu_socket);

        bindInterface();
        getExtras();
        loadInterface();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hangu_socket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_edit:
                edit();
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_EDIT && resultCode == PersistWebAppActivity.RESULT_EDIT_OK){
            hasEdit = true;

            hanguSocket = (HanguSocket) data.getSerializableExtra( PersistHanguSocketActivity.OUT_HANGU_SOCKET );
            loadInterface();
        }
    }

    private void getExtras(){
        hanguSocket = (HanguSocket) getIntent().getSerializableExtra(IN_HANGU_SOCKET);
    }

    private void bindInterface(){
        textView_host = (TextView)findViewById( R.id.textView_host );
        textView_port = (TextView)findViewById( R.id.textView_port );
    }

    private void loadInterface(){
        textView_host.setText( hanguSocket.getHost() );
        textView_port.setText( Integer.toString( hanguSocket.getPort() ) );
    }

    public void edit(){
        Intent intent = new Intent(this,PersistHanguSocketActivity.class);
        intent.putExtra(PersistHanguSocketActivity.IN_HANGU_SOCKET,(Serializable) hanguSocket);
        startActivityForResult(intent,REQUEST_EDIT);

    }
}
