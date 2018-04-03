package com.bank;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;
/**
 * Created by Adminstrator on 2017/4/2.
 */
//多记计界面
public class FragmentFind extends Fragment implements View.OnClickListener {
    //记一笔
    private FrameLayout frameLayout_recorder;
    //账单
    private FrameLayout frameLayout_bills;
    //计一划
    private FrameLayout frameLayout_plan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deal_data, container, false);
        frameLayout_recorder = (FrameLayout) view.findViewById(R.id.frameLayoutRecorder);
        frameLayout_bills = (FrameLayout) view.findViewById(R.id.frameLayoutStream);
        frameLayout_plan = (FrameLayout) view.findViewById(R.id.frameLayoutPlan);

        frameLayout_bills.setOnClickListener(this);
        frameLayout_recorder.setOnClickListener(this);
        frameLayout_plan.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frameLayoutRecorder:
                Intent intent = new Intent(getActivity(), RecorderActivity.class);
                startActivity(intent);
                break;
            case R.id.frameLayoutStream:
                startActivity(new Intent(getActivity(), BillsActivity.class));
                break;
            case R.id.frameLayoutPlan:
                //消息通知栏
                //定义NotificationManager
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(ns);
                //定义通知栏展现的内容信息
                int icon = R.drawable.back;
                CharSequence tickerText = "通知栏";
                long when = System.currentTimeMillis();
                Notification notification = new Notification(icon, tickerText, when);

                //定义下拉通知栏时要展现的内容信息
                Context context = getActivity().getApplicationContext();
                CharSequence contentTitle = "已经完成同步";
                CharSequence contentText = "进入程序查看详情";
                Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0,
                        notificationIntent, 0);
                notification.setLatestEventInfo(context, contentTitle, contentText,
                        contentIntent);

                //用mNotificationManager的notify方法通知用户生成标题栏消息通知
                mNotificationManager.notify(1, notification);
                startActivity(new Intent(getActivity(), PlanActivity.class));
                break;
            default:
                break;
        }

    }

}
