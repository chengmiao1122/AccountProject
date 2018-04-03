package com.bank;

import android.Manifest;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
/**
 * Created by Adminstrator on 2017/4/2.
 */
//账户的fragment
public class FragmentAccount extends Fragment {

        private MySQLiteHelper mMysql;
        private SQLiteDatabase mDataBase;
        private TextView textRemainder,textPay,textIncome;

        private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        private AlertDialog dialog;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.finance_check, container, false);

                double resultIncome = 0,resultRemainder = 0,resultPay = 0;
                textIncome      = (TextView)view.findViewById(R.id.textincome);
                textRemainder      = (TextView)view.findViewById(R.id.textremainder);
                textPay      = (TextView)view.findViewById(R.id.textpay);

                mMysql = new MySQLiteHelper(getActivity(), "finance.db", null, 1);
                mDataBase = mMysql.getReadableDatabase();


                //*****在每次初始化“账户”fragment时都重新计算一次总收入和总支出***
                Cursor cursor = mDataBase.rawQuery("select Fee,Budget from finance",null);

                cursor.moveToFirst();

                if (cursor.getCount() > 0) {
                        for (int i = 0; i < cursor.getCount(); i++) {
                                //  cursor.move(i);
                                //移动到指定记录
                                double Fee = cursor.getDouble(cursor.getColumnIndex("Fee"));
                                String budget = cursor.getString(cursor.getColumnIndex("Budget"));
                                if(budget.equals("payment")) {
                                        resultPay += Fee;
                                } else if (budget.equals("income")){
                                        resultIncome += Fee;
                                }
                                cursor.moveToNext();
                        }
                }
                DecimalFormat df = new DecimalFormat("###.##");
                textPay.setText(String.valueOf(df.format(resultPay)));
                textIncome.setText(String.valueOf(df.format(resultIncome)));
                textRemainder.setText(String.valueOf(df.format(resultIncome - resultPay)));

                cursor.close();
                mDataBase.close();
                mMysql.close();

                return view;
        }
}


