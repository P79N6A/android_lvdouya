package com.hhkj.dyedu.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.CourseBean;
import com.hhkj.dyedu.utils.ImageLoaderUtils;
import com.hhkj.dyedu.utils.LogUtil;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 * 主题详情 里面的课程列表
 */

public class CourseAdapter04 extends MyBaseQuickAdapter<CourseBean, BaseViewHolder> {
    private int showTag;
    private int vipType;

    public void setUiType(int showTag,int vipType){
        this.showTag=showTag;
        this.vipType=vipType;
        LogUtil.i("设置ui"+showTag+"  "+vipType);
    }

    private int is_price;//返回价格：0为不显示，1为显示

    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public CourseAdapter04() {
        super(R.layout.rv_course_item_04);
    }

    private boolean showBuy=false;

    public void setShowBuy(boolean showBuy) {
        this.showBuy = showBuy;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseBean item) {
        //主题课程
        ImageLoaderUtils.setImage(item.getImage(), (ImageView) helper.getView(R.id.ivHead));
        //helper.setText(R.id.tvPrice, "￥ " + item.getPrice());
        if (is_price == 1){
            helper.setText(R.id.tvPrice,"￥" + item.getPrice());
        }else if (is_price == 0){
            helper.setText(R.id.tvPrice,null);
        }
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.duration, item.getDuration() + "分钟");
        ImageView iv01 = helper.getView(R.id.iv01);
        ImageView iv02 = helper.getView(R.id.iv02);
        ImageView iv03 = helper.getView(R.id.iv03);
        ImageView iv04 = helper.getView(R.id.iv04);
        ImageView iv05 = helper.getView(R.id.iv05);
        setStarNum(item.getStar(), iv01, iv02, iv03, iv04, iv05);

        if(this.type==2){
            helper.setVisible(R.id.tvBuy,false);
            helper.setVisible(R.id.tvbuuu,false);
        }else {
            helper.setVisible(R.id.tvBuy,true);
            helper.setVisible(R.id.tvbuuu,true);
            helper.setVisible(R.id.tvbuuu,showBuy);
        }

        if(vipType==2){
            //会员不显示购买次数
            helper.setGone(R.id.tvBuy,false);
        }else {
            //普通课程显示购买次数
            helper.setGone(R.id.tvBuy,true);
            helper.setText(R.id.tvBuy,"已购买 "+item.getBuy()+" 次");
        }

        helper.addOnClickListener(R.id.ivHead);
        helper.addOnClickListener(R.id.layoutBo);

        //是否选中
        if(item.isChoose()){
            helper.setVisible(R.id.layoutChoose,true);
        }else {
            helper.setVisible(R.id.layoutChoose,false);
        }

        helper.setGone(R.id.layoutPlay,false);
        helper.setVisible(R.id.tvbuuu,false);

        if(showTag==1){
            if(vipType==2){
                //是vip
                helper.setGone(R.id.layoutPlay,false);
                helper.setVisible(R.id.tvbuuu,false);
            }else {
                //普通
                helper.setGone(R.id.layoutPlay,true);
                helper.setVisible(R.id.tvbuuu,true);
            }
        }


    }

    private void setStarNum(int star, ImageView iv01, ImageView iv02, ImageView iv03, ImageView iv04, ImageView iv05) {

        switch (star) {
            default:
            case 1:
                iv01.setVisibility(View.VISIBLE);
                iv02.setVisibility(View.GONE);
                iv03.setVisibility(View.GONE);
                iv04.setVisibility(View.GONE);
                iv05.setVisibility(View.GONE);
                break;

            case 2:
                iv01.setVisibility(View.VISIBLE);
                iv02.setVisibility(View.VISIBLE);
                iv03.setVisibility(View.GONE);
                iv04.setVisibility(View.GONE);
                iv05.setVisibility(View.GONE);
                break;
            case 3:
                iv01.setVisibility(View.VISIBLE);
                iv02.setVisibility(View.VISIBLE);
                iv03.setVisibility(View.VISIBLE);
                iv04.setVisibility(View.GONE);
                iv05.setVisibility(View.GONE);
                break;
            case 4:
                iv01.setVisibility(View.VISIBLE);
                iv02.setVisibility(View.VISIBLE);
                iv03.setVisibility(View.VISIBLE);
                iv04.setVisibility(View.VISIBLE);
                iv05.setVisibility(View.GONE);
                break;
            case 5:
                iv01.setVisibility(View.VISIBLE);
                iv02.setVisibility(View.VISIBLE);
                iv03.setVisibility(View.VISIBLE);
                iv04.setVisibility(View.VISIBLE);
                iv05.setVisibility(View.VISIBLE);
                break;
        }
    }
}
