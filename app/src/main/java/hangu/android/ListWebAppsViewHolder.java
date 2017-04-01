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

import hangu.android.R;
import hangu.android.entity.Status;
import hangu.android.entity.WebApp;

/**
 * Created by Victor Menegusso on 07/03/17.
 */

public class ListWebAppsViewHolder extends RecyclerView.ViewHolder {

    private final TextView nome;
    private final TextView url;
    //final TextView httpMethod;
    //final TextView status;
    private final ImageView status;
    private WebApp webApp;
    private Context context;

    public ListWebAppsViewHolder(View view) {
        super(view);
        nome = (TextView) view.findViewById(R.id.webapps_name);

        url = (TextView) view.findViewById(R.id.webapps_url);
        //httpMethod = (TextView) view.findViewById(R.id.webapps_httpmethod);
        //status = (TextView) view.findViewById(R.id.webapps_status);
        status = (ImageView) view.findViewById(R.id.imageView_status);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebAppActivity.class);
                intent.putExtra(WebAppActivity.IN_WEB_APP, (Serializable) webApp);
                //context.startActivity(intent);
                ((Activity) context).startActivityForResult(intent,ListWebAppsActivity.REQUEST_UPDATE_LIST);
            }
        });
    }

    public void setHolder(Context context, WebApp webApp ){
        this.context = context;
        this.webApp = webApp;

        this.nome.setText( webApp.getName() );
        this.url.setText( webApp.getUrl() );

        // status
        if(webApp.getStatus() == Status.WAIT_CONNECTION)
            status.setImageResource(android.R.drawable.presence_away);
        else if(webApp.getStatus() == Status.OFFLINE)
            status.setImageResource(android.R.drawable.presence_offline);
        else if(webApp.getStatus() == Status.ONLINE)
            status.setImageResource(android.R.drawable.presence_online);
    }
}
