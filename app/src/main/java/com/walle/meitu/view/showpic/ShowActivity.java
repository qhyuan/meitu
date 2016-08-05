package com.walle.meitu.view.showpic;

import com.walle.meitu.R;
import com.walle.meitu.base.BaseActivity;
import com.walle.meitu.data.remote.model.SearchPic;

import java.util.ArrayList;

public class ShowActivity extends BaseActivity {

    private ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist.List>  picList;


    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_show;
    }
    @Override
    protected void initView() {
        picList = getIntent().getParcelableArrayListExtra("pic");
        ShowFragment showFragment = ShowFragment.newInstance(picList);
        new ShowPresenter(showFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,showFragment).commit();
    }

}
