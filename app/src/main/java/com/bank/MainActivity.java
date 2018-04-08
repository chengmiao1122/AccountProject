package com.bank;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adminstrator on 2017/4/2.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    //用于控制mainactivity主界面显示内容
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    /**
     * 底部三个按钮
     */
    private LinearLayout mTabBtnAccount;
    private LinearLayout mTabBtnPlan;
    private LinearLayout mTabBtnManage;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onResume() {
        super.onResume();

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        verifyStoragePermissions(this);

        initView();

        findViewById(R.id.iv_acount).setOnClickListener(this);
        findViewById(R.id.iv_manage).setOnClickListener(this);
        findViewById(R.id.iv_planDetail).setOnClickListener(this);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }

        };

        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            private int currentIndex;

            @Override
            public void onPageSelected(int position) {
                resetTabBtn();
                //set selected bottom iamge to green
                switch (position) {
                    case 0:
                        ((ImageView) mTabBtnAccount.findViewById(R.id.iv_acount))
                                .setImageResource(R.drawable.home_pressed);
                        break;
                    case 1:
                        ((ImageView) mTabBtnPlan.findViewById(R.id.iv_planDetail))
                                .setImageResource(R.drawable.plan_pressed);
                        break;
                    case 2:
                        ((ImageView) mTabBtnManage.findViewById(R.id.iv_manage))
                                .setImageResource(R.drawable.plus_pressed);
                        break;
                }

                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //check for permission
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // ask for permission
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //reset the three bottom image to grey
    protected void resetTabBtn() {

        ((ImageView) mTabBtnAccount.findViewById(R.id.iv_acount))
                .setImageResource(R.drawable.home_unpressed);
        ((ImageView) mTabBtnPlan.findViewById(R.id.iv_planDetail))
                .setImageResource(R.drawable.plan_unpress);
        ((ImageView) mTabBtnManage.findViewById(R.id.iv_manage))
                .setImageResource(R.drawable.plus_unpressed);
    }

    private void initView() {

        mTabBtnAccount = (LinearLayout) findViewById(R.id.bottom_account);
        mTabBtnPlan = (LinearLayout) findViewById(R.id.bottom_plan);
        mTabBtnManage = (LinearLayout) findViewById(R.id.bottom_manage);

        FragmentAccount fragmentaccount = new FragmentAccount();
        FragmentManage fragmentfind = new FragmentManage();
        FragmentPlan fragmentplan = new FragmentPlan();
        mFragments.add(fragmentaccount);
        mFragments.add(fragmentplan);
        mFragments.add(fragmentfind);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_acount:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.iv_planDetail:
                mViewPager.setCurrentItem(1, true);
                break;
            case R.id.iv_manage:
                mViewPager.setCurrentItem(2, true);
                break;
        }
    }
}
