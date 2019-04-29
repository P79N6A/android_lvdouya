package com.hhkj.dyedu.callback;

/**
 * Created by zangyi_shuai_ge on 17.12.29
 * pop 缩小
 */

public interface NarrowListener {


    /**
     * 提问环节
     */
    int NARROW_POP_TYPE_01 = 1;

    /**
     * 竞赛
     */
    int NARROW_POP_TYPE_02 = 2;
    /**
     * 黑板
     */
    int NARROW_POP_TYPE_03 = 3;

    /**
     * 画笔
     */
    int NARROW_POP_TYPE_04 = 4;



    int NARROW_POP_TYPE_05 = 5;


    void onNarrowListener(int narrowPop);
}
