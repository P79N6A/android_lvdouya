package com.hhkj.dyedu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.BuyVipAdapter;
import com.hhkj.dyedu.bean.gson.orderStatus;
import com.hhkj.dyedu.bean.gson.pay;
import com.hhkj.dyedu.bean.gson.payList;
import com.hhkj.dyedu.event.MoneyVipUpdateEvent;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.ui.fragment.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 */

public class BuyVipFragment extends BaseFragment {

    @BindView(R.id.rvVip)
    RecyclerView rvVip;
    @BindView(R.id.btPay)
    Button btPay;
    @BindView(R.id.iv01)
    ImageView iv01;
    @BindView(R.id.iv02)
    ImageView iv02;
    @BindView(R.id.tvVipTime)
    TextView tvVipTime;
    @BindView(R.id.iv03)
    ImageView iv03;

    private BuyVipAdapter buyVipAdapter;

    private boolean isGetData = false;
    private boolean isInit = false;
    private PersonalCenterActivity personalCenterActivity;
    private int cardId;
    private String payWay = "alipay";

    public void setPersonalCenterActivity(PersonalCenterActivity personalCenterActivity) {
        this.personalCenterActivity = personalCenterActivity;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_buy_vip;
    }

    @Override
    public void initView() {

        EventBus.getDefault().register(this);
        MoneyVipUpdateEvent event = new MoneyVipUpdateEvent();
//        tvMoney.setText("余额："+event.getMoney()+"元");
        tvVipTime.setText(event.getVipTimeS());


        if (!isInit) {
            isInit = true;
            buyVipAdapter = new BuyVipAdapter();

            buyVipAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    for (int i = 0; i < buyVipAdapter.getData().size(); i++) {
                        buyVipAdapter.getData().get(i).setChoose(false);
                    }
                    buyVipAdapter.getData().get(position).setChoose(true);
                    buyVipAdapter.notifyDataSetChanged();
                    cardId = buyVipAdapter.getData().get(position).getId();
                    btPay.setText("￥ " + buyVipAdapter.getData().get(position).getPrice() + "  确认支付");
                }
            });

            rvVip.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            rvVip.setAdapter(buyVipAdapter);

            if (!isGetData) {
                payList();
            }
        }


    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private void payList() {
        personalCenterActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.payList);//"获取会员购买列表"
//        httpRequest.add("---", ---);
        CallServer.getInstance().postRequest("获取会员购买列表", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        personalCenterActivity.closeLoading();
                        payList info = gson.fromJson(response, payList.class);
                        if (info.getCode() == 1) {
                            isGetData = true;
                            info.getData().get(0).setChoose(true);
                            cardId = info.getData().get(0).getId();
                            buyVipAdapter.replaceData(info.getData());
                            btPay.setText("￥ " + buyVipAdapter.getData().get(0).getPrice() + "  确认支付");

                        } else {
                            personalCenterActivity.showToast(info.getMsg());
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        personalCenterActivity.closeLoading();
                        personalCenterActivity.showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MoneyVipUpdateEvent event) {
//        tvMoney.setText("余额："+event.getMoney()+"元");
        tvVipTime.setText(event.getVipTimeS());
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        unbinder1 = ButterKnife.bind(this, rootView);
//        return rootView;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder1.unbind();
//    }

    @OnClick(R.id.btPay)
    public void onViewClicked() {

        if (payWay.equals("wallet")) {
            pay();
        } else {
            personalCenterActivity.buyVipFragmentEvent(payWay, cardId);
        }
    }

    private String out_trade_no="";
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
//                        personalCenterActivity.showToast(info.getMsg());
//
//                        personalCenterActivity.payWebViewFragmentEvent(doType);
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
    private void pay() {
        personalCenterActivity.showLoading();
        String url = AppUrl.clubCard;
        HttpRequest httpRequest = new HttpRequest(url);//"会员购买"
        httpRequest.add("type", "wallet");
        httpRequest.add("cardId", cardId);
        CallServer.getInstance().postRequest("会员购买", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        personalCenterActivity.closeLoading();
                        pay info = gson.fromJson(response, pay.class);
                        if (info.getCode() == 1) {
                            personalCenterActivity.showToast("支付成功");
                            out_trade_no = info.getData().getOut_trade_no();
                            orderStatus();
                        } else {
                            personalCenterActivity.showToast(info.getMsg());
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        personalCenterActivity.closeLoading();
                    }
                }, getContext());
    }

    @OnClick({R.id.iv01, R.id.iv02, R.id.iv03})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv01:
                iv01.setImageResource(R.mipmap.dy2_01);
                iv02.setImageResource(R.mipmap.dy_93);
                iv03.setImageResource(R.mipmap.dy_1000);
                payWay = "alipay";
                break;
            case R.id.iv02:
                payWay = "wechat";
                iv01.setImageResource(R.mipmap.dy_92);
                iv02.setImageResource(R.mipmap.dy2_02);
                iv03.setImageResource(R.mipmap.dy_1000);
                break;
            case R.id.iv03:
                payWay = "wallet";
                iv01.setImageResource(R.mipmap.dy_92);
                iv02.setImageResource(R.mipmap.dy_93);
                iv03.setImageResource(R.mipmap.dy_1001);
                break;
        }
    }
}
