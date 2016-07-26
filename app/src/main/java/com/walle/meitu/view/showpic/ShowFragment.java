package com.walle.meitu.view.showpic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.walle.meitu.R;
import com.walle.meitu.adapter.MenuListAdapter;
import com.walle.meitu.adapter.ShowPicAdapter;
import com.walle.meitu.base.BaseFragment;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.download.ImageDownload;
import com.walle.meitu.widget.DividerGridItemDecoration;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by yqh on 2016/7/26.
 */
public class ShowFragment extends BaseFragment implements ShowContract.View{
    private static final String PIC_LIST = "picList";
    private ViewPager mPager;
    private RecyclerView mMenuList;
    private ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist.List> picList;
    private ShowContract.Presenter mShowPresenter;

    public ShowFragment(){}

    public static ShowFragment newInstance(ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist.List> picList) {
        ShowFragment showFragment = new ShowFragment();
        Bundle arg = new Bundle();
        arg.putParcelableArrayList(PIC_LIST,picList);
        showFragment.setArguments(arg);
        return showFragment;
    }
    @Override
    protected void initView() {
        mPager.setAdapter(new ShowPicAdapter(getActivity().getSupportFragmentManager(),picList,new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopwindow(v);
                return true;
            }
        }));
        mPager.setClickable(true);
    }

    @Override
    protected void findView(View view) {
        mPager = (ViewPager)view.findViewById(R.id.show_pic);
    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_show;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            picList = getArguments().getParcelableArrayList(PIC_LIST);
        }
    }


    public void showPopwindow(final View view) {
        View contentView = LayoutInflater.from(getActivity()).inflate(
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
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindow));
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        mMenuList.setAdapter(new MenuListAdapter(getActivity(), list, new MenuListAdapter.MenuClickListener() {
            @Override
            public void onMenuClick(int pos) {
                switch (pos){
                    case 0:
                        mShowPresenter.download(picList.get(mPager.getCurrentItem()).big,getApplicationContext(),false);
                        break;
                    case 1:
                        mShowPresenter.download(picList.get(mPager.getCurrentItem()).big,getApplicationContext(),true);
                        break;
                    case 2:
                        mShowPresenter.download(picList);
                        break;
                    case 3:
                        mShowPresenter.download(picList.get(mPager.getCurrentItem()).big, getApplicationContext());
                        break;
                }
                popupWindow.dismiss();
            }
        }));

    }

    //分享单张图片
    @Override
    public void shareSingleImage(String imagePath) {
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    @Override
    public void setPresenter(ShowContract.Presenter presenter) {
        mShowPresenter = presenter;
    }
}
