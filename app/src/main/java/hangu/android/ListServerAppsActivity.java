package hangu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;
import hangu.android.dao.ServerAppDAO;
import hangu.android.entity.ServerApp;

public class ListServerAppsActivity extends AppCompatActivity {

    public final int REQUEST_UPDATE_LIST = 1;

    private ListServerAppsAdapter listServerAppsAdapter;
    private RecyclerView recyclerView;
    private List<ServerApp> serverApps;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_server_apps);

        bindInterface();
        loadInterface();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void bindInterface(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    private void loadInterface(){
        ServerAppDAO dao = new ServerAppDAO(this);
        dao.open();
        serverApps = dao.list();
        dao.close();

        listServerAppsAdapter = new ListServerAppsAdapter(serverApps,this);
        recyclerView.setAdapter(listServerAppsAdapter);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_UPDATE_LIST){
        }
    }
}
