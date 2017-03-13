package hangu.android;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by victor on 13/03/17.
 */

public class ListHanguSocketsViewHolder extends RecyclerView.ViewHolder {

    final TextView host;
    final TextView port;

    public ListHanguSocketsViewHolder(View view) {
        super(view);
        host = (TextView) view.findViewById(R.id.textView_host);
        port = (TextView) view.findViewById(R.id.textView_port);

    }
}