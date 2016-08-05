package com.walle.meitu.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.walle.meitu.R;
import com.walle.meitu.utils.WindowUtil;

public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(getContentViewRes());
        super.onCreate(savedInstanceState);
        findView();
        initView();
    }

    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTransLucentStatus(color);
        }
    }

    private void setTransLucentStatus(int color) {
        View view = $(R.id.statusbarview);
        if(view==null)
            throw new NullPointerException("statusbarview=null");
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) WindowUtil.getStatusBarHeight(getApplicationContext());
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(color);

    }


    protected abstract int getContentViewRes();

    protected void findView() {
    }

    protected abstract void initView();

    protected boolean isFullScreen() {
        return false;
    }

    protected <T extends View> T $(int id) {
        return (T) findViewById(id);
    }
}
