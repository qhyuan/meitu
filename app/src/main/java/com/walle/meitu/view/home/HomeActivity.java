package com.walle.meitu.view.home;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;

import com.walle.meitu.R;
import com.walle.meitu.base.BaseActivity;
import com.walle.meitu.data.DataManager;
import com.walle.meitu.data.remote.SimpleSubscribers;

import java.io.File;

public class HomeActivity extends BaseActivity{

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        setStatusBarColor(Color.BLUE);
        //// TODO: 2016/7/20 文件下载测试
        String url = "logo_white_fe6da1ec.png";
        DataManager.downloadFile(url, new SimpleSubscribers<File>(){});
        ////
        HomeFragment homeFragment = HomeFragment.newInstance();
        new HomePresenter(homeFragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_panel, homeFragment);
        transaction.commit();
    }


}
