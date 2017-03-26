package hangu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;

import java.util.List;

import hangu.android.dao.HanguSocketDAO;
import hangu.android.dao.ServerAppDAO;
import hangu.android.entity.HanguSocket;
import hangu.android.entity.ServerApp;
import hangu.android.entity.WebApp;

public class ListServerAppsActivity extends AppCompatActivity {

    public final int REQUEST_UPDATE_LIST = 1;

    private ListServerAppsAdapter listServerAppsAdapter;
    private List<ServerApp> serverApps;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_server_apps);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        ServerAppDAO dao = new ServerAppDAO(this);
        dao.open();
        serverApps = dao.list();
        dao.close();

        listServerAppsAdapter = new ListServerAppsAdapter(serverApps,this);
        recyclerView.setAdapter(listServerAppsAdapter);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_UPDATE_LIST){
        }
    }
}
