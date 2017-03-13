package hangu.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import hangu.android.entity.HanguSocket;

/**
 * Created by Victor Menegusso on 13/03/17.
 */

public class ListHanguSocketsAdapter extends RecyclerView.Adapter {

    private List<HanguSocket> hanguSockets;
    private Context context;

    public ListHanguSocketsAdapter(List<HanguSocket> hanguSockets, Context context) {
        this.hanguSockets = hanguSockets;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.activity_list_hangu_sockets_adapter, parent, false);

        ListHanguSocketsViewHolder holder = new ListHanguSocketsViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,
                                 int position) {
        ListHanguSocketsViewHolder holder = (ListHanguSocketsViewHolder) viewHolder;
        HanguSocket hanguSocket = hanguSockets.get(position);

        holder.host.setText(hanguSocket.getHost());
        holder.port.setText(Integer.toString(hanguSocket.getPort()));
    }

    @Override
    public int getItemCount() {
        return hanguSockets.size();
    }
}