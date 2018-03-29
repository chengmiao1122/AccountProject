package guohuayu.com.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private LinearLayout bottom_account, bottom_planDetail, bottom_mulInput, bottom_setting;
    private ImageView iv_account, iv_planDetial, iv_mulInput, iv_setting;
    private FragmentPagerAdapter adapter;
    private List<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        viewPager = findViewById(R.id.viewPager);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };

        viewPager.setAdapter(adapter);

        ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshBottom();
                switch (position){
                    case 0:
                        iv_account.setImageResource(R.drawable.home_pressed);
                        break;

                    case 1:
                        iv_planDetial.setImageResource(R.drawable.plan_pressed);
                        break;

                    case 2:
                        iv_mulInput.setImageResource(R.drawable.plus_pressed);
                        break;

                    case 3:
                        iv_setting.setImageResource(R.drawable.setting_pressed);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        viewPager.setOnPageChangeListener(changeListener);
    }

    private void initView() {
        bottom_account = findViewById(R.id.bottom_account);
        bottom_planDetail = findViewById(R.id.bottom_plan);
        bottom_mulInput = findViewById(R.id.bottom_mulInput);
        bottom_setting = findViewById(R.id.bottom_setting);

        iv_account = findViewById(R.id.iv_acount);
        iv_planDetial = findViewById(R.id.iv_planDetail);
        iv_mulInput = findViewById(R.id.iv_manage);
        iv_setting = findViewById(R.id.iv_setting);

        bottom_account.setOnClickListener(this);
        bottom_planDetail.setOnClickListener(this);
        bottom_mulInput.setOnClickListener(this);
        bottom_setting.setOnClickListener(this);

        accountFragment accountFragment = new accountFragment();
        mulInputFragment mulInputFragment = new mulInputFragment();
        planFragment planFragment = new planFragment();
        settingFragment settingFragment = new settingFragment();

        mFragment = new ArrayList<>();
        mFragment.add(accountFragment);
        mFragment.add(planFragment);
        mFragment.add(mulInputFragment);
        mFragment.add(settingFragment);
    }

    private void refreshBottom(){
        iv_account.setImageResource(R.drawable.home_unpressed);
        iv_planDetial.setImageResource(R.drawable.plan_unpress);
        iv_mulInput.setImageResource(R.drawable.plus_unpressed);
        iv_setting.setImageResource(R.drawable.setting_unpressed);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bottom_account:
                viewPager.setCurrentItem(0,true);
                break;
            case R.id.bottom_plan:
                viewPager.setCurrentItem(1,true);
                break;
            case R.id.bottom_mulInput:
                viewPager.setCurrentItem(2,true);
                break;
            case R.id.bottom_setting:
                viewPager.setCurrentItem(3,true);
                break;
        }
    }
}
