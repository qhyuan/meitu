package com.walle.meitu.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Created by void on 16/6/30
 */
public class PicType implements IResult<PicType.ShowapiResBody>{

    public int showapi_res_code;
    public String showapi_res_error;
    public ShowapiResBody showapi_res_body;

    @Override
    public int getResultCode() {
        return showapi_res_code;
    }

    @Override
    public ShowapiResBody getData() {
        return showapi_res_body;
    }

    public static class ShowapiResBody {
        public int ret_code;
        @SerializedName("list")
        public ArrayList<RootListEntity> rootList;

        public static class RootListEntity {
            public String name;
            @SerializedName("list")
            public ArrayList<SecondListEntity> secondList;

            public static class SecondListEntity implements Parcelable {
                public int id;
                public String name;

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.id);
                    dest.writeString(this.name);
                }

                public SecondListEntity() {
                }

                protected SecondListEntity(Parcel in) {
                    this.id = in.readInt();
                    this.name = in.readString();
                }

                public static final Parcelable.Creator<SecondListEntity> CREATOR = new Parcelable.Creator<SecondListEntity>() {
                    public SecondListEntity createFromParcel(Parcel source) {
                        return new SecondListEntity(source);
                    }

                    public SecondListEntity[] newArray(int size) {
                        return new SecondListEntity[size];
                    }
                };
            }
        }
    }

    @Override
    public String toString() {
        return "PicType{" +
                "showapi_res_code=" + showapi_res_code +
                ", showapi_res_error='" + showapi_res_error + '\'' +
                ", showapi_res_body=" + showapi_res_body +
                '}';
    }
}
