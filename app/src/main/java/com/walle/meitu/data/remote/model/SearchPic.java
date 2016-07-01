package com.walle.meitu.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 16/6/27
 */
public class SearchPic{

    public int showapi_res_code;
    public ShowapiResBody showapi_res_body;

    public static class ShowapiResBody {
        public int ret_code;
        public Pagebean pagebean;

        public static class Pagebean {
            public int allPages;
            public int currentPage;
            public int allNum;
            public int maxResult;
            public ArrayList<Contentlist> contentlist;

            public static class Contentlist implements Parcelable {
                public String typeName;
                public String title;
                public int type;
                public String itemId;
                public String ct;
                public java.util.ArrayList<List> list;
                public Contentlist(){}
                protected Contentlist(Parcel in) {
                    ct = in.readString();
                    itemId = in.readString();
                    title = in.readString();
                    type = in.readInt();
                    typeName = in.readString();
                }

                public static final Creator<Contentlist> CREATOR = new Creator<Contentlist>() {
                    @Override
                    public Contentlist createFromParcel(Parcel in) {
                        return new Contentlist(in);
                    }

                    @Override
                    public Contentlist[] newArray(int size) {
                        return new Contentlist[size];
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
                public static class List implements Parcelable{
                    public String big;
                    public String small;
                    public String middle;
                    public List(){}
                    protected List(Parcel in) {
                        big = in.readString();
                        middle = in.readString();
                        small = in.readString();
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(big);
                        dest.writeString(middle);
                        dest.writeString(small);
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    public static final Creator<List> CREATOR = new Creator<List>() {
                        @Override
                        public List createFromParcel(Parcel in) {
                            return new List(in);
                        }

                        @Override
                        public List[] newArray(int size) {
                            return new List[size];
                        }
                    };
                }
            }
        }
    }



}
