package com.hhkj.dyedu.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.gson.studentFile;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 */

public class StudentAdapter extends MyBaseQuickAdapter<studentFile.DataBean, BaseViewHolder> {
    public StudentAdapter() {
        super(R.layout.rv_student_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, studentFile.DataBean item) {
        //主题课程
//        ImageLoaderUtils.setImage(item.getImage(), (ImageView) helper.getView(R.id.ivHead));
        helper.setText(R.id.username, item.getUsername());
        helper.setText(R.id.nickname, item.getNickname());
        helper.setText(R.id.age, item.getAge() + "");
        helper.setText(R.id.study, item.getStudy());
        if (item.getGender() == 1) {
            helper.setText(R.id.gender, "男");
        } else {
            helper.setText(R.id.gender, "女");
        }

        helper.addOnClickListener(R.id.layout01)
                .addOnClickListener(R.id.iv01)
                .addOnClickListener(R.id.iv02);

        if(item.isChoose()){
            helper.setImageResource(R.id.iv03,R.mipmap.zx_107);
        }else {
            helper.setImageResource(R.id.iv03,R.mipmap.zx_108);
        }


//        helper.setText(R.id.remark,item.getRemark());
//
//
//        if(item.isChoose()){
//            helper.getView(R.id.layoutMain).setBackgroundResource(R.drawable.bg_27);
//        }else {
//            helper.getView(R.id.layoutMain).setBackgroundResource(R.drawable.bg_26);
//        }
//        helper.setText(R.id.price,item.getPrice());
//
//        //会员免费
//        if(item.getType()==2){
//            helper.setVisible(R.id.layoutVip,true);
//        }else {
//            helper.setText(R.id.tvPrice,"￥ "+item.getPrice());
//            helper.setVisible(R.id.layoutVip,false);
//        }


    }

}
