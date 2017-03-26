package hangu.android;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import hangu.android.dao.WebAppDAO;
import hangu.android.entity.WebApp;

public class WebAppActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextURL;
    private Spinner spinnerHttpMethod;
    private Spinner spinnerPeriod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_app);
        bindInterface();
        loadInterface();
    }

    private void bindInterface(){
        editTextName = (EditText) findViewById( R.id.editText_name );
        editTextURL = (EditText) findViewById( R.id.editText_url );
        spinnerHttpMethod = (Spinner) findViewById( R.id.editText_http_method );
        spinnerPeriod = (Spinner) findViewById( R.id.spinner_period );
    }

    private void loadInterface(){
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
        WebApp webApp = new WebApp();
        WebAppDAO dao = new WebAppDAO(this);

        webApp.setName( editTextName.getText().toString() );
        webApp.setUrl( editTextURL.getText().toString() );

        webApp.setHttpMethod( spinnerHttpMethod.getSelectedItem().toString() );
        webApp.setCheckInPeriod( getSpinnerPeriodValue() );

        dao.open();
        dao.insert(webApp);
        dao.close();


        finish();
    }
}
