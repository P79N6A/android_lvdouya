package com.hhkj.dyedu.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.ui.activity.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/9/6
 *
 * @author zangyi 767450430
 */
public class ForgetSuccessActivity extends BaseTitleActivity {

    @BindView(R.id.layoutTop)
    RelativeLayout layoutTop;
    @BindView(R.id.et01)
    TextView et01;
    @BindView(R.id.et02)
    TextView et02;
    private CountDownTimer timer;

    @Override
    public int setLayoutId() {
        return R.layout.activity_forget_success;
    }
    int  all=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new CountDownTimer(10*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int a = (int) (millisUntilFinished / 1000);
                et02.setText(a+"秒后自动跳转至登录页面，您也可以通过点击下方的完成" +
                        "按钮手动页面登录");
            }

            @Override
            public void onFinish() {
                finish();
            }
        };
        timer.start();
    }


    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    @OnClick(R.id.btLogin)
    public void onViewClicked() {
        finish();
    }
}
