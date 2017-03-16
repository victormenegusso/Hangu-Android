package hangu.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import hangu.android.entity.HanguSocket;
import hangu.android.entity.ServerApp;

/**
 * Created by victor on 13/03/17.
 */

public class ListHanguSocketsViewHolder extends RecyclerView.ViewHolder {

    final TextView host;
    final TextView port;
    public HanguSocket hanguSocket;
    public Context context;

    public ListHanguSocketsViewHolder(View view) {
        super(view);
        host = (TextView) view.findViewById(R.id.textView_host);
        port = (TextView) view.findViewById(R.id.textView_port);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HanguSocketActivity.class);
                intent.putExtra(HanguSocketActivity.IN_HANGU_SOCKET,(Serializable) hanguSocket);
                context.startActivity(intent);
            }
        });
    }
}