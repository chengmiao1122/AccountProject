package com.bank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Adminstrator on 2017/4/2.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    /*
    * When the database is first created, it executes the method. Generally,
    * the initialization operation such as table creation is executed in this method.
    * rewrite the onCreate method, invoke the execSQL method to create the table.
    * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("SWORD", "create a Database");
        String sql = "create table finance(ID integer PRIMARY KEY AUTOINCREMENT," +
                "Type varchar(10),Time varchar(20),Fee double," +
                "Remarks varchar(20),Budget varchar(10))";
        db.execSQL(sql);

        sql = "create table plan(ID integer PRIMARY KEY AUTOINCREMENT," +
                "Morningplan varchar(100),Afternoonplan varchar(100)," +
                "Nightplan varchar(100)," +
                "Rank varchar(5), Conclusion varchar(100))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public MySQLiteHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, getMyDatabaseName(name), factory, version);
    }

    private static String getMyDatabaseName(String name) {
        String databasename = name;
        boolean isSdcardEnable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {//SDCard是否插入
            isSdcardEnable = true;
        }
        String dbPath = null;
        if (isSdcardEnable) {
            dbPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Finance/database/";
        } else {//未插入SDCard，建在内存中

        }
        File dbp = new File(dbPath);
        if (!dbp.exists()) {
            dbp.mkdirs();
        }
        databasename = dbPath + databasename;
        return databasename;
    }


}
