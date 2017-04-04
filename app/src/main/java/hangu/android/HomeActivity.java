package hangu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import hangu.android.core.SchedulerCheck;
import hangu.android.dao.Preferences;

public class HomeActivity extends AppCompatActivity {

    private Switch switchMonitorStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        switchMonitorStatus = (Switch)findViewById(R.id.switch_monitorStatus);

        switchMonitorStatus.setChecked(Preferences.getPreferences(this).getMonitorStatus());
        switchMonitorStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Preferences.getPreferences(getApplicationContext()).setMonitorStatus(true);
                    SchedulerCheck.getScheduler().scheduleAll(getApplicationContext());
                }else{
                    Preferences.getPreferences(getApplicationContext()).setMonitorStatus(false);
                    SchedulerCheck.getScheduler().cancelAllSchedule(getApplicationContext());
                }
            }
        });
    }

    public void openWebApp(View view) {
        Intent intent = new Intent(this, PersistWebAppActivity.class);
        startActivity(intent);
    }

    public void openListWebApps(View view) {
        Intent intent = new Intent(this, ListWebAppsActivity.class);
        startActivity(intent);
    }

    public void openServerApp(View view) {
        Intent intent = new Intent(this, PersistServerAppActivity.class);
        startActivity(intent);
    }

    public void openListServerApps(View view) {
        Intent intent = new Intent(this, ListServerAppsActivity.class);
        startActivity(intent);
    }


    public void openHanguSocket(View view) {
        Intent intent = new Intent(this, PersistHanguSocketActivity.class);
        startActivity(intent);
    }

    public void openListHanguSockets(View view) {
        Intent intent = new Intent(this, ListHanguSocketsActivity.class);
        startActivity(intent);
    }
}
