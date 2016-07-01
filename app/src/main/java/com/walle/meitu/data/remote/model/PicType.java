package com.walle.meitu.data.remote.model;

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

            public static class SecondListEntity {
                public int id;
                public String name;
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
