package com.walle.meitu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walle.meitu.App;
import com.walle.meitu.R;

import com.walle.meitu.data.remote.model.PicType;

import java.util.ArrayList;



public class MyMainRecyclerViewAdapter extends RecyclerView.Adapter<MyMainRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<PicType.ShowapiResBody.RootListEntity> mValues;


    public MyMainRecyclerViewAdapter(ArrayList<PicType.ShowapiResBody.RootListEntity>  items) {

        mValues = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(App.getInstance(),R.layout.main_fragment_title_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void refreshData(ArrayList<PicType.ShowapiResBody.RootListEntity>  items){
        mValues = items;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public PicType.ShowapiResBody.RootListEntity  mItem;

        public ViewHolder(View view) {
            super(view);
            mContentView = (TextView) itemView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
