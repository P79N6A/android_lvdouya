package com.hhkj.dyedu.ui.fragment;

import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.withdraw;
import com.hhkj.dyedu.event.MoneyVipUpdateEvent;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.ui.fragment.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 */

public class CashFragment extends BaseFragment {


    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.btPay)
    Button btPay;
    private PersonalCenterActivity personalCenterActivity;

    public void setPersonalCenterActivity(PersonalCenterActivity personalCenterActivity) {
        this.personalCenterActivity = personalCenterActivity;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_cash;
    }

    @Override
    public void initView() {


    }

    private void withdraw() {
        personalCenterActivity.showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.withdraw);//"提现"
        httpRequest.add("name", name.getText().toString().trim());
        httpRequest.add("account", account.getText().toString().trim());
        httpRequest.add("money", money.getText().toString().trim());
        CallServer.getInstance().postRequest("提现", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        personalCenterActivity.closeLoading();
                        withdraw info = gson.fromJson(response, withdraw.class);
                        if (info.getCode() == 1) {
                            MoneyVipUpdateEvent event=new MoneyVipUpdateEvent(info.getData().getMoney()+"",info.getData().getExpiration()+"");
                            EventBus.getDefault().post(event);
                            personalCenterActivity.cashFragmentEvent(1);

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



    @OnClick(R.id.btPay)
    public void onViewClicked() {

        String n=name.getText().toString().trim();
        String a=account.getText().toString().trim();
        String m=money.getText().toString().trim();

        if(n.equals("")){
            personalCenterActivity.showToast(getString(R.string.cash_toast_01));
            return;
        }
        if(n.equals("")){
            personalCenterActivity.showToast(getString(R.string.cash_toast_02));
            return;
        }

        if(m.equals("")){
            personalCenterActivity.showToast(getString(R.string.cash_toast_03));
            return;
        }
        withdraw();
    }
}
