package com.hhkj.dyedu.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.bean.model.MultipleItem;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/8
 */

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public MultipleItemQuickAdapter(List data) {
        super(data);
//        addItemType(MultipleItem.TEXT, R.layout.text_view);
//        addItemType(MultipleItem.IMG, R.layout.image_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
//            case MultipleItem.TEXT:
//                helper.setImageUrl(R.id.tv, item.getContent());
//                break;
//            case MultipleItem.IMG:
//                helper.setImageUrl(R.id.iv, item.getContent());
//                break;
        }
    }

}


