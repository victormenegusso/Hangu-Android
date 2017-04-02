package hangu.android.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import hangu.android.entity.ServerApp;

/**
 * Created by Victor Menegusso on 12/03/17.
 */

public class ServerAppDAO {

    private static final String TABLE_NAME = "server_app";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_NAME = "name";
    private static final String COLUMN_NAME_URL = "url";
    private static final String COLUMN_NAME_PATH_PROCESS_START = "path_process_start";
    private static final String COLUMN_NAME_PATH_PROCESS_STOP = "path_process_stop";
    private static final String COLUMN_NAME_HANGU_SOCKET_ID = "hangu_socket_id";
    private static final String COLUMN_NAME_CHECKIN_PERIOD = "checkin_period";

    private DBHelper dataBaseHelper;
    private Context context;
    private SQLiteDatabase db;

    public ServerAppDAO(Context context) {
        this.context = context;
        this.dataBaseHelper = new DBHelper(context);
    }

    public static String create() {
        String sql = "CREATE TABLE " + TABLE_NAME + " (";
        sql += COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += COLUMN_NAME_NAME + " TEXT NOT NULL, ";
        sql += COLUMN_NAME_URL + " TEXT NOT NULL, ";
        sql += COLUMN_NAME_PATH_PROCESS_START + " TEXT NOT NULL, ";
        sql += COLUMN_NAME_PATH_PROCESS_STOP + " TEXT NOT NULL, ";
        sql += COLUMN_NAME_HANGU_SOCKET_ID + " INTEGER NOT NULL, ";
        sql += COLUMN_NAME_CHECKIN_PERIOD + " INTEGER)";

        return sql;
    }

    public void open () throws SQLException {
        db = dataBaseHelper.getWritableDatabase ();
    }
    public void close () {
        dataBaseHelper.close();
    }

    public long insert(ServerApp serverApp) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, serverApp.getName());
        values.put(COLUMN_NAME_URL, serverApp.getUrl());
        values.put(COLUMN_NAME_PATH_PROCESS_START, serverApp.getPathProcessStart());
        values.put(COLUMN_NAME_PATH_PROCESS_STOP, serverApp.getPathProcessStop());
        values.put(COLUMN_NAME_HANGU_SOCKET_ID, serverApp.getHanguSocket().getId());
        values.put(COLUMN_NAME_CHECKIN_PERIOD, serverApp.getCheckInPeriod());

        return db.insert(TABLE_NAME, null, values);
    }

    public boolean update(ServerApp serverApp) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, serverApp.getName());
        values.put(COLUMN_NAME_URL, serverApp.getUrl());
        values.put(COLUMN_NAME_PATH_PROCESS_START, serverApp.getPathProcessStart());
        values.put(COLUMN_NAME_PATH_PROCESS_STOP, serverApp.getPathProcessStop());
        values.put(COLUMN_NAME_HANGU_SOCKET_ID, serverApp.getHanguSocket().getId());
        values.put(COLUMN_NAME_CHECKIN_PERIOD, serverApp.getCheckInPeriod());

        return db.update(TABLE_NAME, values, COLUMN_NAME_ID+" = " + serverApp.getId(), null) > 0;
    }

    public boolean remove(int id) {
        return db.delete(TABLE_NAME, COLUMN_NAME_ID+" = " + id, null) > 0;
    }

    public List<ServerApp> list(){

        List<ServerApp> serverApps = new ArrayList<>();

        String [] columns = { COLUMN_NAME_ID, COLUMN_NAME_NAME, COLUMN_NAME_URL, COLUMN_NAME_PATH_PROCESS_START, COLUMN_NAME_PATH_PROCESS_STOP, COLUMN_NAME_HANGU_SOCKET_ID, COLUMN_NAME_CHECKIN_PERIOD };
        Cursor cursor = db.query ( TABLE_NAME, columns, null, null, null, null, null);
        cursor.moveToFirst ();

        while (! cursor.isAfterLast()) {
            ServerApp serverApp = new ServerApp();

            serverApp.setId( cursor.getInt(0) );
            serverApp.setName( cursor.getString(1) );
            serverApp.setUrl( cursor.getString(2) );
            serverApp.setPathProcessStart( cursor.getString(3) );
            serverApp.setPathProcessStop( cursor.getString(4) );
            serverApp.setCheckInPeriod (cursor.getLong(6) );

            int hanguSocketID= cursor.getInt(5);

            HanguSocketDAO hsDAO = new HanguSocketDAO(context);
            hsDAO.open();
            serverApp.setHanguSocket( hsDAO.get(hanguSocketID) );
            hsDAO.close();

            serverApps.add( serverApp );
            cursor.moveToNext();
        }
        cursor.close();
        return serverApps ;
    }

    public ServerApp get(int id) {

        ServerApp serverApp = null;

        String [] columns = { COLUMN_NAME_ID, COLUMN_NAME_NAME, COLUMN_NAME_URL, COLUMN_NAME_PATH_PROCESS_START, COLUMN_NAME_PATH_PROCESS_STOP, COLUMN_NAME_HANGU_SOCKET_ID, COLUMN_NAME_CHECKIN_PERIOD };
        Cursor cursor = db.query(TABLE_NAME, columns, COLUMN_NAME_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            serverApp = new ServerApp();

            serverApp.setId( cursor.getInt(0) );
            serverApp.setName( cursor.getString(1) );
            serverApp.setUrl( cursor.getString(2) );
            serverApp.setPathProcessStart( cursor.getString(3) );
            serverApp.setPathProcessStop( cursor.getString(4) );
            serverApp.setCheckInPeriod (cursor.getLong(6) );

            int hanguSocketID= cursor.getInt(5);

            HanguSocketDAO hsDAO = new HanguSocketDAO(context);
            hsDAO.open();
            serverApp.setHanguSocket( hsDAO.get(hanguSocketID) );
            hsDAO.close();

            cursor.moveToNext();
        }
        cursor.close();
        return serverApp;
    }
}