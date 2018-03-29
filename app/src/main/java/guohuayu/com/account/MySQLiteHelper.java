package guohuayu.com.account;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2018/3/29.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, getMyDatabaseName(name), factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //for create delete select update bill
        String sql_finance = "reate table finance(ID integer PRIMARY KEY AUTOINCREMENT,Type varchar(10),Time varchar(20),Fee double,Remarks varchar(20),Budget varchar(10))";
        sqLiteDatabase.execSQL(sql_finance);

        //for create delete select update bill
        String sql_plan = "create table plan(ID integer PRIMARY KEY AUTOINCREMENT,Morningplan varchar(100),Afternoonplan varchar(100),Nightplan varchar(100),Rank varchar(5), Conclusion varchar(100))";
        sqLiteDatabase.execSQL(sql_plan);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private static String getMyDatabaseName(String name){
        String databaseName = name;
        boolean isSdcardEnable = false;
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            isSdcardEnable = true;
        }
        String dbPath = null;
        if(isSdcardEnable){
            dbPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"Finance/database";
        }else{//put data on memory without sdcard

        }
        File dbp = new File(dbPath);
        if(!dbp.exists()){
            dbp.mkdirs();
        }
        databaseName = dbPath + databaseName;
        return databaseName;
    }
}
