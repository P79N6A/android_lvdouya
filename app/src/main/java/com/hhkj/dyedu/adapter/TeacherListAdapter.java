package com.hhkj.dyedu.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.Teacher;
import com.hhkj.dyedu.utils.ImageLoaderUtils;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 */

public class TeacherListAdapter extends MyBaseQuickAdapter<Teacher, BaseViewHolder> {
    
    public TeacherListAdapter() {
        super(R.layout.rv_teacher_item);
        
    }
    
    @Override
    protected void convert(BaseViewHolder viewHolder, Teacher item) {
        viewHolder.getLayoutPosition();
        viewHolder.setText(R.id.username, mContext.getString(R.string.teacher_list_01) + item.getUsername());
        viewHolder.setText(R.id.nickname, item.getNickname());
        viewHolder.setText(R.id.pos, viewHolder.getLayoutPosition() + 1 + "");
        ImageLoaderUtils.setHeadImage(item.getAvatar(), (ImageView) viewHolder.getView(R.id.avatar));
        
        viewHolder.addOnClickListener(R.id.iv01)
                .addOnClickListener(R.id.iv02)
                .addOnClickListener(R.id.iv08)
                .addOnClickListener(R.id.iv03);
        
        
        if (item.getStatus().equals("normal")) {
            //已启用
            viewHolder.setImageResource(R.id.iv08, R.mipmap.kqs_01);
        } else {
            viewHolder.setImageResource(R.id.iv08, R.mipmap.kqs_02);
        }
        
    }
    
}
