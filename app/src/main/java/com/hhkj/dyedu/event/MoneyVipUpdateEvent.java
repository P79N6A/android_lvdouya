package com.hhkj.dyedu.event;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.TimeUtils;
import com.hhkj.dyedu.cache.CacheUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by zangyi_shuai_ge on 2018/6/22
 */

public class MoneyVipUpdateEvent {

    private String money;
    private String  vipTime;
//    yyyy年MM月dd日HH:mm:ss
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");
    public MoneyVipUpdateEvent(String money, String  vipTime) {
        this.money = money;
        this.vipTime = vipTime;

        CacheUtils.setVipTime(vipTime);
        CacheUtils.setMoney(money);
    }

    public MoneyVipUpdateEvent() {
        String m=CacheUtils.getMoney();
        String v=CacheUtils.getVipTime();
        this.money = m;
        this.vipTime=v;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getVipTime() {
        return vipTime;
    }
    public String getVipTimeS() {
        long a=Long.parseLong(vipTime);
        if(a==0){
            return "未购买会员";
        }
        String time= TimeUtils.millis2String(a*1000,DEFAULT_FORMAT);
        return "您的会员到期日为"+time;
    }

    public String getVipTimeS2() {
        long a=Long.parseLong(vipTime);
        if(a==0){
            return "未购买会员";
        }
        String time= TimeUtils.millis2String(a*1000,DEFAULT_FORMAT);
        return time;
    }
    public void setVipTime(String vipTime) {
        this.vipTime = vipTime;
    }
}
