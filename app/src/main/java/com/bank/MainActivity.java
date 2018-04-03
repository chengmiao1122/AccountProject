package com.bank;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Adminstrator on 2017/4/2.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
        private View.OnClickListener onclicklistene;

        //用于控制mainactivity主界面显示内容
        private ViewPager mViewPager;
        private FragmentPagerAdapter mAdapter;
        private List<Fragment> mFragments = new ArrayList<Fragment>();

        /**
         * 底部四个按钮
         */
        private LinearLayout mTabBtnWeixin;
        private LinearLayout mTabBtnFrd;
        private LinearLayout mTabBtnAddress;
        private LinearLayout mTabBtnSettings;


        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

                initView();

                findViewById(R.id.iv_acount).setOnClickListener(this);
                findViewById(R.id.iv_manage).setOnClickListener(this);
                findViewById(R.id.iv_planDetail).setOnClickListener(this);
                findViewById(R.id.iv_setting).setOnClickListener(this);

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
                                //将所点击按钮图样设置为绿色被选图样 并获取所选按钮位置position
                                switch (position) {
                                        case 0:
                                                ((ImageView) mTabBtnWeixin.findViewById(R.id.iv_acount))
                                                        .setImageResource(R.drawable.home_pressed);
                                                break;
                                        case 1:
                                                ((ImageView) mTabBtnFrd.findViewById(R.id.iv_planDetail))
                                                        .setImageResource(R.drawable.plan_pressed);
                                                break;
                                        case 2:
                                                ((ImageView) mTabBtnAddress.findViewById(R.id.iv_manage))
                                                        .setImageResource(R.drawable.plus_pressed);
                                                break;
                                        case 3:
                                                ((ImageView) mTabBtnSettings.findViewById(R.id.iv_setting))
                                                        .setImageResource(R.drawable.setting_pressed);
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

        //将底部四个按钮图样初始化为未点击灰色图样
        protected void resetTabBtn() {

                ((ImageView) mTabBtnWeixin.findViewById(R.id.iv_acount))
                        .setImageResource(R.drawable.home_unpressed);
                ((ImageView) mTabBtnFrd.findViewById(R.id.iv_planDetail))
                        .setImageResource(R.drawable.plan_unpress);
                ((ImageView) mTabBtnAddress.findViewById(R.id.iv_manage))
                        .setImageResource(R.drawable.plus_unpressed);
                ((ImageView) mTabBtnSettings.findViewById(R.id.iv_setting))
                        .setImageResource(R.drawable.setting_unpressed);
        }

        private void initView() {

                mTabBtnWeixin = (LinearLayout) findViewById(R.id.bottom_account);
                mTabBtnFrd = (LinearLayout) findViewById(R.id.bottom_plan);
                mTabBtnAddress = (LinearLayout) findViewById(R.id.bottom_manage);
                mTabBtnSettings = (LinearLayout) findViewById(R.id.bottom_setting);

                //将四个fragment创建并添加到mFragments列表中开始监听
                FragmentAccount fragmentaccount = new FragmentAccount();
                FragmentFind fragmentfind = new FragmentFind();/*******************************/
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
                        case R.id.iv_setting:
                                mViewPager.setCurrentItem(3, true);
                                break;
                }
        }
}
