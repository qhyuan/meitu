package com.walle.meitu.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.walle.meitu.App;
import com.walle.meitu.R;
import com.walle.meitu.ShowActivity;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.download.ImageDownload;
import com.walle.meitu.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class PicListRecyclerViewAdapter extends RecyclerView.Adapter<PicListRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> mData;
    private final App mContext;

    public PicListRecyclerViewAdapter(ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> items) {
        mData = items;
        mContext = App.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_piclist2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        LogUtil.i("title="+mData.get(position).title);
        holder.title.setText(mData.get(position).title);
        Uri uri = Uri.parse(mData.get(position).list.get(0).middle);
        Glide.with(App.getInstance()).load(uri).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img);
        if(position==mData.size()-1){
//            mLoadListener.loadNextPage(mAllData.showapi_res_body.pagebean.currentPage+1);
        }
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDownload.download(mData.get(position).list.get(0).big,App.getInstance(),true);
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowActivity.class);
                intent.putParcelableArrayListExtra("pic",mData.get(position).list);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void refreshData(ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> data){
        mData.clear();
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> data) {
        int size = mData.size();
        mData.addAll(data);
        notifyItemRangeChanged(size-1,data.size());
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        ImageView download;
        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.pic);
            title = (TextView)itemView.findViewById(R.id.title);
            download = (ImageView) itemView.findViewById(R.id.download);
        }
    }
}
