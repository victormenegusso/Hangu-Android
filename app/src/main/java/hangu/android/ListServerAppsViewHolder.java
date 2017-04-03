package hangu.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import hangu.android.entity.ServerApp;
import hangu.android.entity.Status;

/**
 * Created by Victor Menegusso on 14/03/17.
 */

public class ListServerAppsViewHolder extends RecyclerView.ViewHolder{

    private final TextView name;
    private final TextView url;
    private final ImageView status;
    private ServerApp serverApp;
    private Context context;

    public ListServerAppsViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.textView_name);
        url = (TextView) view.findViewById(R.id.textView_url);
        status = (ImageView) view.findViewById(R.id.imageView_status);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServerAppActivity.class);
                intent.putExtra(ServerAppActivity.IN_SERVER_APP, (Serializable) serverApp);
                context.startActivity(intent);
            }
        });
    }

    public void setHolder(Context context, ServerApp serverApp ){
        this.context = context;
        this.serverApp = serverApp;

        this.name.setText( serverApp.getName() );
        this.url.setText( serverApp.getUrl() );

        // status
        if(serverApp.getStatus() == Status.WAIT_CONNECTION)
            status.setImageResource(android.R.drawable.presence_away);
        else if(serverApp.getStatus() == Status.OFFLINE)
            status.setImageResource(android.R.drawable.presence_busy);
        else if(serverApp.getStatus() == Status.ONLINE)
            status.setImageResource(android.R.drawable.presence_online);
    }
}