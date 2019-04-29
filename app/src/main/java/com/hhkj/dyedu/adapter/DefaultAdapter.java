package com.hhkj.dyedu.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 */

public class DefaultAdapter extends MyBaseQuickAdapter<String, BaseViewHolder> {
    public DefaultAdapter() {
        super(R.layout.base_default_list_item);

        String a=AppUrl.login;

//            DefaultAdapter.this.setOnItemChildClickListener(new OnItemChildClickListener() {
//                @Override
//                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//
//
//                }
//            });

    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String item) {
        viewHolder.getLayoutPosition();

        viewHolder.setText(R.id.title, "标题" + item);

        viewHolder.setText(R.id.content, "内容" + item);

        viewHolder.addOnClickListener(R.id.content).addOnClickListener(R.id.title);
//                    .setText(R.id.lmi_actor, item.actors)
//                    .setText(R.id.lmi_grade, item.grade)
//                    .setText(R.id.lmi_describe, item.shortinfo);
//            Glide.with(mContext).load(item.picaddr).into((ImageView) viewHolder.getView(R.id.lmi_avatar));
    }

}
