package hangu.android.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import hangu.android.entity.HanguSocket;

/**
 * Created by Victor Menegusso on 12/03/17.
 */

public class HanguSocketDAO {

    private static final String TABLE_NAME = "hangu_socket";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_HOST = "host";
    private static final String COLUMN_NAME_PORT = "port";

    private DBHelper dataBaseHelper;
    private SQLiteDatabase db;

    public HanguSocketDAO(Context context) {
        this.dataBaseHelper = new DBHelper(context);
    }

    public void open () throws SQLException {
        db = dataBaseHelper.getWritableDatabase ();
    }
    public void close () {
        dataBaseHelper.close();
    }

    public long insert(HanguSocket hanguSocket) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_HOST, hanguSocket.getHost());
        values.put(COLUMN_NAME_PORT, hanguSocket.getPort());

        return db.insert(TABLE_NAME, null, values);
    }

    public boolean update(HanguSocket hanguSocket) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_HOST, hanguSocket.getHost());
        values.put(COLUMN_NAME_PORT, hanguSocket.getPort());

        return db.update(TABLE_NAME, values, COLUMN_NAME_ID+" = " + hanguSocket.getId(), null) > 0;
    }

    public boolean remove(int id) {
        return db.delete(TABLE_NAME, COLUMN_NAME_ID+" = " + id, null) > 0;
    }

    public List<HanguSocket> list(){

        List<HanguSocket> hanguSockets = new ArrayList<>();

        String [] columns = { COLUMN_NAME_ID, COLUMN_NAME_HOST, COLUMN_NAME_PORT };
        Cursor cursor = db.query ( TABLE_NAME , columns, null, null, null, null, null );
        cursor.moveToFirst();
        while (! cursor.isAfterLast()) {
            HanguSocket hanguSocket = new HanguSocket();

            hanguSocket.setId( cursor.getInt(0) );
            hanguSocket.setHost( cursor.getString(1) );
            hanguSocket.setPort( cursor.getInt(2) );

            hanguSockets.add( hanguSocket );
            cursor.moveToNext ();
        }
        cursor.close ();
        return hanguSockets ;
    }

    public HanguSocket get(int id){

        HanguSocket hanguSocket = null;

        String [] columns = { COLUMN_NAME_ID, COLUMN_NAME_HOST, COLUMN_NAME_PORT };
        Cursor cursor = db.query ( TABLE_NAME , columns, null, null, null, null, null );
        cursor.moveToFirst();
        if (! cursor.isAfterLast()) {
            hanguSocket = new HanguSocket();

            hanguSocket.setId( cursor.getInt(0) );
            hanguSocket.setHost( cursor.getString(1) );
            hanguSocket.setPort( cursor.getInt(2) );

            cursor.moveToNext ();
        }
        cursor.close ();
        return hanguSocket ;
    }

    public static String create() {
        String sql = "CREATE TABLE " + TABLE_NAME + " (";
        sql += COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += COLUMN_NAME_HOST + " TEXT NOT NULL, ";
        sql += COLUMN_NAME_PORT + " INTEGER NOT NULL)";

        return sql;
    }
}
