package com.walle.meitu.view.showpic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.walle.meitu.R;
import com.walle.meitu.data.remote.model.SearchPic;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    private static final String TAG = "ShowActivity";
    private ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist.List>  picList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_show);
        picList = getIntent().getParcelableArrayListExtra("pic");
        ShowFragment showFragment = ShowFragment.newInstance(picList);
        new ShowPresenter(showFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,showFragment).commit();
    }
}
