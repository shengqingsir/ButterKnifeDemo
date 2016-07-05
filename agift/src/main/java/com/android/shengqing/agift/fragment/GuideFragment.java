package com.android.shengqing.agift.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shengqing.agift.R;
import com.android.shengqing.agift.bean.GuideClassifyBean;
import com.android.shengqing.agift.fragment.guidechildfragment.HandPickFragment;
import com.android.shengqing.agift.fragment.guidechildfragment.OthersFragment;
import com.android.shengqing.agift.util.URLConstants;
import com.android.shengqing.httplibrary.IOKCallBack;
import com.android.shengqing.httplibrary.OkHttpTool;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GuideFragment extends Fragment {
    @BindView(R.id.guide_index_tl)
    TabLayout mTabLayout;
    @BindView(R.id.guide_content_vp)
    ViewPager mViewPager;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private MyViewPagerAdapter myViewPagerAdapter;

    public static GuideFragment newInstance() {
        GuideFragment fragment = new GuideFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        ButterKnife.bind(this, view);
        setupFragmentData();
        return view;
    }

    private void setupFragmentData() {
        //1、准备数据源
        setupTitleData();

        //2、创建适配器
        myViewPagerAdapter = new MyViewPagerAdapter(getFragmentManager());
        //3、关联适配器
        mViewPager.setAdapter(myViewPagerAdapter);

        //TabLayout与ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupTitleData() {
        OkHttpTool.newInstance().start(URLConstants.URL_GUIDE_CLASSIFY).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                GuideClassifyBean bean =
                        gson.fromJson(result, GuideClassifyBean.class);
                for (GuideClassifyBean.DataBean.ChannelsBean c : bean.getData().getChannels()) {
                    mTitleList.add(c.getName());
                    myViewPagerAdapter.notifyDataSetChanged();
                }


            }
        });

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                fragments.add(HandPickFragment.newInstance());
            }else {
                fragments.add(OthersFragment.newInstance());
            }
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return mTitleList == null ? 0 : mTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }


}
