package hangu.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import hangu.android.dao.HanguSocketDAO;
import hangu.android.dao.ServerAppDAO;
import hangu.android.entity.HanguSocket;
import hangu.android.entity.ServerApp;

public class ListServerAppsActivity extends AppCompatActivity {

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
}
