package hangu.android;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hangu.android.dao.WebAppDAO;
import hangu.android.entity.WebApp;

public class ListWebAppsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_web_apps);

        //ListView  webAppsView = (ListView) findViewById(R.id.lista);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        WebAppDAO dao = new WebAppDAO();
        List<WebApp> webApps = dao.list();
        recyclerView.setAdapter(new ListWebAppsAdapter(webApps,this));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);


        //ArrayAdapter<WebApp> adapter = new ArrayAdapter<WebApp>(this,
        //        android.R.layout.simple_list_item_1, webApps);
        // MyAdapter adapter = new MyAdapter(webApps,this);

        //webAppsView.setAdapter(adapter);


    }
    public void btnClick(View v){
        Log.d("aa","xxxx");

        Log.d("aa",v.getClass().toString());
    }


    /*
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_web_apps);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        List<WebApp> webApps = new ArrayList<>();
        webApps.add( new WebApp("globo","www.globo.com","get") );
        webApps.add( new WebApp("google","www.google.com","get") );
        webApps.add( new WebApp("facebook","www.facebook.com","get") );

        recyclerView.setAdapter(new MyAdapter(webApps,this));
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_web_apps);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        String[] vet = {"aa","bbb"};
        mAdapter = new MyAdapter(vet);
        mRecyclerView.setAdapter(mAdapter);
    }
    */
}
