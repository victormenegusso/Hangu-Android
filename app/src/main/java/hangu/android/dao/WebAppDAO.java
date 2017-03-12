package hangu.android.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hangu.android.entity.WebApp;

/**
 * Created by Victor Menegusso on 08/03/17.
 */

public class WebAppDAO {

    public static final String TABLE_NAME = "web_app";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_URL = "url";
    public static final String COLUMN_NAME_HTTP_METHOD = "http_method";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "hangu.db";

    private DataBaseHelper dataBaseHelper;
    private Context context;
    private SQLiteDatabase db;

    public WebAppDAO(Context context) {
        this.context = context;
        this.dataBaseHelper = new DataBaseHelper(context);
    }

    public void open () throws SQLException {
        db = dataBaseHelper.getWritableDatabase ();
    }
    public void close () {
        dataBaseHelper.close();
    }

    public long insert(WebApp webApp) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, webApp.getName());
        values.put(COLUMN_NAME_URL, webApp.getUrl());
        values.put(COLUMN_NAME_HTTP_METHOD, webApp.getHttpMethod());

        return db.insert(TABLE_NAME, null, values);
    }

    /*
    public boolean updateContact(long cod, String name, String city) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("city", city);

        return db.update(TABLE_NAME, values, "cod = " + cod, null) > 0;
    }

    public boolean removeContact(long cod) {
        return db.delete(TABLE_NAME, "cod = " + cod, null) > 0;
    }

    public Cursor getContacts()
    {
        return db.query(TABLE_NAME,new String[]{"cod","name","city"},null,null,null,null,"city");
    }
    */

    public List<WebApp> list(){

        List<WebApp> webApps = new ArrayList<>();

        String [] columns = { COLUMN_NAME_ID, COLUMN_NAME_NAME, COLUMN_NAME_URL, COLUMN_NAME_HTTP_METHOD };

        Cursor cursor = db.query ( TABLE_NAME ,
                columns , null , null , null , null , null );
        cursor . moveToFirst ();
        while (! cursor . isAfterLast ()) {
            WebApp webApp = new WebApp();

            webApp.setId( cursor.getInt(0) );
            webApp.setName( cursor.getString(1) );
            webApp.setUrl( cursor.getString(2) );
            webApp.setHttpMethod( cursor.getString(3) );

            webApps.add( webApp );
            cursor . moveToNext ();
        }
        cursor.close ();
        return webApps ;

        /*
        List<WebApp> webApps = new ArrayList<>();

        webApps.add( new WebApp("globo","www.globo.com","get") );
        webApps.add( new WebApp("google","www.google.com","get") );
        webApps.add( new WebApp("facebook","www.facebook.com","get") );
        webApps.add( new WebApp("teste","192.168.1.180","get") );

        /*
        webApps.add( new WebApp("teste 2","192.168.1.181","get") );
        webApps.add( new WebApp("teste 3","192.168.1.182","get") );
        webApps.add( new WebApp("teste 4","192.168.1.183","get") );
        webApps.add( new WebApp("teste 5","192.168.1.184","get") );
        webApps.add( new WebApp("teste 6","192.168.1.185","get") );

        webApps.add( new WebApp("gizmodo","www.gizmodo.com.br","get") );
        webApps.add( new WebApp("omgubuntu","www.omgubuntu.com","get") );
        webApps.add( new WebApp("pucpr","www.pucpr.br","get") );
        */
        /*
        return webApps;
        */

    }


    private static class DataBaseHelper extends SQLiteOpenHelper {
        /*
        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        */

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE " + TABLE_NAME + " (";
            sql += COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
            sql += COLUMN_NAME_NAME + " TEXT NOT NULL, ";
            sql += COLUMN_NAME_URL + " TEXT NOT NULL, ";
            sql += COLUMN_NAME_HTTP_METHOD + " TEXT NOT NULL)";

            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
        /*
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
         */

        /*



        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
        */
    }

}
