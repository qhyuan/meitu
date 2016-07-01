package com.walle.meitu.download;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by void on 16/3/10.
 */
public class ImageDownload {

    private static final String TAG = "ImageDownload";
    public static void download(final String _url, final Context context, final boolean isSetWallpaper){
        download(_url,context,isSetWallpaper,null);
    }
    public static void download(final String _url, final Context context, DownloadListener listener){
        download(_url,context,false,listener);
    }
    public static void download(final String _url, final Context context, final boolean isSetWallpaper, final DownloadListener listener) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(_url);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    if(connection.getResponseCode()!=-1) {
                        String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Imeitu";
                        Log.i(TAG,"root="+root);
                        File file = new File(root);
                        if(!file.exists())
                            file.mkdir();
                        File picFile = new File(root + "/" + _url.substring(_url.lastIndexOf("/") + 1));
                        if(picFile.exists()){
                            if (listener != null) {
                                listener.onComplete(picFile.getPath());
                            }else
                            if(isSetWallpaper) {
                                WallpaperManager mWallManager = WallpaperManager.getInstance(context);
                                mWallManager.setBitmap(BitmapFactory.decodeFile(picFile.getPath()));
                                Log.i(TAG, "file=" + picFile.getPath());
                            }else {
                                Looper.prepare();
                                Toast.makeText(context, "图片已存在", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                                return;
                            }
                        }
                        InputStream inputStream = connection.getInputStream();
                        OutputStream outputStream = new FileOutputStream(root+"/"+_url.substring(_url.lastIndexOf("/")+1));
                        byte[] buffer  = new byte[1024];
                        int len = 0;
                        while( (len=inputStream.read(buffer)) != -1){
                            outputStream.write(buffer, 0, len);
                        }
                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();
                        Looper.prepare();
                        Toast.makeText(context, "下载已完成", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                        if (listener != null) {
                            listener.onComplete(picFile.getPath());
                        }
                        if(isSetWallpaper) {
                            WallpaperManager mWallManager = WallpaperManager.getInstance(context);
                            mWallManager.setBitmap(BitmapFactory.decodeFile(picFile.getPath()));
                            Log.i(TAG, "file=" + picFile.getPath());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
    public interface  DownloadListener{
        void onComplete(String path);
    }
}
