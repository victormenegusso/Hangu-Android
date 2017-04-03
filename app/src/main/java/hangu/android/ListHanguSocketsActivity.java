package hangu.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;
import hangu.android.dao.HanguSocketDAO;
import hangu.android.entity.HanguSocket;

public class ListHanguSocketsActivity extends AppCompatActivity {

    private ListHanguSocketsAdapter listHanguSocketsAdapter;
    private List<HanguSocket> hanguSockets;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hangu_sockets);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
