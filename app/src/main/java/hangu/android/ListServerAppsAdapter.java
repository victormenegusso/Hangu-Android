package hangu.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import hangu.android.entity.ServerApp;

/**
 * Created by Victor Menegusso on 14/03/17.
 */

public class ListServerAppsAdapter extends RecyclerView.Adapter {

    private List<ServerApp> serverApps;
    private Context context;

    public ListServerAppsAdapter(List<ServerApp> serverApps, Context context) {
        this.serverApps = serverApps;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_server_apps_adapter, parent, false);
        ListServerAppsViewHolder holder = new ListServerAppsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ListServerAppsViewHolder holder = (ListServerAppsViewHolder) viewHolder;
        ServerApp serverApp = serverApps.get(position);
        holder.setHolder(context,serverApp);
    }

    @Override
    public int getItemCount() {
        return serverApps.size();
    }

    public void update(ServerApp serverApp){
        int i;
        for(i = 0; i < serverApps.size(); i++) {
            if (serverApps.get(i).getId() == serverApp.getId()) {
                serverApps.remove(i);
                break;
            }
        }
        serverApps.add(i,serverApp);
    }

    public void remove(int id){
        for(int i = 0; i < serverApps.size(); i++) {
            if (serverApps.get(i).getId() == id) {
                serverApps.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

}