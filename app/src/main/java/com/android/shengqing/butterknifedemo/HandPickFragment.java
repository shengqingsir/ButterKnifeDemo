package com.android.shengqing.butterknifedemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HandPickFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HandPickFragment extends Fragment {

    private View view;
    @BindView(R.id.handpick_list)
    ExpandableListView expandableListView;

    private Map<String, List<String>> datas = new HashMap<>();
    private List<String> groupNameDatas = new ArrayList<>();
    private Context mContext;
    private MyExpandListAdapter myExpandListAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HandPickFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HandPickFragment newInstance() {
        HandPickFragment fragment = new HandPickFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hand_pick, container, false);
        ButterKnife.bind(this, view);
        setupExpandableListView();
        return view;
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
        for (int i = 0; i <groupNameDatas.size() ; i++) {
            expandableListView.expandGroup(i);
        }
    }

    private void setupData() {
        for (int i = 0; i < 20; i++) {
            String groupName = "组名" + i;
            groupNameDatas.add(groupName);
            ArrayList<String> childListDatas = new ArrayList<>();
            datas.put(groupName, childListDatas);
            for (int j = 0; j < 10; j++) {
                childListDatas.add("组成员" + j);
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
            List<String> list = datas.get(key);
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
            childViewHolder.mImageView.setImageResource(R.mipmap.diaochan);
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
