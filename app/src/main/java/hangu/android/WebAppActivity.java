package hangu.android;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import hangu.android.dao.WebAppDAO;
import hangu.android.entity.WebApp;

public class WebAppActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextURL;
    private EditText editTextHttpMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_app);

        editTextName = (EditText) findViewById( R.id.editText_name );
        editTextURL = (EditText) findViewById( R.id.editText_url );
        editTextHttpMethod = (EditText) findViewById( R.id.editText_http_method );
    }

    public void save(View v){
        WebApp webApp = new WebApp();
        WebAppDAO dao = new WebAppDAO(this);

        webApp.setName( editTextName.getText().toString() );
        webApp.setUrl( editTextURL.getText().toString() );
        webApp.setHttpMethod( editTextHttpMethod.getText().toString() );


        dao.open();
        dao.insert(webApp);
        dao.close();
        
    }
}
