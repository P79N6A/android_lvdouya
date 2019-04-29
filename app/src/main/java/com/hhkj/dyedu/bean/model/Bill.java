package com.hhkj.dyedu.bean.model;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zangyi_shuai_ge on 2018/6/25
 */

public class Bill {
    /**
     * createtime : 1529893769
     * subject : 申请提现333元
     * type : 4
     */
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int createtime;
    private String subject;
    private int type;

    public int getCreatetime() { return createtime;}

    public void setCreatetime(int createtime) { this.createtime = createtime;}

    public String getSubject() { return subject;}

    public void setSubject(String subject) { this.subject = subject;}

    public int getType() { return type;}

    public void setType(int type) { this.type = type;}


    public String getCreatetimeS() {
        long a=createtime;
        String time= TimeUtils.millis2String(a*1000,DEFAULT_FORMAT);
//            String time= TimeUtils.millis2String(a,DEFAULT_FORMAT);
        return time;
    }

    public String getTAG() {
        long a=createtime;
        Date date=  com.blankj.utilcode.util.TimeUtils.millis2Date(a*1000);
        date.getYear();
        date.getMonth();
        return (date.getYear()+1900)+" "+(date.getMonth()+1);
    }

    public String getM() {
        long a=createtime;
        Date date=  com.blankj.utilcode.util.TimeUtils.millis2Date(a*1000);
        date.getYear();
        date.getMonth();
        return  date.getMonth()+1+"月";
    }
}
