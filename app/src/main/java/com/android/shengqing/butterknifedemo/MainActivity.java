package com.android.shengqing.butterknifedemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.guide_index_tl)
    TabLayout mTabLayout;
    @BindView(R.id.guide_content_vp)
    ViewPager mViewPager;


    private List<Fragment> fragments=new ArrayList<>();
    private List<String> mTitleList=new ArrayList<>();
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //1、准备数据源
        setupData();
        setupTitleData();
        //2、创建适配器
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        //3、关联适配器
        mViewPager.setAdapter(myViewPagerAdapter);

        //TabLayout与ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void setupTitleData() {
        mTitleList.add("精选");
        mTitleList.add("海淘");
        mTitleList.add("送女票");
        mTitleList.add("创意生活");
        mTitleList.add("送基友");
    }

    private void setupData() {
        fragments.add(HandPickFragment.newInstance());
        fragments.add(OthersFragment.newInstance());
        fragments.add(OthersFragment.newInstance());
        fragments.add(OthersFragment.newInstance());
        fragments.add(OthersFragment.newInstance());
    }
    class MyViewPagerAdapter extends FragmentPagerAdapter{
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments==null ? 0 : fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }


}
