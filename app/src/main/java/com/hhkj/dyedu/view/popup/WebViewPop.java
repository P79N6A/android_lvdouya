package com.hhkj.dyedu.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.view.MyPopupWindow;
import com.hhkj.dyedu.view.ToolsPop;
import com.hhkj.dyedu.utils.LogUtil;

/**
 * Created by zangyi_shuai_ge on 2018/5/30
 * 黑板
 */

public class WebViewPop extends ToolsPop {

    private RelativeLayout relativeLayout;

    private WebView webView;

    private String url;
    private boolean isShow = false;

    public WebViewPop(Context context, String url) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_webview, null);
        int width = ScreenUtils.getScreenWidth();
        int height = ScreenUtils.getScreenHeight();
        popupWindow = new MyPopupWindow(view, width, height, false);

        this.url = url;

        relativeLayout = view.findViewById(R.id.layoutLeft);
        webView = view.findViewById(R.id.webView);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        initWebView();

        webView.loadUrl(url);
    }

    private void initWebView() {


        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //自动播放
            public void onPageFinished(WebView view, String url) {
//                webView.loadUrl("javascript:(function() { var videos = document.getElementsByTagName('video'); for(var i=0;i<videos.length;i++){videos[i].play();}})()");
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                LogUtil.i("进度啊" + newProgress);
            }
        });

        WebSettings webSettings = webView.getSettings();
        //设置浏览标识
        webSettings.setUserAgentString("android");
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        //设定支持viewport
        webSettings.setUseWideViewPort(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public void showAtLocation(View view) {

        if (!isShow) {
            isShow = true;
            webView.loadUrl(url);
        }

        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (location[1] < 0) {
            location[1] = 0;
        }

        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - popupWindow.getWidth(), location[1]);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
        popupWindow.isShowing();
//        popupWindow.showAtLocation(view, RIGHT|TOP,-width,-height);
    }

}
