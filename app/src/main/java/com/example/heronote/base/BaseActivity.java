package com.example.heronote.base;

/**
 * Created by Jack on 2018/2/14.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.heronote.util.LogUtil;
import android.view.View;

import com.example.heronote.R;

/**
 * Created by Jack on 2017/11/6.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过BaseActivity获取当前类的信息
        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //显示Toast推荐使用Utils.toast(String s)
    //该方法不依赖活动实例，也可在碎片中使用
    //Utils类中还有关于Log、Snackbar、Listener的方法
    //知悉后可自行删除该注释
//    public void showToast(String text){
//        LogUtil.d("BaseActivity:", text);
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onClick(View view) {}

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    protected void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    protected void initActionBar(int i) {
        Toolbar toolbar = (Toolbar) findViewById(i);
        setSupportActionBar(toolbar);
    }

    protected void initActionBar(int i, String title) {
        Toolbar toolbar = (Toolbar) findViewById(i);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    protected void initActionBar(int i, String title, int icon) {
        Toolbar toolbar = (Toolbar) findViewById(i);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(icon);
        }
    }

    protected void initListenerToThis(int... ints) {
        for (int i : ints) {
            findViewById(i).setOnClickListener(this);
        }
    }

    protected void initListenerToThis(View view, int... ints) {
        for (int i : ints) {
            view.findViewById(i).setOnClickListener(this);
        }
    }

    protected void initLongClickListenerToThis(int... ints) {
        for (int i : ints) {
            findViewById(i).setOnLongClickListener(this);
        }
    }

    protected void initLongClickListenerToThis(View view, int... ints) {
        for (int i : ints) {
            view.findViewById(i).setOnLongClickListener(this);
        }
    }

    protected void goToNewAct(java.lang.Class<?> cls) {
        Intent intent = new Intent(BaseApplication.getContext(), cls);
        startActivity(intent);
    }

    protected void goToNewAct(java.lang.Class<?> cls, String name, Parcelable parcelable) {
        Intent intent = new Intent(BaseApplication.getContext(), cls);
        intent.putExtra(name, parcelable);
        startActivity(intent);
    }
}

