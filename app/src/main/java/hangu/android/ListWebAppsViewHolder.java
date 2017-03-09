package hangu.android;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import hangu.android.R;

/**
 * Created by victor menegusso on 07/03/17.
 */

public class ListWebAppsViewHolder extends RecyclerView.ViewHolder{

        final TextView nome;
        final TextView url;
        final TextView httpMethod;

        public ListWebAppsViewHolder(View view) {
            super(view);
            nome = (TextView)
                    view.findViewById(R.id.webapps_name);


            url = (TextView)
                    view.findViewById(R.id.webapps_url);

            httpMethod = (TextView)
                    view.findViewById(R.id.webapps_httpmethod);
        }

    public void btnClick(View v){
        Log.d("aa","bbb");
    }


}
