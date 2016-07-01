package com.walle.meitu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.walle.meitu.data.remote.model.SearchPic;


import java.io.File;
import java.util.ArrayList;

public class ShowActivity extends FragmentActivity {

    private static final String TAG = "ShowActivity";
    private ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist.List> picList;
    private ViewPager mPager;
    private RecyclerView mMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 隐藏应用程序的标题栏，即当前activity的标题栏 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show);
        picList = getIntent().getParcelableArrayListExtra("pic");
        findView();
//        initView();
    }

    private void findView() {
        mPager = (ViewPager)findViewById(R.id.show_pic);
    }
   /* private void initView() {
        mPager.setAdapter(new ShowPicAdapter(getSupportFragmentManager(),picList,new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(TAG,"onLongClick");
                showPopwindow(v);
                return true;
            }
        }));
        mPager.setClickable(true);
    }
    private void showPopwindow(final View view) {
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.showpic_popwindow, null);
        mMenuList = (RecyclerView)contentView.findViewById(R.id.menulist);
        mMenuList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        ArrayList<String> list = new ArrayList<>();
        list.add("下载");
        list.add("设为壁纸");
        list.add("下载全部");
        list.add("分享");

        mMenuList.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        contentView.setLayoutParams(params);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindow));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        mMenuList.setAdapter(new MenuListAdapter(getApplicationContext(), list, new MenuListAdapter.MenuClickListener() {
            @Override
            public void onMenuClick(int pos) {
                Log.i(TAG,"pos="+pos);
                switch (pos){
                    case 0:
                        ImageDownload.download(picList.get(mPager.getCurrentItem()).big,getApplicationContext(),false);
                        break;
                    case 1:
                        ImageDownload.download(picList.get(mPager.getCurrentItem()).big,getApplicationContext(),true);
                        break;
                    case 2:
                        for (PictureBean pic :picList){
                            ImageDownload.download(pic.big,getApplicationContext(),false);
                        }
                        break;
                    case 3:
                        ImageDownload.download(picList.get(mPager.getCurrentItem()).big, getApplicationContext(), new ImageDownload.DownloadListener() {
                            @Override
                            public void onComplete(String path) {
                                shareSingleImage(path);
                            }
                        });

                        break;
                }
                popupWindow.dismiss();
            }
        }));

    }*/
    //分享单张图片
    public void shareSingleImage(String imagePath) {
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Log.d(TAG, "uri:" + imageUri);  //输出：file:///storage/emulated/0/test.jpg
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
