package hangu.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import hangu.android.dao.WebAppDAO;
import hangu.android.entity.HanguSocket;
import hangu.android.entity.WebApp;

public class HanguSocketActivity extends AppCompatActivity {

    private EditText editTextHost;
    private EditText editTextPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangu_socket);

        editTextHost = (EditText) findViewById( R.id.editText_host );
        editTextPort = (EditText) findViewById( R.id.editText_port );
    }

    public void save(View v){
        HanguSocket hanguSocket = new HanguSocket();
        HanguSocketDAO dao = new HanguSocketDAO(this);

        webApp.setName( editTextName.getText().toString() );
        webApp.setUrl( editTextURL.getText().toString() );
        webApp.setHttpMethod( editTextHttpMethod.getText().toString() );


        dao.open();
        dao.insert(webApp);
        dao.close();

    }
}
