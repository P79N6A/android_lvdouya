package com.hhkj.dyedu.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.event.MoneyVipUpdateEvent;
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

public class WalletFragment extends BaseFragment {

    @BindView(R.id.layout01)
    Button layout01;
    @BindView(R.id.layout02)
    Button layout02;
    @BindView(R.id.layout03)
    Button layout03;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.tvVipTime)
    TextView tvVipTime;
    @BindView(R.id.ivBuyVip)
    ImageView ivBuyVip;

    private PersonalCenterActivity personalCenterActivity;

    public void setPersonalCenterActivity(PersonalCenterActivity personalCenterActivity) {
        this.personalCenterActivity = personalCenterActivity;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        MoneyVipUpdateEvent event = new MoneyVipUpdateEvent();

        tvMoney.setText("余额：" + event.getMoney() + "元");

        if (event.getVipTime().equals("0")) {
            tvVipTime.setText("您还不是会员");
            ivBuyVip.setImageResource(R.mipmap.new_96);
        } else {
            tvVipTime.setText(event.getVipTimeS());
            ivBuyVip.setImageResource(R.mipmap.new_95);
        }

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @OnClick({R.id.layout01, R.id.layout02, R.id.layout03, R.id.ivBuyVip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout01:
                //充值
                personalCenterActivity.walletFragmentEvent(2);
                break;
            case R.id.layout02:
                //提现
                personalCenterActivity.walletFragmentEvent(3);
                break;
            case R.id.layout03:
                personalCenterActivity.walletFragmentEvent(4);
                break;
            case R.id.ivBuyVip:
                personalCenterActivity.personalInfoFragmentEvent(1);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MoneyVipUpdateEvent event) {
        tvMoney.setText("余额：" + event.getMoney() + "元");
        if (event.getVipTime().equals("0")) {
            tvVipTime.setText("您还不是会员");
            ivBuyVip.setImageResource(R.mipmap.dy2_11);
        } else {
            tvVipTime.setText(event.getVipTimeS());
            ivBuyVip.setImageResource(R.mipmap.dy2_10);
        }
    }
}
