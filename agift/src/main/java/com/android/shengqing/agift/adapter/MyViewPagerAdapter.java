package com.android.shengqing.agift.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/6/28.
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment>  fragmentList;
    private List<String> mTitleList;

    public MyViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> mTitleList) {
        super(fm);
        this.fragmentList=fragmentList;
        this.mTitleList=mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return  fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList== null ? 0 :fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
