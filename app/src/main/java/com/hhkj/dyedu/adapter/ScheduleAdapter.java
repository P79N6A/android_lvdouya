package com.hhkj.dyedu.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.MainApplication;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.ScheduleUnit;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 */

public class ScheduleAdapter extends MyBaseQuickAdapter<ScheduleUnit, BaseViewHolder> {

    private int itemHeight = 0;

    public ScheduleAdapter() {
        super(R.layout.rv_schedule_item);
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight + 2;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, ScheduleUnit item) {
        //主题课程


        //设置布局高度
        LinearLayout relativeLayout = helper.getView(R.id.layoutMain);
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.height = itemHeight;
        relativeLayout.setLayoutParams(layoutParams);

        helper.addOnClickListener(R.id.tv01);
        helper.addOnClickListener(R.id.tv02);
        helper.addOnClickListener(R.id.tv03);
        helper.addOnClickListener(R.id.tv04);
        helper.addOnClickListener(R.id.layoutAdd);

        helper.getView(R.id.layoutAdd).setVisibility(View.VISIBLE);
        helper.getView(R.id.tv01).setVisibility(View.VISIBLE);
        helper.getView(R.id.tv02).setVisibility(View.VISIBLE);
        helper.getView(R.id.tv03).setVisibility(View.VISIBLE);
        helper.getView(R.id.tv04).setVisibility(View.VISIBLE);

        if (item.getMon().size() == 4) {
            //填充满了
            helper.setText(R.id.tv01, setTimes(item.getMon().get(0).getStart_time(), item.getMon().get(0).getEnd_time()));
            helper.setText(R.id.tv02, setTimes(item.getMon().get(1).getStart_time(), item.getMon().get(1).getEnd_time()));
            helper.setText(R.id.tv03, setTimes(item.getMon().get(2).getStart_time(), item.getMon().get(2).getEnd_time()));
            helper.setText(R.id.tv04, setTimes(item.getMon().get(3).getStart_time(), item.getMon().get(3).getEnd_time()));

            helper.getView(R.id.layoutAdd).setVisibility(View.GONE);
//            helper.getView(R.id.view).setVisibility(View.GONE);

        } else if (item.getMon().size() == 3) {
            helper.setText(R.id.tv01, setTimes(item.getMon().get(0).getStart_time(), item.getMon().get(0).getEnd_time()));
            helper.setText(R.id.tv02, setTimes(item.getMon().get(1).getStart_time(), item.getMon().get(1).getEnd_time()));
            helper.setText(R.id.tv03, setTimes(item.getMon().get(2).getStart_time(), item.getMon().get(2).getEnd_time()));
            helper.getView(R.id.tv04).setVisibility(View.GONE);
        } else if (item.getMon().size() == 2) {
            helper.setText(R.id.tv01, setTimes(item.getMon().get(0).getStart_time(), item.getMon().get(0).getEnd_time()));
            helper.setText(R.id.tv02, setTimes(item.getMon().get(1).getStart_time(), item.getMon().get(1).getEnd_time()));
            helper.getView(R.id.tv03).setVisibility(View.GONE);
            helper.getView(R.id.tv04).setVisibility(View.GONE);
        } else if (item.getMon().size() == 1) {
            helper.setText(R.id.tv01, setTimes(item.getMon().get(0).getStart_time(), item.getMon().get(0).getEnd_time()));
            helper.getView(R.id.tv02).setVisibility(View.GONE);
            helper.getView(R.id.tv03).setVisibility(View.GONE);
            helper.getView(R.id.tv04).setVisibility(View.GONE);
        } else if (item.getMon().size() == 0) {
            helper.getView(R.id.tv01).setVisibility(View.GONE);
            helper.getView(R.id.tv02).setVisibility(View.GONE);
            helper.getView(R.id.tv03).setVisibility(View.GONE);
            helper.getView(R.id.tv04).setVisibility(View.GONE);
//            helper.getView(R.id.view).setVisibility(View.VISIBLE);
        }
        if(MainApplication.role==2){
            helper.getView(R.id.layoutAdd).setVisibility(View.GONE);
        }


//        ImageLoaderUtils.setImage(item.getImage(), (ImageView) helper.getView(R.id.ivHead));
//        helper.setText(R.id.tvPrice, "￥ " + item.getPrice());
//        helper.setText(R.id.tvTitle, item.getTitle());
//        helper.setText(R.id.duration, item.getDuration() + "分钟");
//        ImageView iv01 = helper.getView(R.id.iv01);
//        ImageView iv02 = helper.getView(R.id.iv02);
//        ImageView iv03 = helper.getView(R.id.iv03);
//        ImageView iv04 = helper.getView(R.id.iv04);
//        ImageView iv05 = helper.getView(R.id.iv05);
//        setStarNum(item.getStar(), iv01, iv02, iv03, iv04, iv05);
//
//
//        if(item.isChoose()){
//            helper.setVisible(R.id.layoutChoose,true);
//        }else {
//            helper.setVisible(R.id.layoutChoose,false);
//        }


    }

    private String setTimes(int start_time, int end_time) {

        int a1 = start_time / 60;
        int a2 = start_time % 60;

        int a3 = end_time / 60;
        int a4 = end_time % 60;

        String des = add0(a1) + ":" + add0(a2) + " ~ " + add0(a3) + ":" + add0(a4);

        return des;
    }

    private String add0(int a1) {
        if (a1 < 10) {
            return "0" + a1;
        } else {
            return a1 + "";
        }
    }
}
