package hangu.android.test;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import hangu.android.R;

public class ReadWebpageAsyncTaskActivity extends AppCompatActivity {


        private TextView textView;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_read_webpage_async_task);
            textView = (TextView) findViewById(R.id.TextView01);
        }

        private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... urls) {
                return "aaaaaaaaaaaaaaaaa";

            }
            @Override
            protected void onPostExecute(String result) {
                textView.setText(result);
            }
        }




    public void onClick(View view) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[] { "http://www.vogella.com/index.html" });

    }
}
