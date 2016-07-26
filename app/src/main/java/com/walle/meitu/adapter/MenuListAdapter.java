package com.walle.meitu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.walle.meitu.R;

import java.util.ArrayList;

/**
 * Created by void on 16/3/12.
 */
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuListHolder> {
    private ArrayList<String> mData;
    private Context mContex;
    private MenuClickListener mListener;
    public MenuListAdapter(Context context, ArrayList<String> data,MenuClickListener listener){
        mData = data;
        mContex = context;
        mListener = listener;
    }
    @Override
    public MenuListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuListHolder(LayoutInflater.from(mContex).inflate(R.layout.item_menu,parent,false));
    }

    @Override
    public void onBindViewHolder(MenuListHolder holder, final int position) {
        holder.tv.setText(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMenuClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

     static class MenuListHolder extends RecyclerView.ViewHolder {
         TextView tv;
         public MenuListHolder(View itemView) {
             super(itemView);
             tv= (TextView) itemView;
         }
     }

    public interface MenuClickListener{
        void onMenuClick(int pos);
    }
}
