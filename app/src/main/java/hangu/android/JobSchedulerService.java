package hangu.android;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by victor on 21/03/17.
 */

public class JobSchedulerService extends JobService {


    private Handler mJobHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage( Message msg ) {
            Toast.makeText( getApplicationContext(),
                    "JobService task running123", Toast.LENGTH_SHORT )
                    .show();

            // info the system task finish
            jobFinished( (JobParameters) msg.obj, false );
            return true;
        }

    } );

    @Override
    public boolean onStartJob(JobParameters params) {

        mJobHandler.sendMessage( Message.obtain( mJobHandler, 1, params ) );
        Log.i("TESTE----123", "on start job: " + params.getJobId());
        return true; // EXECUTE in thread
    }

    @Override
    public boolean onStopJob(JobParameters params) {
                Log.i("TESTE", "on stpop job: " + params.getJobId());


        mJobHandler.removeMessages( 2 );
        return true;
        //return false;

        //return true;
    }

}