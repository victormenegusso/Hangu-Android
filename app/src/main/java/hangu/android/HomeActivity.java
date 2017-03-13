package hangu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import hangu.android.entity.HanguSocket;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openWebApp(View view) {
        Intent intent = new Intent(this, WebAppActivity.class);
        startActivity(intent);
    }

    public void openListWebApps(View view) {
        Intent intent = new Intent(this, ListWebAppsActivity.class);
        startActivity(intent);
    }

    public void openServerApp(View view) {
        //Intent intent = new Intent(this, ServerAppActivity.class);
        //startActivity(intent);
    }

    public void openListServerApps(View view) {
        Intent intent = new Intent(this, ServerAppActivity.class);
        startActivity(intent);
    }


    public void openHanguSocket(View view) {
        Intent intent = new Intent(this, HanguSocketActivity.class);
        startActivity(intent);
    }

    public void openListHanguSockets(View view) {
        //Intent intent = new Intent(this, ListWebAppsActivity.class);
        //startActivity(intent);
    }
}
