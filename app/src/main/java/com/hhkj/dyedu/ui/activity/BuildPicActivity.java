package com.hhkj.dyedu.ui.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hhkj.dyedu.BuildConfig;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadActivity;
import com.hhkj.dyedu.utils.LogUtil;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/6/14
 */

public class BuildPicActivity extends BaseTitleHeadActivity {
    @BindView(R.id.webView)
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTitle("课程预览");
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //自动播放
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { var videos = document.getElementsByTagName('video'); for(var i=0;i<videos.length;i++){videos[i].play();}})()");
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
//                LogUtil.i("进度啊"+newProgress);


            }
        });

        WebSettings webSettings = webView.getSettings();
        //设置浏览标识
//        webSettings.setUserAgentString("android");
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        //设定支持viewport
        webSettings.setUseWideViewPort(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
//        webView.loadUrl(sss);
//        webView.loadDataWithBaseURL(null, "javascript:formSubmit(" + "'" + sss + "'" + ")", "text/html", "UTF-8", null);


        String url =getIntent().getStringExtra("url");
        if(!url.contains("http")){
            webView.loadUrl(BuildConfig.API_HOST + getIntent().getStringExtra("url"));
        }else {
            webView.loadUrl(getIntent().getStringExtra("url"));
            setTitle("");
        }
        LogUtil.i("url" + BuildConfig.API_HOST + getIntent().getStringExtra("url"));


    }

    @Override
    protected void onDestroy() {
        //为了使WebView退出时音频或视频关闭
        webView.destroy();
        super.onDestroy();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_build_pic;
    }

}
