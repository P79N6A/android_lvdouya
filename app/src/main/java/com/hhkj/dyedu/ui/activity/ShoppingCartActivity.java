package com.hhkj.dyedu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.ShopCarAdapter;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.gson.pay;
import com.hhkj.dyedu.bean.gson.shopcarInfo;
import com.hhkj.dyedu.callback.NumChangeListener;
import com.hhkj.dyedu.callback.PayTypeListener;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadListActivity;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.view.ChangeNumDialog;
import com.hhkj.dyedu.view.PayDialog;
import com.hhkj.dyedu.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/13
 */

public class ShoppingCartActivity extends BaseTitleHeadListActivity {

    @BindView(R.id.ivChooseAll)
    ImageView ivChooseAll;
    @BindView(R.id.tvAllMoney)
    TextView tvAllMoney;

    private boolean isChooseAll;
    private ShopCarAdapter shopCarAdapter;
    private ChangeNumDialog.Builder builder;
    private ChangeNumDialog changeNumDialog;
    private PayDialog.Builder payDialogBuilder;
    private PayDialog payDialog;
    private String orderInfo = "";

    @Override
    public int setLayoutId() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("购物车");

        shopCarAdapter = new ShopCarAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(shopCarAdapter);
        recyclerView.getItemAnimator().setChangeDuration(0);//关闭局部刷新动画


        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) smartRefreshLayout.getLayoutParams();
        layoutParams.height = 0;
        layoutParams.weight = 1;
        smartRefreshLayout.setLayoutParams(layoutParams);
        smartRefreshLayout.setPadding(SizeUtils.dp2px(14), 0, SizeUtils.dp2px(14), 0);
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadMore(false);

        shopCarAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                shopcarInfo.DataBean item = shopCarAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tvAdd:
                        item.setNum(item.getNum() + 1);
                        adapter.notifyItemChanged(position);
