package hangu.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import hangu.android.R;
import hangu.android.entity.WebApp;

/**
 * Created by Victor Menegusso on 07/03/17.
 */

public class ListWebAppsViewHolder extends RecyclerView.ViewHolder {

    final TextView nome;
    final TextView url;
    final TextView httpMethod;
    final TextView status;
    public WebApp webApp;
    public Context context;

    public ListWebAppsViewHolder(View view) {
        super(view);
        nome = (TextView) view.findViewById(R.id.webapps_name);

        url = (TextView) view.findViewById(R.id.webapps_url);
        httpMethod = (TextView) view.findViewById(R.id.webapps_httpmethod);
        status = (TextView) view.findViewById(R.id.webapps_status);

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
}
