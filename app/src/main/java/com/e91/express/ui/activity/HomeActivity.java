package com.e91.express.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.e91.express.R;
import com.e91.express.base.BaseActivity;
import com.e91.express.ui.adapter.MenuItemAdapter;
import com.e91.express.ui.fragment.HomeFragment;
import com.e91.express.ui.view.BadgeButton;
import com.e91.express.ui.view.CustomViewPager;
import com.e91.express.ui.view.SplashScreen;
import com.e91.express.util.HandlerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * =====================================
 * 项目名称：com.e91.express.ui.activity
 * 类描述：主界面
 * 创建人：大风车
 * 创建时间：2016/10/23 15:37
 * 修改人：大风车
 * 修改时间：2016/10/23 15:37
 * 版本说明：Version1.0
 * =====================================
 */

public class HomeActivity extends BaseActivity {
    private SplashScreen splashScreen;
    @Bind(R.id.toolbarBase)
    Toolbar toolbar;
    @Bind(R.id.nav_index)
    LinearLayout nav_index;//主页
    @Bind(R.id.nav_order)
    LinearLayout nav_order;//行程
    @Bind(R.id.nav_call)
    ImageView nav_call;//呼叫
    @Bind(R.id.main_viewpager)
    CustomViewPager customViewPager;//切换View
    @Bind(R.id.id_lv_left_menu)
    ListView mLvLeftMenu;//侧滑View
    @Bind(R.id.user_bar)
    BadgeButton user_bar;
    @Bind(R.id.fd)
    DrawerLayout drawerLayout;
    private ArrayList<LinearLayout> tabs = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void injectPresenter() {
        super.injectPresenter();
        startWellCome();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        stopWellCome();
        setToolBar();
        setViewPager();
        setUpDrawer();
    }

    /**
     * 设置Title
     */
    private void setToolBar() {
        getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }
    /**
     * 设置Fragment
     */
    public void setViewPager(){
        tabs.add(nav_index);
        tabs.add(nav_order);
        final HomeFragment homeFragment = new HomeFragment();//主页
        //final OrderFragment orderFragment = new TabNetPagerFragment();//行程
        CustomViewPagerAdapter customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        customViewPagerAdapter.addFragment(homeFragment);
        //customViewPagerAdapter.addFragment(orderFragment);
        customViewPager.setAdapter(customViewPagerAdapter);
        customViewPager.setCurrentItem(0);
        nav_index.setSelected(true);
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        nav_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    customViewPager.setCurrentItem(0);
            }
        });
        nav_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //等有了行程页面在开启
                /*if (customViewPager != null) {
                    customViewPager.setCurrentItem(1);
                }*/
                Toast.makeText(HomeActivity.this,"点击行程",Toast.LENGTH_SHORT).show();
            }
        });

        //呼叫处理
        nav_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this,"点击呼叫",Toast.LENGTH_SHORT).show();
            }
        });
        user_bar.setVisibility(View.VISIBLE);
        user_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    /**
     * 设置侧滑
     */
    private void setUpDrawer() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.nav_header_main, mLvLeftMenu, false));
        mLvLeftMenu.setAdapter(new MenuItemAdapter(this));
        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        drawerLayout.closeDrawers();
                        break;
                    case 2:
                        Toast.makeText(HomeActivity.this,"点击",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case 3:
                        /*BitSetFragment bfragment = new BitSetFragment();
                        bfragment.show(getSupportFragmentManager(), "bitset");*/
                        Toast.makeText(HomeActivity.this,"点击",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case 4:
                        Toast.makeText(HomeActivity.this,"点击",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case 5:
                        Toast.makeText(HomeActivity.this,"点击",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case 6:
                        Toast.makeText(HomeActivity.this,"点击",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                }
            }
        });


    }


    /**
     * 设置引导页开始于结束
     */
    private void startWellCome(){
        splashScreen = new SplashScreen(this);
        splashScreen.show(R.drawable.bl_well,
                SplashScreen.SLIDE_LEFT);
    }
    private void stopWellCome() {
        HandlerUtil.getInstance(this).postDelayed(new Runnable() {
            @Override
            public void run() {
                splashScreen.removeSplashScreen();
            }
        },2000);
    }

    static class CustomViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();

        public CustomViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }
    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    long time = 0;

    /**
     * 双击返回桌面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1000)) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent startMain = new Intent(Intent.ACTION_MAIN);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(startMain);
//        moveTaskToBack(true);
        // System.exit(0);
        // finish();
    }
}
