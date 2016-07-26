package com.walle.meitu.view.showpic;

import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.walle.meitu.base.BaseFragment;
import com.walle.meitu.R;


public class ShowPicFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ShowPicFragment";
    private static View.OnLongClickListener mListener;

    private String mParam1;
    private ImageView pic;


    public ShowPicFragment() {
    }


    public static ShowPicFragment newInstance(String param, View.OnLongClickListener listener) {
        mListener = listener;
        ShowPicFragment fragment = new ShowPicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }


    protected void initView() {
        Glide.with(this).load(mParam1).into(pic);
        pic.setOnLongClickListener(mListener);
    }


    protected void findView(View view) {
        pic = (ImageView) view.findViewById(R.id.pic);
    }


    public int getContentViewRes() {
        return R.layout.fragment_show_pic;
    }

}
