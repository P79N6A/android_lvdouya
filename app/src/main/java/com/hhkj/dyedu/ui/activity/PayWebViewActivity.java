package com.hhkj.dyedu.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.SizeUtils;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.gson.pay;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadActivity;
import com.hhkj.dyedu.utils.LogUtil;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/6/14
 */

public class PayWebViewActivity extends BaseTitleHeadActivity {
    @BindView(R.id.webView)
    WebView webView;

    private String orderInfo;
    private String out_trade_no;


    private int payEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("支付宝支付");
        orderInfo = getIntent().getStringExtra("orderInfo");
        payEvent = getIntent().getIntExtra("payEvent", 0);

        setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnBack();
            }
        });

        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                LogUtil.i("拦截url" + url);

                if (url.contains("https://unitradeprod.alipaydev.com/acq/cashierReturn.htm")) {
                    //支付成功
                    turnBack();

                    //查询是否真的成功

                } else {
                    view.loadUrl(url);
                }

                return true;
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
//        webView.loadUrl("javascript:formSubmit(" + "'" + sss + "'" + ")");

        pay();

    }

    private void turnBack() {

        orderStatus();


    }

    private void pay() {
        double size = SizeUtils.dp2px(299) * 1.45;
        showLoading();
        String url;
        if (payEvent == 0) {
            url = AppUrl.pay;
        } else if (payEvent == 1) {
            //会员购买
        }
        HttpRequest httpRequest = new HttpRequest(AppUrl.pay);//"支付测试"
        httpRequest.add("orderInfo", orderInfo);
        httpRequest.add("size", size);
        CallServer.getInstance().postRequest("支付测试", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                closeLoading();
                            }
                        }, 3000);
                        pay info = gson.fromJson(response, pay.class);
                        if (info.getCode() == 1) {
                            webView.loadData(info.getData().getInfo(), "text/html", "UTF-8");
                            out_trade_no = info.getData().getOut_trade_no();
                        } else {
                            showToast(info.getMsg());
                            finish();
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                        finish();
                    }
                }, getContext());
    }

    private void orderStatus() {
        if (orderInfo == null) {
            myFinish();
            return;
        }
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.orderStatus);//"查询支付状态"
        httpRequest.add("order", out_trade_no);
        CallServer.getInstance().postRequest("查询支付状态", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                        showToast(info.getMsg());
                        if (info.getCode() == 1) {
                            setResult(100);
                        } else {
                            setResult(101);
                        }
                        finish();
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                        finish();
                    }
                }, getContext());
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_pay_webview;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        turnBack();
    }
}
