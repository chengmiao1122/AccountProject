package com.bank;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener {

    //按钮
    private Button PlanSure, PlanCancel;
    //编辑框控件
    private EditText MorningPlan, AfternoonPlan, NightPlan;
    private ImageView PlanBack;

    //数据库
    private MySQLiteHelper mMysql;
    private SQLiteDatabase mDataBase;

    private ArrayList<String> Data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan);
        initView();

    }

    public void initView() {
        PlanSure         = (Button) findViewById(R.id.btn_savebill);
        PlanCancel       = (Button) findViewById(R.id.btn_cancelplan);
        MorningPlan     = (EditText) findViewById(R.id.MorningPlan);
        AfternoonPlan   = (EditText) findViewById(R.id.AfternoonPlan);
        NightPlan       = (EditText) findViewById(R.id.NightPlan);

        PlanSure.setOnClickListener(this);
        PlanCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_savebill:
                GetData();
                WriteData();
                break;
            case R.id.btn_cancelplan:
                this.finish();
                break;
        }

    }

    public void GetData()
    {
        Data.clear();
        Data.add(MorningPlan.getText().toString());
        Data.add(AfternoonPlan.getText().toString());
        Data.add(NightPlan.getText().toString());
    }

    public void WriteData()
    {
        mMysql = new MySQLiteHelper(this, "finance.db", null, 1);
        mDataBase = mMysql.getReadableDatabase();

        ContentValues cv=new ContentValues();
        cv.put("Morningplan",Data.get(0));
        cv.put("Afternoonplan",Data.get(1));
        cv.put("Nightplan", Data.get(2));

        mDataBase.insert("plan", "Nightplan", cv);
        mDataBase.close();
        mMysql.close();
        //结束当前activity
        this.finish();

    }
}
