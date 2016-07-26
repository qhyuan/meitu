package com.walle.meitu;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.walle.meitu.view.home.MainFragment;
import com.walle.meitu.view.home.MainPresenter;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// TODO: 2016/7/20 文件下载测试
//        String url = "http://120.210.211.197:8080/consumer/img/1.jpg";
//        DataManager.downloadFile(url, new SimpleSubscribers<File>(){});
        ////

        MainFragment mainFragment = MainFragment.newInstance();
        new MainPresenter(mainFragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_panel,mainFragment);
        transaction.commit();

    }

    private  <T extends View>  T $(int id){
        return (T)findViewById(id);
    }

}
