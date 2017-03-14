package hangu.android;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Victor Menegusso on 14/03/17.
 */

public class ListServerAppsViewHolder extends RecyclerView.ViewHolder {

    final TextView name;
    final TextView url;

    public ListServerAppsViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.textView_name);
        url = (TextView) view.findViewById(R.id.textView_url);

    }
}