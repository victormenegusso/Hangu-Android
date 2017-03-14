package hangu.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import hangu.android.entity.ServerApp;

/**
 * Created by Victor Menegusso on 14/03/17.
 */

public class ListServerAppsAdapter   extends RecyclerView.Adapter {

    private List<ServerApp> serverApps;
    private Context context;

    public ListServerAppsAdapter(List<ServerApp> serverApps, Context context) {
        this.serverApps = serverApps;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.activity_list_server_apps_adapter, parent, false);

        ListServerAppsViewHolder holder = new ListServerAppsViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,
                                 int position) {
        ListServerAppsViewHolder holder = (ListServerAppsViewHolder) viewHolder;
        ServerApp serverApp = serverApps.get(position);

        holder.name.setText(serverApp.getName());
        holder.url.setText(serverApp.getUrl());
    }

    @Override
    public int getItemCount() {
        return serverApps.size();
    }
}

