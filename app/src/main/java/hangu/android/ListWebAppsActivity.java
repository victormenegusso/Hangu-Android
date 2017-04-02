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
import hangu.android.dao.WebAppDAO;
import hangu.android.entity.Status;
import hangu.android.entity.WebApp;
import hangu.android.service.HttpConnector;

/**
 * Created by Victor Menegusso on 08/03/17.
 */
public class ListWebAppsActivity extends AppCompatActivity {

    public static final int REQUEST_UPDATE_LIST = 1;

    private InnerReceiver receiver = null;
    private ListWebAppsAdapter listWebAppsAdapter;
    private RecyclerView recyclerView;
    private List<WebApp> webApps;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_web_apps);

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
        if(requestCode == REQUEST_UPDATE_LIST && resultCode == WebAppActivity.RESULT_DELETE_OK){
            int id = data.getIntExtra(WebAppActivity.OUT_WEB_APP_ID,-1);
            listWebAppsAdapter.remove(id);
        }
        else if(requestCode == REQUEST_UPDATE_LIST && resultCode == WebAppActivity.RESULT_EDIT_OK){
            WebApp webApp = (WebApp) data.getSerializableExtra(WebAppActivity.OUT_WEB_APP);
            listWebAppsAdapter.update(webApp);
        }
        else if(requestCode == REQUEST_UPDATE_LIST && resultCode == 0){ // backbutton
            loadInterface();
        }
    }

    private void bindInterface(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    private void loadInterface(){
        WebAppDAO dao = new WebAppDAO(this);
        dao.open();
        webApps = dao.list();
        dao.close();

        listWebAppsAdapter = new ListWebAppsAdapter(webApps,this);
        recyclerView.setAdapter(listWebAppsAdapter);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        for( WebApp webApp : webApps){
            Intent intent = new Intent(this, HttpConnector.class);
            intent.putExtra(HttpConnector.IN_URL,webApp.getUrl());
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

            for(WebApp webApp : webApps){
                if(webApp.getUrl().equals(url)){

                    if(isCon)
                        webApp.setStatus(Status.ONLINE);
                    else
                        webApp.setStatus(Status.OFFLINE);

                    listWebAppsAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }
}
