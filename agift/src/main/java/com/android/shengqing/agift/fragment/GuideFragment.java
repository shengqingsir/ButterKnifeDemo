package com.android.shengqing.agift.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shengqing.agift.R;
import com.android.shengqing.agift.adapter.MyViewPagerAdapter;
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
    private List<String> mTitleData = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    List<GuideClassifyBean.DataBean.ChannelsBean> mList;
    @BindView(R.id.guide_index_tl)
    TabLayout mTabLayout;
    @BindView(R.id.guide_content_vp)
    ViewPager mViewPager;
    private MyViewPagerAdapter adapter;

    public static GuideFragment newInstance(Bundle args) {
        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, null);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void bindAdapter() {
        mViewPager.setAdapter(adapter);
    }

    private void initAdapter() {
        adapter = new MyViewPagerAdapter(getChildFragmentManager(),fragmentList, mTitleData);
    }

    private void initData() {
        mList = new ArrayList<>();
        OkHttpTool.newInstance().start(URLConstants.URL_GUIDE_CLASSIFY).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                GuideClassifyBean bean = gson.fromJson(result, GuideClassifyBean.class);
                mList.addAll(bean.getData().getChannels());
                for (int i = 0; i < mList.size(); i++) {
                    String title = mList.get(i).getName();
                    mTitleData.add(title);
                }
                setupTitleData();
                setupFragment();
                initAdapter();
                bindAdapter();
                mTabLayout.setupWithViewPager(mViewPager);
            }
        });
    }

    private void setupFragment() {
        HandPickFragment fragment1 = HandPickFragment.newInstance(null);
        fragmentList.add(fragment1);
        //      从第二个位置开始
        for (int i = 1; i < mTitleData.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", mList.get(i).getId());
            OthersFragment fragment = OthersFragment.newInstance(bundle);
            fragmentList.add(fragment);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setupTitleData() {
        for (int i = 0; i < mTitleData.size(); i++) {
            //创建Tab:mTabLayout.newTab()
            //设置Tab内容:tab.setText(内容);
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleData.get(i)));
        }
    }
}
