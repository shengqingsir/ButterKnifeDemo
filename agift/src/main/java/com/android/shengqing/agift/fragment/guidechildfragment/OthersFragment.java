package com.android.shengqing.agift.fragment.guidechildfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.shengqing.agift.R;
import com.android.shengqing.agift.bean.GuideBean.HaiTaoBean;
import com.android.shengqing.agift.util.URLConstants;
import com.android.shengqing.httplibrary.IOKCallBack;
import com.android.shengqing.httplibrary.OkHttpTool;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OthersFragment extends Fragment {
    @BindView(R.id.other_content_rv)
    RecyclerView mRecyclerView;
    private List<HaiTaoBean.DataBean.ItemsBean> recyclerImageDatas = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;

    public static OthersFragment newInstance() {
        OthersFragment fragment = new OthersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others, container, false);
        ButterKnife.bind(this,view);
        loadRecyclerDatas();
        setupRecyclerView();
        return view;
    }


    private void loadRecyclerDatas() {

        OkHttpTool.newInstance().start(URLConstants.HAITAO_URL).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                HaiTaoBean bean = gson.fromJson(result, HaiTaoBean.class);
                recyclerImageDatas.addAll(bean.getData().getItems());
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }
    //创建ViewHolder
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }

    //创建适配器
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(getActivity());

            int matchParent = RecyclerView.LayoutParams.MATCH_PARENT;
            int h =400;
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(matchParent,h);

            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new RecyclerViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            //holder.imageView.setImageResource(R.mipmap.ic_launcher);
            HaiTaoBean.DataBean.ItemsBean bean = recyclerImageDatas.get(position);
            Picasso.with(getActivity()).load(bean.getCover_image_url()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return recyclerImageDatas == null ? 0 : recyclerImageDatas.size();
        }
    }

    private void setupRecyclerView() {
        //创建布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //创建一个适配器
        recyclerViewAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(recyclerViewAdapter);

    }

}
