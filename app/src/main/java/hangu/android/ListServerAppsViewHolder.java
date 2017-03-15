package hangu.android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import hangu.android.entity.ServerApp;

/**
 * Created by Victor Menegusso on 14/03/17.
 */

public class ListServerAppsViewHolder extends RecyclerView.ViewHolder{

    final TextView name;
    final TextView url;
    public ServerApp serverApp;
    public Context context;

    public ListServerAppsViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.textView_name);
        url = (TextView) view.findViewById(R.id.textView_url);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("asd","itemView");
                Log.d("asd",""+serverApp.getId());
                Log.d("asd",serverApp.getName());
                Log.d("asd",serverApp.getPathProcessStart());
                Log.d("asd",serverApp.getPathProcessStop());
                Log.d("asd",serverApp.getUrl());

                Intent intent = new Intent(context,ServerAppActivity.class);
                context.startActivity(intent);
            }
        });
    }
}