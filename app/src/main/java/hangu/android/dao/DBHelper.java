package hangu.android.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hangu.android.entity.HanguSocket;

/**
 * Created by victor on 12/03/17.
 */

public class DBHelper  extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "hangu.db";

    public DBHelper(Context context) {
        super(context, ConstantsDAO.DATABASE_NAME, null, ConstantsDAO.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HanguSocketDAO.create());
        db.execSQL(ServerAppDAO.create());
        db.execSQL(WebAppDAO.create());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
