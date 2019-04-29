package com.hhkj.dyedu.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.BillAdapter;
import com.hhkj.dyedu.adapter.base.BaseHeaderAdapter;
import com.hhkj.dyedu.bean.gson.getUserBill;
import com.hhkj.dyedu.bean.model.Bill;
import com.hhkj.dyedu.bean.model.PinnedHeaderEntity;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.ui.fragment.base.BaseFragment;
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 */

public class BillFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ivNoData)
    ImageView ivNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.layoutNoData)
    LinearLayout layoutNoData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    List<PinnedHeaderEntity<Bill>> data = new ArrayList<>();
    private PersonalCenterActivity personalCenterActivity;
    //订单列表 不包括头部
    private boolean isInit = false;
    private boolean isGet = false;


    private BillAdapter billAdapter;

    public void setPersonalCenterActivity(PersonalCenterActivity personalCenterActivity) {
        this.personalCenterActivity = personalCenterActivity;
    }

    @Override
    public int setLayoutId() {
        return R.layout.base_default_fragment_list;
    }

    @Override
    public void initView() {
        if (!isInit) {
            isInit = true;
            layoutNoData.setVisibility(View.GONE);
            billAdapter = new BillAdapter(data);


            recyclerView
                    .addItemDecoration(
                            new PinnedHeaderItemDecoration
                                    .Builder(BaseHeaderAdapter.TYPE_HEADER)
                                    .setDividerId(R.drawable.divider).enableDivider(true)
                                    .setHeaderClickListener(null).create());

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(billAdapter);

            refreshLayout.setEnableAutoLoadMore(false);

            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    billAdapter.replaceData(new ArrayList<PinnedHeaderEntity<Bill>>());
                    getUserBill();
                }
            });
        }
        if (!isGet) {
            refreshLayout.autoRefresh();
        }
    }

    private void getUserBill() {
        personalCenterActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.getUserBill);//"获取订单列表"
//        httpRequest.add("---", ---);
        CallServer.getInstance().postRequest("获取订单列表", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        if(refreshLayout==null){
                            return;
                        }
                        refreshLayout.finishRefresh();
                        personalCenterActivity.closeLoading();
                        getUserBill info = gson.fromJson(response, getUserBill.class);
//                        list.addAll(info.getData());
                        if (info.getCode() == 1) {
//                            isGet = true;

                            List<PinnedHeaderEntity<Bill>> temporaryList = new ArrayList<>();
                            for (int i = 0; i < info.getData().size(); i++) {

                                if (billAdapter.getData().size() == 0) {
                                    //第一次加载
                                    if (temporaryList.size() == 0) {
                                        //这组数据第一次解析
                                        temporaryList.add(new PinnedHeaderEntity<>(getBill(info.getData().get(i)), BaseHeaderAdapter.TYPE_HEADER, "Dog"));
                                        temporaryList.add(new PinnedHeaderEntity<>(getBill(info.getData().get(i)), BaseHeaderAdapter.TYPE_DATA, "Dog"));
                                    } else {
                                        //不是第一次解析
                                        int p = i - 1;
                                        if (info.getData().get(i).getTAG().equals(temporaryList.get(temporaryList.size() - 1).getData().getTAG())) {
                                            temporaryList.add(new PinnedHeaderEntity<>(getBill(info.getData().get(i)), BaseHeaderAdapter.TYPE_DATA, info.getData().get(i).getTAG()));
                                        } else {
                                            temporaryList.add(new PinnedHeaderEntity<>(getBill(info.getData().get(i)), BaseHeaderAdapter.TYPE_HEADER, "Dog"));
                                            temporaryList.add(new PinnedHeaderEntity<>(getBill(info.getData().get(i)), BaseHeaderAdapter.TYPE_DATA, "Dog"));
                                        }
                                    }
                                } else {
                                    //不是第一次解析  获取展示列表的最后一条数据
                                    Bill bill = billAdapter.getData().get(billAdapter.getData().size() - 1).getData();
                                    if (temporaryList.size() == 0) {
                                        if (info.getData().get(i).getTAG().equals(bill.getTAG())) {
                                            temporaryList.add(new PinnedHeaderEntity<>(getBill(info.getData().get(i)), BaseHeaderAdapter.TYPE_DATA, info.getData().get(i).getTAG()));
                                        } else {
                                            temporaryList.add(new PinnedHeaderEntity<>(getBill(info.getData().get(i)), BaseHeaderAdapter.TYPE_HEADER, "Dog"));
                                            temporaryList.add(new PinnedHeaderEntity<>(getBill(info.getData().get(i)), BaseHeaderAdapter.TYPE_DATA, "Dog"));
                                        }

                                    } else {
                                        //不是第一次解析
                                        int p = i - 1;
                                        if (info.getData().get(i).getTAG().equals(temporaryList.get(temporaryList.size() - 1).getData().getTAG())) {
                                            temporaryList.add(new PinnedHeaderEntity<>(getBill(info.getData().get(i)), BaseHeaderAdapter.TYPE_DATA, info.getData().get(i).getTAG()));
                                        } else {
                                            temporaryList.add(new PinnedHeaderEntity<>(getBill(info.getData().get(i)), BaseHeaderAdapter.TYPE_HEADER, "Dog"));
                                            temporaryList.add(new PinnedHeaderEntity<>(getBill(info.getData().get(i)), BaseHeaderAdapter.TYPE_DATA, "Dog"));
                                        }
                                    }
                                }

                            }
                            billAdapter.replaceData(temporaryList);
                        } else {
                            personalCenterActivity.showToast(info.getMsg());
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        personalCenterActivity.closeLoading();
                        personalCenterActivity.showToast(getString(R.string.toast_server_error));
                        refreshLayout.finishRefresh();
                    }
                }, getContext());
    }

    private Bill getBill(Bill bill) {

        Bill bill1 = new Bill();
        bill1.setCreatetime(bill.getCreatetime());
        bill1.setSubject(bill.getSubject());
        bill1.setType(bill.getType());
        return bill;

    }

}
