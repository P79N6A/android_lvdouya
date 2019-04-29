package com.hhkj.dyedu.bean.model;

/**
 * Created by zangyi_shuai_ge on 2018/5/28
 */

public class SmallPpt {

    private String url;

    private boolean isChoose;

    private boolean isLook;

    public boolean isLook() {
        return isLook;
    }

    public void setLook(boolean look) {
        isLook = look;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
