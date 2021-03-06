package hangu.android.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hangu.android.entity.WebApp;

/**
 * Created by Victor Menegusso on 08/03/17.
 */

public class WebAppDAO {

    private static final String TABLE_NAME = "web_app";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_NAME = "name";
    private static final String COLUMN_NAME_URL = "url";
    private static final String COLUMN_NAME_HTTP_METHOD = "http_method";
    private static final String COLUMN_NAME_CHECKIN_PERIOD = "checkin_period";

    private DBHelper dataBaseHelper;
    private SQLiteDatabase db;

    public WebAppDAO(Context context) {
        this.dataBaseHelper = new DBHelper(context);
    }

    public static String create() {
        String sql = "CREATE TABLE " + TABLE_NAME + " (";
        sql += COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += COLUMN_NAME_NAME + " TEXT NOT NULL, ";
        sql += COLUMN_NAME_URL + " TEXT NOT NULL, ";
        sql += COLUMN_NAME_HTTP_METHOD + " TEXT NOT NULL, ";
        sql += COLUMN_NAME_CHECKIN_PERIOD + " INTEGER) ";

        return sql;
    }

    public void open() throws SQLException {
        db = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
    }

    public long insert(WebApp webApp) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, webApp.getName());
        values.put(COLUMN_NAME_URL, webApp.getUrl());
        values.put(COLUMN_NAME_HTTP_METHOD, webApp.getHttpMethod());
        values.put(COLUMN_NAME_CHECKIN_PERIOD, webApp.getCheckInPeriod());

        return db.insert(TABLE_NAME, null, values);
    }

    public boolean update(WebApp webApp) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, webApp.getName());
        values.put(COLUMN_NAME_URL, webApp.getUrl());
        values.put(COLUMN_NAME_HTTP_METHOD, webApp.getHttpMethod());
        values.put(COLUMN_NAME_CHECKIN_PERIOD, webApp.getCheckInPeriod());

        return db.update(TABLE_NAME, values, COLUMN_NAME_ID + " = " + webApp.getId(), null) > 0;
    }

    public boolean remove(int id) {
        return db.delete(TABLE_NAME, COLUMN_NAME_ID + " = " + id, null) > 0;
    }

    public List<WebApp> list() {

        List<WebApp> webApps = new ArrayList<>();

        String[] columns = {COLUMN_NAME_ID, COLUMN_NAME_NAME, COLUMN_NAME_URL, COLUMN_NAME_HTTP_METHOD, COLUMN_NAME_CHECKIN_PERIOD};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            WebApp webApp = new WebApp();

            webApp.setId(cursor.getInt(0));
            webApp.setName(cursor.getString(1));
            webApp.setUrl(cursor.getString(2));
            webApp.setHttpMethod(cursor.getString(3));
            webApp.setCheckInPeriod(cursor.getLong(4));

            webApps.add(webApp);
            cursor.moveToNext();
        }
        cursor.close();
        return webApps;
    }

    public WebApp get(int id) {

        WebApp webApp = null;

        String[] columns = {COLUMN_NAME_ID, COLUMN_NAME_NAME, COLUMN_NAME_URL, COLUMN_NAME_HTTP_METHOD, COLUMN_NAME_CHECKIN_PERIOD};
        Cursor cursor = db.query(TABLE_NAME, columns, COLUMN_NAME_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            webApp = new WebApp();

            webApp.setId(cursor.getInt(0));
            webApp.setName(cursor.getString(1));
            webApp.setUrl(cursor.getString(2));
            webApp.setHttpMethod(cursor.getString(3));
            webApp.setCheckInPeriod(cursor.getLong(4));

            cursor.moveToNext();
        }
        cursor.close();
        return webApp;
    }
}