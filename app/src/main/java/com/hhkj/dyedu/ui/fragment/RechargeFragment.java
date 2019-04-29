package com.hhkj.dyedu.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.ui.fragment.base.BaseFragment;
import com.zuoni.common.callback.SimpleTextWatcher;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 */

public class RechargeFragment extends BaseFragment {
    
    @BindView(R.id.iv01)
    ImageView iv01;
    @BindView(R.id.iv02)
    ImageView iv02;
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.btPay)
    Button btPay;
    
    private PersonalCenterActivity personalCenterActivity;
    private String payWay = "alipay";
    private boolean isInit = false;
    
    public void setPersonalCenterActivity(PersonalCenterActivity personalCenterActivity) {
        this.personalCenterActivity = personalCenterActivity;
    }
    
    @Override
    public int setLayoutId() {
        return R.layout.fragment_recharge;
    }
    
    @Override
    public void initView() {
        if (!isInit) {
            isInit = true;
            btPay.setText(R.string.recharge_03);
            et01.addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().trim().equals("")) {
                        btPay.setText(R.string.recharge_03);
                    } else {
                        btPay.setText("￥ " + s + getString(R.string.recharge_04));
                    }
                }
            });
        }
        
    }
    
    @OnClick({R.id.iv01, R.id.iv02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv01:
                iv01.setImageResource(R.mipmap.dy2_01);
                iv02.setImageResource(R.mipmap.dy_93);
                payWay = "alipay";
                break;
            case R.id.iv02:
                payWay = "wechat";
                iv01.setImageResource(R.mipmap.dy_92);
                iv02.setImageResource(R.mipmap.dy2_02);
                break;
        }
    }
    
    
    @OnClick(R.id.btPay)
    public void onViewClicked() {
        String money = et01.getText().toString().trim();
        if (money.equals("")) {
            personalCenterActivity.showToast(getString(R.string.recharge_03));
        } else {
            personalCenterActivity.rechargeFragmentEvent(payWay, money);
        }
    }
}
