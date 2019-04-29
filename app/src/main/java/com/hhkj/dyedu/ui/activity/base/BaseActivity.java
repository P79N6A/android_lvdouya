package com.hhkj.dyedu.ui.activity.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.ClipboardManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hhkj.dyedu.GlobalVariable;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.ui.activity.user.LoginActivity;
import com.hhkj.dyedu.ui.utils.LanguageUtil;
import com.zuoni.common.dialog.loading.LoadingDialog;
import com.hhkj.dyedu.utils.LogUtil;
import com.zuoni.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;


/**
 * Created by zangyi_shuai_ge on 2017/4/21
 * Activity 基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    
    private Context mContext;
    //弹窗
    private LoadingDialog loadingDialog;
    private LoadingDialog.Builder builder;
    //广播
    private BaseActivityBroadcastReceiver baseActivityBroadcastReceiver;
    
    public String TAG = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setLanguage();
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);//强制竖屏
        mContext = BaseActivity.this;
        TAG = this.getClass().getName();
//        setLanguage();
        initLoadingDialog();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        registerBroadcast();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        unregisterBroadcast();
        
    }
    
    @Override
    protected void onDestroy() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        mContext = null;
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (clickBlankArea2HideSoftInput) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm == null) {
                        return super.dispatchTouchEvent(ev);
                    }
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    
    /**
     * 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null & (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
    
    private boolean clickBlankArea2HideSoftInput = false;
    
    /**
     * @param clickBlankArea2HideSoftInput 是否点击其他区域隐藏软键盘
     */
    public void setClickBlankArea2HideSoftInput(boolean clickBlankArea2HideSoftInput) {
        this.clickBlankArea2HideSoftInput = clickBlankArea2HideSoftInput;
    }
    
    public void setLanguage() {
        String cacheLanguage = CacheUtils.getLanguage();
        String language = getApplication().getResources().getConfiguration().locale.getLanguage();
        //当前语言和设置的语言不相等时候重新设置下语言
        if (!cacheLanguage.equals(language)) {
            if (cacheLanguage.equals(new Locale("zh").getLanguage())) {
                LanguageUtil.set(false, getApplication());
            } else {
                LanguageUtil.set(true, getApplication());
            }
        }
    }
    
    public List<String> getFalseData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        return list;
    }
    
    public void setSize(View view, int height, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }
    
    public abstract int setLayoutId();
    
    private void initLoadingDialog() {
        builder = new LoadingDialog.Builder(BaseActivity.this);
        builder.setMessage("载入中...");
        loadingDialog = builder.create();
    }
    
    /**
     * Loading
     */
    public void showLoading() {
        loadingDialog.show();
    }
    
    public void closeLoading() {
        loadingDialog.dismiss();
        setLoadingDialogMessage("载入中...");
    }
    
    public void setLoadingDialogMessage(String message) {
        builder.setTvMessage(message);
    }
    
    public Context getContext() {
        return mContext;
    }
    
    /**
     * Toast  Snackbar
     */
    public void showToast(String message) {
        ToastUtils.showToast(BaseActivity.this.getApplicationContext(), message);
    }
    
    public void showSnackbar(View mView, String message) {
        Snackbar.make(mView, message, Snackbar.LENGTH_SHORT)
//                .setAction("", click listener)
                .show();
    }
    
    /**
     * 普通的跳转界面
     */
    public void jumpToActivity(Class<?> cls) {
        Intent mIntent = new Intent(BaseActivity.this, cls);
        myStartActivity(mIntent);
    }
    
    /**
     * 添加了动画效果的跳转和销毁
     */
    public void myStartActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }
    
    @Override
    public void onBackPressed() {
        myFinish();
    }
    
    /**
     * 注册广播
     */
    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GlobalVariable.BROADCAST_TOKEN_ERROR);
        intentFilter.addAction(GlobalVariable.BROADCAST_SERVER_EXCEPTION);
        baseActivityBroadcastReceiver = new BaseActivityBroadcastReceiver();
        registerReceiver(baseActivityBroadcastReceiver, intentFilter);
    }
    
    /**
     * 取消注册广播
     */
    private void unregisterBroadcast() {
        if (baseActivityBroadcastReceiver != null) {
            unregisterReceiver(baseActivityBroadcastReceiver);
            baseActivityBroadcastReceiver = null;
        }
    }
    
    /**
     * 退出界面
     */
    public void myFinish() {
        finish();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
    
    /**
     * 基类广播接收器
     * 用于处理异常 弹出提示框
     * token 异常: token_error
     * 接口异常 : server_exception
     */
    private class BaseActivityBroadcastReceiver extends BroadcastReceiver {
        private AlertDialog alertDialog;
        private AlertDialog.Builder builder;
        
        @Override
        public void onReceive(final Context context, final Intent intent) {
            //动态注册的广播 context 是Activity
            LogUtil.i("广播" + intent.getAction());
            if (intent.getAction() == null) {
                return;
            }
            String action = intent.getAction();
            
            switch (action) {
                case GlobalVariable.BROADCAST_TOKEN_ERROR:
                    //token异常
                    if (alertDialog == null || !alertDialog.isShowing()) {
                        
                        builder = new AlertDialog.Builder(context);
                        builder.setTitle("提示");
                        builder.setMessage("账号密码失效,请重新登录");
                        builder.setCancelable(false);
                        builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CacheUtils.setToken("");
                                CacheUtils.setLogin(false);
                                
                                ActivityCollector.finishAll();
                                Intent mIntent = new Intent(context, LoginActivity.class);
                                context.startActivity(mIntent);
                                alertDialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CacheUtils.setLogin(false);
                                ActivityCollector.finishAll();
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog = builder.create();
                        alertDialog.show();
                    }
                    break;
                case GlobalVariable.BROADCAST_SERVER_EXCEPTION:
                    //服务器异常
                    if (alertDialog == null || !alertDialog.isShowing()) {
                        //接收到广播的时候
                        builder = new AlertDialog.Builder(context);
                        builder.setTitle("错误日志(仅测试版本显示)");
                        builder.setMessage(intent.getStringExtra("log"));
                        builder.setCancelable(false);
                        builder.setPositiveButton("知道了", null);
                        builder.setNegativeButton("复制报错日志", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                // 将文本内容放到系统剪贴板里。
                                cm.setText(intent.getStringExtra("log"));
                                showToast("复制成功");
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog = builder.create();
                        alertDialog.show();
                    }
                    break;
            }
        }
    }
}
