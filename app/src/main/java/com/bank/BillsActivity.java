package com.bank;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Adminstrator on 2017/4/2.
 */

//账单界面
public class BillsActivity extends Activity implements View.OnClickListener {

    //列举数据的ListView
    private ListView mlistbills;
    // 适配器
    private SimpleAdapter mlistbillsAdapter;
    //数据库
    private MySQLiteHelper mMysql;
    private SQLiteDatabase mDataBase;
    private ImageView imageviewback;

    // 存储数据的数组列表
    ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bills);

        mlistbills = (ListView) this.findViewById(R.id.list_bills);
        imageviewback = (ImageView) this.findViewById(R.id.imageviewBack);
        imageviewback.setOnClickListener(this);

        GetData();
        mlistbillsAdapter = new SimpleAdapter(
                this,
                listData,
                R.layout.billsitem,
                new String[]{"Time", "Type", "Fee", "Remarks"},
                new int[]{R.id.texttimeshow, R.id.imagetypeshow, R.id.textfeeshow, R.id.textremarksshow}
        );
        //赋予数据
        mlistbills.setAdapter(mlistbillsAdapter);

        //常按响应
        View.OnCreateContextMenuListener listviewLongPress = new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                // TODO Auto-generated method stub
                final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                new AlertDialog.Builder(BillsActivity.this)
                        /* 弹出窗口的最上头文字 */
                        .setTitle("delete this bill?")
                        /* 设置弹出窗口的图式 */
                        .setIcon(android.R.drawable.ic_dialog_info)
                        /* 设置弹出窗口的信息 */
                        .setMessage("Determine to delete the current record")
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialoginterface, int i) {
                                        // 获取位置索引
                                        int mListPos = info.position;
                                        // 获取对应HashMap数据内容
                                        HashMap<String, Object> map = listData.get(mListPos);
                                        // 获取id
                                        int id = Integer.valueOf((map.get("ID").toString()));

                                        // 获取数组具体值后,可以对数据进行相关的操作,例如更新数据
                                        String[] whereArgs = new String[]{String.valueOf(id)};
                                        //获取当前数据库
                                        mMysql = new MySQLiteHelper(BillsActivity.this, "finance.db", null, 1);
                                        mDataBase = mMysql.getReadableDatabase();
                                        try {
                                            mDataBase.delete("Finance", "ID=?", whereArgs);
                                            listData.remove(mListPos);
                                            mlistbillsAdapter.notifyDataSetChanged();
                                        } catch (Exception e) {
                                            Log.e("delete erro!", "error");
                                        } finally {
                                            mDataBase.close();
                                            mMysql.close();
                                        }
                                    }
                                }
                        ).setNegativeButton(
                        "NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                            }
                        }
                ).show();

            }
        };

//                mlistbills.setOnCreateContextMenuListener(listviewLongPress);
//                //点击事件
//                AdapterView.OnItemClickListener lvItemListener  = new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                // 获取对应HashMap数据内容
//                                HashMap<String, Object> map = listData.get(i);
//                                // 获取id
//                                int id = Integer.valueOf((map.get("ID").toString()));
//                                Intent intent = new Intent(BillsActivity.this, RecorderActivity.class);
//                                intent.putExtra("ID",id);
//                                startActivity(intent);
//                                BillsActivity.this.finish();
//                        }
//                };
//                mlistbills.setOnItemClickListener(lvItemListener);


        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            float x, y, ux, uy;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        ux = event.getX();
                        uy = event.getY();
                        int p2 = ((ListView) v).pointToPosition((int) ux, (int) uy);

                        return false;
                }

                return false;
            }

        };
        mlistbills.setOnTouchListener(onTouchListener);

    }

    //从数据库获得适配器数据

    public void GetData() {
        mMysql = new MySQLiteHelper(this, "finance.db", null, 1);
        mDataBase = mMysql.getReadableDatabase();

        Cursor cursor = mDataBase.rawQuery("select * from finance order by Time DESC ", null);
        cursor.moveToFirst();
        int columnsSize = cursor.getColumnCount();
        int number = 0;
        while (number < cursor.getCount()) {
            //  cursor.move(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            String budget = cursor.getString(cursor.getColumnIndex("Budget"));
            map.put("ID", cursor.getString(cursor.getColumnIndex("ID")));
            map.put("Fee", cursor.getDouble(cursor.getColumnIndex("Fee")));
            map.put("Time", cursor.getString(cursor.getColumnIndex("Time")));
            if (budget.equals("income"))
                map.put("Fee", "+" + cursor.getString(cursor.getColumnIndex("Fee")));
            else
                map.put("Fee", "-" + cursor.getString(cursor.getColumnIndex("Fee")));
            map.put("Remarks", cursor.getString(cursor.getColumnIndex("Remarks")));

            if ((cursor.getString(cursor.getColumnIndex("Type"))).equals("clothing")) {
                map.put("Type", R.drawable.cloth);
            } else if ((cursor.getString(cursor.getColumnIndex("Type"))).equals("eat")) {
                map.put("Type", R.drawable.shi);
            } else if ((cursor.getString(cursor.getColumnIndex("Type"))).equals("housing")) {
                map.put("Type", R.drawable.zhu);
            } else if ((cursor.getString(cursor.getColumnIndex("Type"))).equals("tansportation")) {
                map.put("Type", R.drawable.xing);
            } else if ((cursor.getString(cursor.getColumnIndex("Type"))).equals("others")) {
                map.put("Type", R.drawable.getmoney);
            }

            cursor.moveToNext();
            listData.add(map);
            number++;
            System.out.println(listData);
        }

        cursor.close();
        mDataBase.close();
        mMysql.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageviewBack:
                this.finish();
                break;
            default:
                break;
        }

    }

}


