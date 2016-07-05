package com.android.shengqing.agift.fragment.guidechildfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.shengqing.agift.R;
import com.android.shengqing.agift.bean.HandpickBean.HandpickBean;
import com.android.shengqing.agift.util.URLConstants;
import com.android.shengqing.httplibrary.IOKCallBack;
import com.android.shengqing.httplibrary.OkHttpTool;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OthersFragment extends Fragment {
    List<HandpickBean.DataBean.ItemsBean> itemBean;
    private ListView mlistView;
    @BindView(R.id.other_content_listview)
    PullToRefreshListView pullToRefreshListView;
    private int id;
    private Context mContext;
    private MyListAdapter adapter;

    public static OthersFragment newInstance(Bundle args) {
        OthersFragment fragment = new OthersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle=getArguments();
        id = bundle.getInt("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others, container, false);
        ButterKnife.bind(this,view);
        mlistView=pullToRefreshListView.getRefreshableView();
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        initData();
        initAdapter();
        bindAdapter();
        return view;
    }

    private void bindAdapter() {
        mlistView.setAdapter(adapter);
    }

    private void initAdapter() {

        adapter=new MyListAdapter();
    }

    private void initData() {
        itemBean=new ArrayList<>();
        OkHttpTool.newInstance().start(URLConstants.URL_START + id + URLConstants.URL_END).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson=new Gson();
                HandpickBean bean=gson.fromJson(result,HandpickBean.class);
                itemBean.addAll(bean.getData().getItems());
                adapter.notifyDataSetChanged();
            }
        });


    }




    class   MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemBean.size();
        }

        @Override
        public Object getItem(int position) {
            return itemBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){
                convertView =LayoutInflater.from(mContext).inflate(R.layout.item_view,null);
                viewHolder=new ViewHolder();
                viewHolder.imageView= (ImageView) convertView.findViewById(R.id.child_image);
                viewHolder.textView1= (TextView) convertView.findViewById(R.id.child_text);
                viewHolder.textView2= (TextView) convertView.findViewById(R.id.child_text0);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();

            }
            Picasso.with(getActivity()).load(itemBean.get(position).getCover_image_url())
                    .into(viewHolder.imageView);
            viewHolder.textView2.setText("  " + itemBean.get(position).getLikes_count());
            viewHolder.textView1.setText(itemBean.get(position).getTitle());
            return convertView;
        }
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView1;
        TextView textView2;

    }
}
