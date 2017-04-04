package hangu.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;
import hangu.android.dao.ServerAppDAO;
import hangu.android.entity.ServerApp;
import hangu.android.entity.Status;
import hangu.android.service.HttpConnector;

public class ListServerAppsActivity extends AppCompatActivity {

    public final int REQUEST_UPDATE_LIST = 1;

    private InnerReceiver receiver = null;

    private ListServerAppsAdapter listServerAppsAdapter;
    private RecyclerView recyclerView;
    private List<ServerApp> serverApps;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_server_apps);

        receiver = new InnerReceiver();

        bindInterface();
        loadInterface();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(HttpConnector.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_UPDATE_LIST && resultCode == ServerAppActivity.RESULT_DELETE_OK){
            int id = data.getIntExtra(ServerAppActivity.OUT_SERVER_APP_ID,-1);
            listServerAppsAdapter.remove(id);
        }
        else if(requestCode == REQUEST_UPDATE_LIST && resultCode == ServerAppActivity.RESULT_EDIT_OK){
            ServerApp serverApp = (ServerApp) data.getSerializableExtra(ServerAppActivity.OUT_SERVER_APP);
            listServerAppsAdapter.update(serverApp);
        }
        else if(requestCode == REQUEST_UPDATE_LIST && resultCode == 0){ // backbutton
            loadInterface();
        }
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

        for( ServerApp serverApp : serverApps){
            Intent intent = new Intent(this, HttpConnector.class);
            intent.putExtra(HttpConnector.IN_URL,serverApp.getUrl());
            startService(intent);
        }
    }

    private class InnerReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            String url;
            boolean isCon;

            url = intent.getStringExtra(HttpConnector.OUT_URL);
            isCon = intent.getBooleanExtra(HttpConnector.OUT_ISCONNECTED,false);

            for(ServerApp serverApp : serverApps){
                if(serverApp.getUrl().equals(url)){

                    if(isCon)
                        serverApp.setStatus(Status.ONLINE);
                    else
                        serverApp.setStatus(Status.OFFLINE);

                    listServerAppsAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }
}