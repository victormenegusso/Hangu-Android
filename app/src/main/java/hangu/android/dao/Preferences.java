package hangu.android.dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by victor on 04/04/17.
 */

public class Preferences {
    private final String NAME = "hangu.android.preferences";
    private final String MONITOR_STATUS = "MONITOR_STATUS";

    private SharedPreferences sharedPreferences;
    private static Preferences preferences;

    private Preferences(Context context){
        this.sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setMonitorStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MONITOR_STATUS,status);
        editor.commit();
    }

    public boolean getMonitorStatus(){
        return sharedPreferences.getBoolean(MONITOR_STATUS,false);
    }

    public static Preferences getPreferences(Context context){
        if(preferences == null){
            preferences = new Preferences(context);
        }
        return preferences;
    }

}
