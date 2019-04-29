package com.hhkj.dyedu.ui.activity;

import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
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

public class WebViewActivity extends BaseTitleHeadActivity {
    public static String TAG = "WebViewActivity";

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("课程预览");
        
        String title = getIntent().getStringExtra("title");
        if (title != null) {
            setTitle(title);
        }

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            getSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            
            //自动播放
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                webView.loadUrl("javascript:(function() { var videos = document.getElementsByTagName('video'); for(var i=0;i<videos.length;i++){videos[i].play();}})()");
            }
            
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
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
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
//        webView.loadUrl(sss);
//        webView.loadDataWithBaseURL(null, "javascript:formSubmit(" + "'" + sss + "'" + ")", "text/html", "UTF-8", null);
        
        
        String url = getIntent().getStringExtra("url");
        LogUtil.i(url);
        final String html = "<iframe frameborder=\"0\" src=\"https://v.qq.com/txp/iframe/player.html?vid=h0762gmomzw\" allowFullScreen=\"true\" height=100% width=100%></iframe>";
        LogUtil.i(url);
        if (url.startsWith("http")) {
            webView.loadUrl(getIntent().getStringExtra("url"));
            setTitle("");
        } else if (url.startsWith("<iframe")) {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    String url = getIntent().getStringExtra("url");
                    url = url.replaceAll("></iframe>", " height=100% width=100%></iframe>");
                    LogUtil.i(url);
                    webView.loadData(url, "text/html", "UTF-8");

                }
            });
        } else {
            setTitle("");
            webView.loadUrl(BuildConfig.API_HOST + getIntent().getStringExtra("url"));
        }
        
    }
    
    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }
    
    @Override
    public int setLayoutId() {
        return R.layout.activity_webview;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}
