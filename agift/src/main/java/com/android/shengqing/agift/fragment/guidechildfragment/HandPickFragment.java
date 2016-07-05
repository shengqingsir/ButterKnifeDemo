package com.android.shengqing.agift.fragment.guidechildfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shengqing.agift.R;
import com.android.shengqing.agift.bean.HandpickBean.BannerInfo;
import com.android.shengqing.agift.bean.HandpickBean.HandpickBean;
import com.android.shengqing.agift.bean.HandpickBean.RecyclerInfo;
import com.android.shengqing.agift.util.URLConstants;
import com.android.shengqing.httplibrary.IOKCallBack;
import com.android.shengqing.httplibrary.OkHttpTool;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HandPickFragment extends Fragment {

    private View view;
    @BindView(R.id.handpick_list)
    ExpandableListView expandableListView;
    private List<BannerInfo.DataBean.BannersBean> imageDatas = new ArrayList<>();
    private List<RecyclerInfo.DataBean.SecondaryBannersBean> recyclerImageDatas = new ArrayList<>();
    final ArrayList<HandpickBean.DataBean.ItemsBean> childListDatas = new ArrayList<>();
    private Map<String, List<HandpickBean.DataBean.ItemsBean>> datas = new HashMap<>();
    private List<String> groupNameDatas = new ArrayList<>();
    private Context mContext;
    private MyExpandListAdapter myExpandListAdapter;
    private HeaderRVAdapter headerRVAdapter;
    private HeaderViewHolder headerViewHolder;


    public static HandPickFragment newInstance(Bundle args) {
        HandPickFragment fragment = new HandPickFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_handpick, container, false);
        ButterKnife.bind(this, view);
        setupExpandableListView();
        setupHeaderView();
        return view;
    }

    private void setupHeaderView() {
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.handpick_header_view, null);
        headerViewHolder = new HeaderViewHolder(headerView);
        setupHeaderRecyclerView(headerViewHolder);
        loadBannerDatas();//动态加载数据
        loadRecyclerDatas();
        setupBanner(headerViewHolder);
        expandableListView.addHeaderView(headerView);
    }

    private void loadBannerDatas() {
        OkHttpTool.newInstance().start(URLConstants.BANNER_URL).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                BannerInfo bannerInfo = gson.fromJson(result, BannerInfo.class);
                imageDatas.addAll(bannerInfo.getData().getBanners());
                headerViewHolder.convenientBanner.getViewPager().getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void loadRecyclerDatas() {

        OkHttpTool.newInstance().start(URLConstants.RECYCLER_URL).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                RecyclerInfo recyclerInfo = gson.fromJson(result, RecyclerInfo.class);
                recyclerImageDatas.addAll(recyclerInfo.getData().getSecondary_banners());
                headerRVAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setupBanner(HeaderViewHolder headerViewHolder) {

        headerViewHolder.convenientBanner.setPages(new CBViewHolderCreator<HeaderBannerViewHolder>() {
            @Override
            public HeaderBannerViewHolder createHolder() {
                return new HeaderBannerViewHolder();
            }
        }, imageDatas);
               /* .setPageIndicator(new int[]{R.drawable.btn_check_disabled_nightmode,R.drawable.btn_check_normal})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);*/
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始自动滚动
        headerViewHolder.convenientBanner.startTurning(2000);
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止滚动
        headerViewHolder.convenientBanner.stopTurning();
    }

    class HeaderBannerViewHolder implements Holder<BannerInfo.DataBean.BannersBean> {
        ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerInfo.DataBean.BannersBean data) {
//            imageView.setImageResource(data.getImage_url());
            Picasso.with(mContext).load(data.getImage_url()).into(imageView);
        }
    }

    private void setupHeaderRecyclerView(HeaderViewHolder headerViewHolder) {
        //创建布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        headerViewHolder.mRecyclerView.setLayoutManager(linearLayoutManager);
        //创建一个适配器
        headerRVAdapter = new HeaderRVAdapter();
        headerViewHolder.mRecyclerView.setAdapter(headerRVAdapter);

    }

    class HeaderViewHolder {
        @BindView(R.id.header_view_rv)
        RecyclerView mRecyclerView;
        @BindView(R.id.header_view_cb)
        ConvenientBanner convenientBanner;

        public HeaderViewHolder(View headerView) {
            ButterKnife.bind(this, headerView);
        }
    }

    //创建ViewHolder
    class HeaderRVViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public HeaderRVViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    //创建适配器
    class HeaderRVAdapter extends RecyclerView.Adapter<HeaderRVViewHolder> {

        @Override
        public HeaderRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(mContext);
            return new HeaderRVViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(HeaderRVViewHolder holder, int position) {
            //holder.imageView.setImageResource(R.mipmap.ic_launcher);
            RecyclerInfo.DataBean.SecondaryBannersBean bean = recyclerImageDatas.get(position);
            Picasso.with(mContext).load(bean.getImage_url()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return recyclerImageDatas == null ? 0 : recyclerImageDatas.size();
        }
    }


    private void setupExpandableListView() {
        //1、准备数据源
        setupData();
        //2、创建适配器
        myExpandListAdapter = new MyExpandListAdapter();
        //3、关联适配器
        expandableListView.setAdapter(myExpandListAdapter);

        //设置ExpandableListView为点击不收缩
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        //设置所有的Group全部展开
        for (int i = 0; i < groupNameDatas.size(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    private void setupData() {
        if (groupNameDatas != null && !groupNameDatas.isEmpty()) {
            return;
        }

        for (int i = 0; i < 10; i++) {
            String groupName = "GROUP" + i;
            groupNameDatas.add(groupName);
            datas.put(groupName, childListDatas);
            for (int j = 0; j <childListDatas.size(); j++) {
//                 childListDatas.add("CHILD" + j);
                OkHttpTool.newInstance().start(URLConstants.HANDPICK_URL).callback(new IOKCallBack() {
                    @Override
                    public void success(String result) {
                        Gson gson = new Gson();
                        HandpickBean bean = gson.fromJson(result, HandpickBean.class);
                        childListDatas.addAll(bean.getData().getItems());
                        myExpandListAdapter.notifyDataSetChanged();
                    }
                });

            }
        }
    }

    class MyExpandListAdapter extends BaseExpandableListAdapter {
        /**
         * 返回分组的数量
         *
         * @return
         */
        @Override
        public int getGroupCount() {
            return groupNameDatas == null ? 0 : groupNameDatas.size();
        }

        /**
         * 返回每一个分组中的item的个数
         *
         * @param groupPosition
         * @return
         */
        @Override
        public int getChildrenCount(int groupPosition) {
            String key = groupNameDatas.get(groupPosition);
            List<HandpickBean.DataBean.ItemsBean> list = datas.get(key);
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupNameDatas.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        /**
         * 创建分组视图
         *
         * @param groupPosition
         * @param isExpanded
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View view = convertView;
            GroupViewHolder groupViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.group_view, null);
                groupViewHolder = new GroupViewHolder(view);
            } else {
                groupViewHolder = (GroupViewHolder) view.getTag();
            }
            groupViewHolder.mLeftTxt.setText("2016-06-27 星期一");
            return view;
        }

        class GroupViewHolder {
            @BindView(R.id.group_left_tv)
            TextView mLeftTxt;
            @BindView(R.id.group_right_tv)
            TextView mRightTxt;

            public GroupViewHolder(View view) {
                view.setTag(this);
                ButterKnife.bind(this, view);
            }
        }

        /**
         * 创建分组中的Item的视图
         *
         * @param groupPosition
         * @param childPosition
         * @param isLastChild
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = convertView;
            ChildViewHolder childViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.child_view, null);
                childViewHolder = new ChildViewHolder(view);
            } else {
                childViewHolder = (ChildViewHolder) view.getTag();
            }
            //childViewHolder.mImageView.setImageResource(R.mipmap.ic_launcher);
            HandpickBean.DataBean.ItemsBean bean = childListDatas.get(childPosition);
            Picasso.with(mContext).load(bean.getCover_image_url()).into(childViewHolder.mImageView);

            return view;
        }

        class ChildViewHolder {
            @BindView(R.id.child_iv)
            ImageView mImageView;

            public ChildViewHolder(View view) {
                view.setTag(this);
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }


}