//                        adapter.notifyDataSetChanged();

                        calculationMoney();
                        shopcarCount(item.getShopId(), item.getNum());
                        break;
                    case R.id.tvReduce:
                        int num = item.getNum() - 1;
                        if (num < 1) {
                            num = 1;
                        }
                        item.setNum(num);
                        adapter.notifyItemChanged(position);
                        calculationMoney();


                        shopcarCount(item.getShopId(), item.getNum());

                        break;
                    case R.id.num:

                        if (builder == null) {
                            builder = new ChangeNumDialog.Builder(getContext());
                        }
                        builder.setPos(position);

                        builder.setNum(item.getNum());

                        builder.setOnClickListener(new NumChangeListener() {
                            @Override
                            public void numChange(int num, int pos) {
                                shopCarAdapter.getData().get(pos).setNum(num);
                                shopCarAdapter.notifyItemChanged(pos);
                                changeNumDialog.dismiss();

                                calculationMoney();
                                shopcarCount(shopCarAdapter.getData().get(pos).getShopId(), shopCarAdapter.getData().get(pos).getNum());
                            }
                        });
                        changeNumDialog = builder.create();
                        changeNumDialog.show();

                        break;
                    case R.id.ivChoose:
                        item.setChoose(!item.isChoose());
                        adapter.notifyItemChanged(position);
                        int shopSize = shopCarAdapter.getData().size();
                        boolean hava = false;
                        for (int i = 0; i < shopSize; i++) {
                            if (!shopCarAdapter.getData().get(i).isChoose()) {
//                                LogUtil.i("找到未选中的");
                                isChooseAll = false;
                                hava = true;
                                ivChooseAll.setImageResource(R.mipmap.zx_108);
                                break;
                            }
                        }
                        if (!hava) {
                            isChooseAll = true;
                            ivChooseAll.setImageResource(R.mipmap.zx_107);
                        }
                        calculationMoney();

                        break;

                    case R.id.del:
                        shopcarDel(item.getShopId());
                        shopCarAdapter.remove(position);
                        calculationMoney();
                        if (shopCarAdapter.getData().size() > 0) {
                            setNoDataVisibility(View.GONE);
                        } else {
                            setNoDataVisibility(View.VISIBLE);
                        }
                        break;
                }


            }
        });

        shopcarInfo();
    }

    private void calculationMoney() {

        double money = 0;
        int shopSize = shopCarAdapter.getData().size();
        for (int i = 0; i < shopSize; i++) {
            if (shopCarAdapter.getData().get(i).isChoose()) {
                money = money + shopCarAdapter.getData().get(i).getNum() * shopCarAdapter.getData().get(i).getPrice();
            }
        }
        tvAllMoney.setText("￥ " + money);
    }

    private void shopcarCount(int shopId, int num) {

        CallServer.getInstance().cancelBySign(AppUrl.shopcarCount);
//        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.shopcarCount);//"购物车同步"
        httpRequest.add("shopId", shopId);
        httpRequest.add("num", num);
        httpRequest.setCancelSign(AppUrl.shopcarCount);
        CallServer.getInstance().postRequest("购物车同步", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
//                        closeLoading();
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                        if (info.getCode() == 1) {
                        } else {
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());

    }

    private void shopcarDel(int shopId) {
//        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.shopcarDel);//"移除购物车"
        httpRequest.add("shopId", shopId);
        CallServer.getInstance().postRequest("移除购物车", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
//                        closeLoading();
//                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
//                        if (info.getCode()==1) {
//                        } else {
//                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
//                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());

    }

    private void shopcarInfo() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.shopcarInfo);//"购物车信息"
//        httpRequest.add("---", ---);
        CallServer.getInstance().postRequest("购物车信息", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        shopcarInfo info = gson.fromJson(response, shopcarInfo.class);
                        if (info.getCode() == 1) {
                            shopCarAdapter.replaceData(info.getData());
                        } else {
                            showToast(info.getMsg());
                        }

                        if (shopCarAdapter.getData().size() > 0) {
                            setNoDataVisibility(View.GONE);
                        } else {
                            setNoDataVisibility(View.VISIBLE);
                        }
                        //重置
                        tvAllMoney.setText("￥ " + "0.0");
                        isChooseAll = false;
                        ivChooseAll.setImageResource(R.mipmap.zx_108);
                        onViewClicked();//点击全选按钮

                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }

    @OnClick(R.id.ivChooseAll)
    public void onViewClicked() {

        isChooseAll = !isChooseAll;
        int shopSize = shopCarAdapter.getData().size();
        for (int i = 0; i < shopSize; i++) {
            shopCarAdapter.getData().get(i).setChoose(isChooseAll);
        }
        shopCarAdapter.notifyDataSetChanged();
        if (isChooseAll) {
            ivChooseAll.setImageResource(R.mipmap.zx_107);
        } else {
            ivChooseAll.setImageResource(R.mipmap.zx_108);
        }
        calculationMoney();
    }

    @OnClick(R.id.btBuy)
    public void btBuy() {

        List<shopcarInfo.DataBean> list = new ArrayList<>();


        int size = shopCarAdapter.getData().size();
        for (int i = 0; i < size; i++) {
            if (shopCarAdapter.getData().get(i).isChoose()) {
                LogUtil.i("数量" + shopCarAdapter.getData().get(i).getNum());
                list.add(shopCarAdapter.getData().get(i));
            }
        }

        if (list.size() == 0) {
            showToast("请选择需要购买的课程");
            return;
        }
        Gson gson = new GsonBuilder().setExclusionStrategies(
                new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        //过滤掉字段名包含"id","address"的字段
                        return !((f.getName().equals("shopId")) | (f.getName().equals("num")));
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 过滤掉 类名包含 Bean的类
                        return false;
                    }
                }).create();

        orderInfo = gson.toJson(list);
        LogUtil.i("订单啊啊啊啊啊" + orderInfo);
        if (payDialogBuilder == null) {
            payDialogBuilder = new PayDialog.Builder(getContext());
            payDialogBuilder.setOnClickListener(new PayTypeListener() {
                @Override
                public void payType(int type) {

                    if (type == PayTypeListener.TYPE_ALI) {
                        startActivityForResult(new Intent(getContext(), PersonalCenterActivity.class).putExtra("orderInfo", orderInfo).putExtra("PayType", "alipay").putExtra("doType", 1), 100);
//                        jumpToActivity(PayWebViewActivity.class);
                    } else if (type == PayTypeListener.TYPE_WE_CHAT) {
                    } else if (type == PayTypeListener.TYPE_WALLET) {
                        pay();

                    }
                    payDialog.dismiss();
                }
            });
        }

        payDialog = payDialogBuilder.create();
        payDialog.show();

    }

    private void pay() {
        showLoading();
        double size = SizeUtils.dp2px(199) * 1.7;
        HttpRequest httpRequest = new HttpRequest(AppUrl.pay);
        httpRequest.add("size", size);
        httpRequest.add("type", "wallet");
        httpRequest.add("orderInfo", orderInfo);

        CallServer.getInstance().postRequest("会员购买", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        pay info = gson.fromJson(response, pay.class);
                        showToast(info.getMsg());
                        if (info.getCode() == 1) {
                            shopcarInfo();
                        } else {
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 10086) {
            //支付成功
//            刷新
            shopcarInfo();
        }


    }
}
