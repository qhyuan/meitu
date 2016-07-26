package com.walle.meitu.data.remote.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by void on 16/3/9.
 */
public class ContentList implements Parcelable {


    public String ct;
    public String itemId;
    public ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist.List> list;
    public String title;
    public int type;
    public String typeName;
    public ContentList(){}
    protected ContentList(Parcel in) {
        ct = in.readString();
        itemId = in.readString();
        title = in.readString();
        type = in.readInt();
        typeName = in.readString();
    }

    public static final Creator<ContentList> CREATOR = new Creator<ContentList>() {
        @Override
        public ContentList createFromParcel(Parcel in) {
            return new ContentList(in);
        }

        @Override
        public ContentList[] newArray(int size) {
            return new ContentList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ct);
        dest.writeString(itemId);
        dest.writeString(title);
        dest.writeInt(type);
        dest.writeString(typeName);
    }
}
