package hangu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;
import hangu.android.dao.HanguSocketDAO;
import hangu.android.entity.HanguSocket;

public class ListHanguSocketsActivity extends AppCompatActivity {

    public static final int REQUEST_UPDATE_LIST = 1;

    private RecyclerView recyclerView;
    private ListHanguSocketsAdapter listHanguSocketsAdapter;
    private List<HanguSocket> hanguSockets;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hangu_sockets);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_UPDATE_LIST && resultCode == WebAppActivity.RESULT_DELETE_OK){
            int id = data.getIntExtra(WebAppActivity.OUT_WEB_APP_ID,-1);
            listHanguSocketsAdapter.remove(id);
        }
        else if(requestCode == REQUEST_UPDATE_LIST && resultCode == WebAppActivity.RESULT_EDIT_OK){
            HanguSocket serverApp = (HanguSocket) data.getSerializableExtra(WebAppActivity.OUT_WEB_APP);
            listHanguSocketsAdapter.update(serverApp);
        }
        else if(requestCode == REQUEST_UPDATE_LIST && resultCode == 0){ // backbutton
            loadInterface();
        }
    }

    private void bindInterface(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    private void loadInterface(){

        HanguSocketDAO dao = new HanguSocketDAO(this);
        dao.open();
        hanguSockets = dao.list();
        dao.close();

        listHanguSocketsAdapter = new ListHanguSocketsAdapter(hanguSockets,this);
        recyclerView.setAdapter(listHanguSocketsAdapter);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

    }
}
