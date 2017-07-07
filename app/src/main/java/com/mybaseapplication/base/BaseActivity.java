package com.mybaseapplication.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mybaseapplication.R;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public Activity context;
    private ImageView baseBack, baseRightIcon2, baseRightIcon1;
    private TextView baseTitle, baseRightText;
    private OnClickRightIcon1CallBack onClickRightIcon1;
    private OnClickRightIcon2CallBack onClickRightIcon2;
    private OnClickRightTextCallBack onClickRightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.inject(this);
        context = this;
        initView();
    }

    /**
     * 隐藏返回键
     */
    private void hideBack() {
        baseBack.setVisibility(View.GONE);
    }

    /**
     * 获取返回键
     */
    public ImageView getBaseBack() {
        return baseBack;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        baseRightIcon1 = (ImageView) findViewById(R.id.base_right_icon1);
        baseRightIcon2 = (ImageView) findViewById(R.id.base_right_icon2);
        baseBack = (ImageView) findViewById(R.id.base_back);
        baseTitle = (TextView) findViewById(R.id.base_title);
        baseRightText = (TextView) findViewById(R.id.base_right_text);
        baseBack.setOnClickListener(this);
    }

    /**
     * 设置标题
     *
     * @param title 标题的文本
     */
    public void setTitle(String title) {
        baseTitle.setText(title);
    }

    /**
     * 设置右侧图片1（最右侧）
     *
     * @param resId             图片的资源id
     * @param alertText         提示文本
     * @param onClickRightIcon1 点击处理接口
     */
    public void setBaseRightIcon1(int resId, String alertText, OnClickRightIcon1CallBack onClickRightIcon1) {
        this.onClickRightIcon1 = onClickRightIcon1;
        baseRightIcon1.setImageResource(resId);
        baseRightIcon1.setVisibility(View.VISIBLE);
        baseRightIcon1.setOnClickListener(this);
        //语音辅助提示的时候读取的信息
        baseRightIcon1.setContentDescription(alertText);
    }

    /**
     * 设置右侧图片2（右数第二个图片）
     *
     * @param resId     图片的资源id
     * @param alertText 提示文本
     */
    public void setBaseRightIcon2(int resId, String alertText, OnClickRightIcon2CallBack onClickRightIcon2) {
        this.onClickRightIcon2 = onClickRightIcon2;
        baseRightIcon2.setImageResource(resId);
        baseRightIcon2.setVisibility(View.VISIBLE);
        baseRightIcon2.setOnClickListener(this);
        //语音辅助提示的时候读取的信息
        baseRightIcon2.setContentDescription(alertText);
    }

    /**
     * 设置右侧文本信息
     *
     * @param text 所需要设置的文本
     */
    public void setBaseRightText(String text, OnClickRightTextCallBack onClickRightText) {
        this.onClickRightText = onClickRightText;
        baseRightText.setText(text);
        baseRightText.setVisibility(View.VISIBLE);
        baseRightText.setOnClickListener(this);
    }

    /**
     * 引用头部布局
     *
     * @param layoutId 布局id
     */
    public void setBaseContentView(int layoutId) {
        //当子布局高度值不足ScrollView时，用这个方法可以充满ScrollView，防止布局无法显示
        ((ScrollView) findViewById(R.id.base_scroll_view)).setFillViewport(true);
        LinearLayout layout = (LinearLayout) findViewById(R.id.base_main_layout);

        //获取布局，并在BaseActivity基础上显示
        final View view = getLayoutInflater().inflate(layoutId, null);
        //关闭键盘
        hideKeyBoard();
        //给EditText的父控件设置焦点，防止键盘自动弹出
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.addView(view, params);
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyBoard() {
        View view = ((Activity) context).getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回按键
            case R.id.base_back:
                finish();
                break;

            //图片1
            case R.id.base_right_icon1:
                onClickRightIcon1.clickRightIcon1();
                break;

            //图片2
            case R.id.base_right_icon2:
                onClickRightIcon2.clickRightIcon2();
                break;

            //右侧文本
            case R.id.base_right_text:
                onClickRightText.clickRightText();
                break;

            default:
                break;
        }
    }

    /**
     * 图片一点击回调接口
     */
    public interface OnClickRightIcon1CallBack {
        void clickRightIcon1();
    }

    /**
     * 图片二点击回调接口
     */
    public interface OnClickRightIcon2CallBack {
        void clickRightIcon2();
    }

    /**
     * 右侧文字点击回调接口
     */
    public interface OnClickRightTextCallBack {
        void clickRightText();
    }
}
