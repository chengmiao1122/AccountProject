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
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;

/**
 * Created by Adminstrator on 2017/4/2.
 */
//多记计界面
public class FragmentManage extends Fragment implements View.OnClickListener {
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
                startActivity(new Intent(getActivity(), PlanActivity.class));
                break;
            default:
                break;
        }

    }

}
