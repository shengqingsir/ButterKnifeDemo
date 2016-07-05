package com.android.shengqing.agift;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.android.shengqing.agift.fragment.ClassifyFragment;
import com.android.shengqing.agift.fragment.GuideFragment;
import com.android.shengqing.agift.fragment.HotFragment;
import com.android.shengqing.agift.fragment.MyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.bottom_title_bar_rg)
    RadioGroup rgTab;

    private FragmentManager manager;
    private GuideFragment guideFragment;
    private Fragment currentShowFragment;
    private HotFragment hotFragment;
    private ClassifyFragment classifyFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //设置监听
        setupListener();

       manager = getSupportFragmentManager();
        //设置默认选择第一个radiobutton
        rgTab.check(R.id.guide_rb);

    }
    private void setupListener() {
        //设置RadioGroup的监听事件
        rgTab.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        FragmentTransaction transaction = manager.beginTransaction();
        switch (checkedId) {
            case R.id.guide_rb:
                if (guideFragment == null) {
                    guideFragment = new GuideFragment();
                    transaction.add(R.id.container_fg,guideFragment);
                }
                switchFragmnet(transaction,guideFragment);
                break;
            case R.id.hot_rb:
                if (hotFragment == null){
                    hotFragment = new HotFragment();
                    transaction.add(R.id.container_fg,hotFragment);
                }
                switchFragmnet(transaction,hotFragment);
                break;
            case R.id.classify_rb:
                if (classifyFragment == null){
                    classifyFragment = new ClassifyFragment();
                    transaction.add(R.id.container_fg,classifyFragment);
                }
                switchFragmnet(transaction,classifyFragment);
                break;
            case R.id.my_rb:
                if (myFragment == null){
                    myFragment = new MyFragment();
                    transaction.add(R.id.container_fg,myFragment);
                }
                switchFragmnet(transaction,myFragment);
                break;
        }
    }


    private void switchFragmnet(FragmentTransaction transaction, Fragment toFragment) {
        if (currentShowFragment==null){
            transaction.show(toFragment).commit();
        }else {
            transaction.hide(currentShowFragment).show(toFragment).commit();
        }
        currentShowFragment=toFragment;
    }

}
