package com.hhkj.dyedu.bean.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by zangyi_shuai_ge on 2018/6/8
 */

public class MultipleItem implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;
    private int itemType;

    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
