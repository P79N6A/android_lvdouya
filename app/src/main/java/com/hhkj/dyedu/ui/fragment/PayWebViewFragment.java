package com.hhkj.dyedu.ui.fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.orderStatus;
import com.hhkj.dyedu.bean.gson.pay;
import com.hhkj.dyedu.event.MoneyVipUpdateEvent;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.ui.fragment.base.BaseFragment;
import com.hhkj.dyedu.ui.utils.QRCode;
import com.hhkj.dyedu.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/6/22
 */

public class PayWebViewFragment extends BaseFragment {

    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.ivQrCode)
    ImageView ivQrCode;
    @BindView(R.id.tvMessage)
    TextView tvMessage;

    private PersonalCenterActivity personalCenterActivity;
    private String type;//支付类型
    private int doType = 0;//1 vip支付  2充值支付  3购物车支付
    //vip充值参数
    private int cardId;
    //充值参数
    private String money;
    //课程购买订单
    private String orderInfo;
    //
    private String out_trade_no;
    private CountDownTimer countDownTimer;

    public void setMoney(String money) {
        this.money = money;
    }

    public void setDoType(int doType) {
        this.doType = doType;
    }

    public void setPersonalCenterActivity(PersonalCenterActivity personalCenterActivity) {
        this.personalCenterActivity = personalCenterActivity;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_pay_webview;
    }

    @Override
    public void initView() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        tvMoney.setText("");
        if (type.equals("alipay")) {
//            支付宝
            webView.setVisibility(View.VISIBLE);
            ivQrCode.setVisibility(View.GONE);
            webView.getSettings().setDefaultTextEncodingName("UTF-8");
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    LogUtil.i("拦截url" + url);

                    if (url.contains("https://unitradeprod.alipaydev.com/acq/cashierReturn.htm")) {
                        //支付成功
                        orderStatus();
                    } else {
                        view.loadUrl(url);
                    }

                    return true;
                }
            });
            webView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
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

        } else {
            webView.setVisibility(View.GONE);
            ivQrCode.setVisibility(View.VISIBLE);
            ivQrCode.setImageResource(0);

            countDownTimer = new CountDownTimer(30 * 1000, 3 * 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    orderStatus2();
                }

                @Override
                public void onFinish() {
                    countDownTimer.start();
                }
            };

        }
        pay();
    }

    private void orderStatus() {
        personalCenterActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.orderStatus);//"查询支付状态"
        httpRequest.add("order", out_trade_no);
        CallServer.getInstance().postRequest("查询支付状态", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        personalCenterActivity.closeLoading();
                        orderStatus info = gson.fromJson(response, orderStatus.class);
                        personalCenterActivity.showToast(info.getMsg());

                        personalCenterActivity.payWebViewFragmentEvent(doType);
                        MoneyVipUpdateEvent event = new MoneyVipUpdateEvent(info.getData().getMoney(), info.getData().getExpiration());
                        EventBus.getDefault().post(event);
//                        webView.clearView();
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        personalCenterActivity.closeLoading();
                        personalCenterActivity.showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }

    private void orderStatus2() {
        HttpRequest httpRequest = new HttpRequest(AppUrl.orderStatus);//"查询支付状态"
        httpRequest.add("order", out_trade_no);
        CallServer.getInstance().postRequest("查询支付状态", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        orderStatus info = gson.fromJson(response, orderStatus.class);
                        if (info.getCode() == 1) {
                            //成功
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                                countDownTimer = null;
                            }
                            personalCenterActivity.showToast(info.getMsg());
                            personalCenterActivity.payWebViewFragmentEvent(doType);
                            MoneyVipUpdateEvent event = new MoneyVipUpdateEvent(info.getData().getMoney(), info.getData().getExpiration());
                            EventBus.getDefault().post(event);
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {

                    }
                }, getContext());
    }

    private void pay() {
        double size = 700;
        personalCenterActivity.showLoading();
        if (type.equals("alipay")) {
            tvMessage.setText("请用支付宝扫描二维码进行支付");
        } else {
            tvMessage.setText("请用微信扫描二维码进行支付");
        }
        String url = "";

        if (doType == 1) {
            //vip
            url = AppUrl.clubCard;
        } else if (doType == 2) {
            //充值
            url = AppUrl.recharge;
        } else if (doType == 3) {
            //充值
            url = AppUrl.pay;
        }

        HttpRequest httpRequest = new HttpRequest(url);//"会员购买"
//        httpRequest.add("orderInfo", orderInfo);
        httpRequest.add("size", size);
        httpRequest.add("type", type);

        if (doType == 1) {
            httpRequest.add("cardId", cardId);
        } else if (doType == 2) {
            httpRequest.add("money", money);
        } else if (doType == 3) {
            httpRequest.add("orderInfo", orderInfo);
        }

        CallServer.getInstance().postRequest("会员购买", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {

                        pay info = gson.fromJson(response, pay.class);
                        if (info.getCode() == 1) {

                            tvMoney.setText("￥ " + info.getData().getMoney());
                            out_trade_no = info.getData().getOut_trade_no();

                            if (type.equals("alipay")) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        personalCenterActivity.closeLoading();
                                    }
                                }, 3000);
                                webView.loadData(info.getData().getInfo(), "text/html", "UTF-8");
                            } else {
                                personalCenterActivity.closeLoading();
                                ivQrCode.setImageBitmap(QRCode.createQRCode(info.getData().getInfo2(), 400));
                                countDownTimer.start();
                            }

                        } else {
                            personalCenterActivity.closeLoading();
                            personalCenterActivity.showToast(info.getMsg());
                            personalCenterActivity.payWebViewFragmentEvent(doType);
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        personalCenterActivity.closeLoading();
                        personalCenterActivity.showToast(getString(R.string.toast_server_error));
                        personalCenterActivity.payWebViewFragmentEvent(doType);
                    }
                }, getContext());
    }

    @Override
    public void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        super.onDestroy();
    }

}
