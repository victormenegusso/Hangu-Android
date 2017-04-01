package hangu.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import hangu.android.entity.WebApp;

/**
 * Created by Victor Menegusso on 07/03/17.
 */
public class ListWebAppsAdapter extends RecyclerView.Adapter {

    private List<WebApp> webApps;
    private Context context;

    public ListWebAppsAdapter(List<WebApp> webApps, Context context) {
        this.webApps = webApps;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_web_apps_adapter, parent, false);

        ListWebAppsViewHolder holder = new ListWebAppsViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ListWebAppsViewHolder holder = (ListWebAppsViewHolder) viewHolder;
        WebApp webApp = webApps.get(position);

        //holder.nome.setText(webApp.getName());
        //holder.url.setText(webApp.getUrl());
        //holder.httpMethod.setText(webApp.getHttpMethod());
        //holder.status.setText(webApp.getStatus().toString());
        //holder.context = context;
        //holder.webApp = webApp;
        holder.setHolder(context,webApp);
    }

    @Override
    public int getItemCount() {
        return webApps.size();
    }

    public void update(WebApp webApp){
        int i;
        for(i = 0; i < webApps.size(); i++) {
            if (webApps.get(i).getId() == webApp.getId()) {
                webApps.remove(i);
                break;
            }
        }
        webApps.add(i,webApp);
    }


    public void remove(int id){
        for(int i = 0; i < webApps.size(); i++) {
            if (webApps.get(i).getId() == id) {
                webApps.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

}
