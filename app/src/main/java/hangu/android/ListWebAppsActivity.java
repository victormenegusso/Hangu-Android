package hangu.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import java.util.List;
import hangu.android.dao.WebAppDAO;
import hangu.android.entity.Status;
import hangu.android.entity.WebApp;
import hangu.android.service.HttpConnector;

/**
 * Created by Victor Menegusso on 08/03/17.
 */
public class ListWebAppsActivity extends AppCompatActivity {

    private InnerReceiver receiver = null;
    private ListWebAppsAdapter listWebAppsAdapter;
    private List<WebApp> webApps;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_web_apps);

        //ListView  webAppsView = (ListView) findViewById(R.id.lista);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        WebAppDAO dao = new WebAppDAO(this);
        dao.open();
        webApps = dao.list();
        dao.close();

        listWebAppsAdapter = new ListWebAppsAdapter(webApps,this);
        recyclerView.setAdapter(listWebAppsAdapter);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);


        receiver = new InnerReceiver();
        for( WebApp webApp : webApps){
            Intent intent = new Intent(this, HttpConnector.class);
            intent.putExtra(HttpConnector.IN_URL,webApp.getUrl());
            startService(intent);
        }

        //ArrayAdapter<WebApp> activity_list_web_apps_adapter = new ArrayAdapter<WebApp>(this,
        //        android.R.layout.simple_list_item_1, webApps);
        // MyAdapter activity_list_web_apps_adapter = new MyAdapter(webApps,this);

        //webAppsView.setAdapter(activity_list_web_apps_adapter);


    }
    public void btnClick(View v){
        Log.d("aa","xxxx");

        Log.d("aa",v.getClass().toString());
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tail","onResume");

        IntentFilter filter = new IntentFilter(HttpConnector.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tail","onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
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
