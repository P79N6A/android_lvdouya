package com.hhkj.dyedu.callback;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zangyi_shuai_ge on 17.12.29
 * 支付类型
 */

public interface PayTypeListener {


    int TYPE_WE_CHAT = 1;//微信支付

    int TYPE_ALI = 2;//支付宝支付

    int TYPE_WALLET = 3;//钱包支付

    void payType(@PayType int type);

    @IntDef({TYPE_WE_CHAT, TYPE_ALI, TYPE_WALLET})
    @Retention(RetentionPolicy.SOURCE)
    @interface PayType {
    }
}
