package hangu.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import hangu.android.entity.WebApp;

/**
 * Created by victor on 07/03/17.
 */

//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

public class ListWebAppsAdapter extends RecyclerView.Adapter {

    private List<WebApp> webApps;
    private Context context;

    public ListWebAppsAdapter(List<WebApp> webApps, Context context) {
        this.webApps = webApps;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        //return null;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter, parent, false);

        ListWebAppsViewHolder holder = new ListWebAppsViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,
                                 int position) {
        ListWebAppsViewHolder holder = (ListWebAppsViewHolder) viewHolder;

        WebApp webApp = webApps.get(position);

        holder.nome.setText(webApp.getName());
        holder.url.setText(webApp.getUrl());
        holder.httpMethod.setText(webApp.getHttpMethod());
    }

    @Override
    public int getItemCount() {
        return webApps.size();
    }
    public void btnClick(View v){
        Log.d("aa","ccc");
    }
}
    /*
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<WebApp> webApps) {
        webApps = webApps;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_web_apps, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(webApps.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return webApps.size();
    }

}
/*
public class MyAdapter extends BaseAdapter {

    private List<WebApp> webApps;
    private Activity activity;

    public MyAdapter(List<WebApp> webApps, Activity activity ) {
        this.webApps = webApps;
        this.activity = activity;

    }

    @Override
    public int getCount() {
        return webApps.size();
    }

    @Override
    public Object getItem(int position) {
        return webApps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        ListWebAppsViewHolder holder;
        if( convertView == null) {
            view = LayoutInflater.from(activity)
                    .inflate(R.layout.adapter, parent, false);
            holder = new ListWebAppsViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ListWebAppsViewHolder) view.getTag();
        }

        WebApp webApp = webApps.get(position);
        holder.nome.setText(webApp.getName());
        holder.url.setText(webApp.getUrl());
        holder.httpMethod.setText(webApp.getHttpMethod());
        /*
        View view = activity.getLayoutInflater()
                .inflate(R.layout.adapter, parent, false);

        WebApp webApp = webApps.get(position);

        TextView nome = (TextView)
                view.findViewById(R.id.webapps_nome);


        TextView url = (TextView)
                view.findViewById(R.id.webapps_nome_url);

        TextView httpMethod = (TextView)
                view.findViewById(R.id.webapps_nome_httpmethod);


        nome.setText(webApp.getName());
        url.setText(webApp.getUrl());
        httpMethod.setText(webApp.getHttpMethod());
        */
        /*return view;
    }
}
*/
/*
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
*/
//public class MyAdapter extends RecyclerView.Adapter {
    /*
    private List<WebApp> webApps;
    private Context context;

    public MyAdapter(List<WebApp> webApps, Context context ) {
        this.webApps = webApps;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        //return null;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_livro, parent, false);

        ListWebAppsViewHolder holder = new ListWebAppsViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,
                                 int position) {
        ListWebAppsViewHolder holder = (ListWebAppsViewHolder) viewHolder;

        WebApp webApp  = webApps.get(position) ;

        holder.nome.setText(webApp.getName());
    }

    @Override
    public int getItemCount() {
        return webApps.size();
    }

    /*
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<WebApp> webApps) {
        webApps = webApps;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_web_apps, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(webApps.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return webApps.size();
    }
    */
//}


