package com.hhkj.dyedu.callback;

/**
 * Created by zangyi_shuai_ge on 17.12.29
 */

public interface OnDirectionListener {

    int LEFT=1;
    int RIGHT=2;

    void direction(int direction);
}
