package com.walle.meitu.data.remote;

enum HttpThrowType{
        CODE_300 (300,"qw"),
        CODE_404 (404,"qwe");

        private final String name;
        private final int code;

        HttpThrowType(int code,String name){
            this.name= name;
            this.code = code;
        }

        public static String getName(int code) {
            for (HttpThrowType type : values()){
                if(type.code==code)
                    return type.name;
            }
            return "网络错误";
        }
    }