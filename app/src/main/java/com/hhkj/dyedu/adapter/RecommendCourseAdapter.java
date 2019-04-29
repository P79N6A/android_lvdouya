package com.hhkj.dyedu.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundFrameLayout;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.CourseBean;
import com.hhkj.dyedu.utils.ImageLoaderUtils;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 * 推荐课程
 */

public class RecommendCourseAdapter extends MyBaseQuickAdapter<CourseBean, BaseViewHolder> {

    public static final int TYPE_VIP_COURSE = 1;

    private int type = 0;

    private int itemWidth;
    private int itemHeight;
    private int imageHeight;

    private boolean isShowAdd = false;
    private boolean isShowVipTag = false;

    private int showTag = 1;//1 不显示已上小节数  2 显示已上小节数


    public RecommendCourseAdapter() {
        super(R.layout.rv_recommend_item);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setShowTag(int showTag) {
        this.showTag = showTag;
    }

    public void setShowVipTag(boolean showVipTag) {
        isShowVipTag = showVipTag;
    }

    public void setShowAdd(boolean showAdd) {
        isShowAdd = showAdd;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseBean item) {
        //主题课程

        //设置边距
        int pos = helper.getLayoutPosition();
        RoundFrameLayout roundFrameLayout=helper.getView(R.id.layoutContent01);
        int c=pos%4;
        ViewGroup.MarginLayoutParams  layoutParams= (ViewGroup.MarginLayoutParams) roundFrameLayout.getLayoutParams();
       
       
        float ccc=mContext.getResources().getDimension(R.dimen.px_1);
       
        if(c==1|c==2){
            //中间的2个
            layoutParams.setMargins((int) (30*ccc),0,(int) (40*ccc),(int) (30*ccc));
        }else if(c==0){
            layoutParams.setMargins((int) (40*ccc),0,(int) (40*ccc),(int) (30*ccc));
        }else {
            layoutParams.setMargins((int) (40*ccc),0,(int) (30*ccc),(int) (30*ccc));
        }
        roundFrameLayout.requestLayout();
//        helper.addOnClickListener(R.id.layoutContent02).addOnClickListener(R.id.layoutContent03);
        setUiData(helper, item);

    }

    private void setUiData(BaseViewHolder helper, CourseBean item) {

        if (helper.getView(R.id.layoutContent03).getVisibility() == View.GONE) {
            ImageLoaderUtils.setImage(item.getImage(), (ImageView) helper.getView(R.id.ivHead));
            helper.setText(R.id.tvPrice, "￥ " + item.getPrice());
            helper.setText(R.id.tvTitle, item.getTitle());
            helper.setText(R.id.duration, item.getDuration() + "分钟");
            ImageView iv01 = helper.getView(R.id.iv01);
            ImageView iv02 = helper.getView(R.id.iv02);
            ImageView iv03 = helper.getView(R.id.iv03);
            ImageView iv04 = helper.getView(R.id.iv04);
            ImageView iv05 = helper.getView(R.id.iv05);
            setStarNum(item.getStar(), iv01, iv02, iv03, iv04, iv05);

        }
        if (item.getType() == CourseBean.VIP_COURSE) {
            //会员课程
            helper.setVisible(R.id.layoutVip, true);
            helper.getView(R.id.tvPrice).setVisibility(View.GONE);
//                    helper.getView(R.id.iv02).setVisibility(View.GONE);
        } else {
            helper.setText(R.id.tvPrice, "￥ " + item.getPrice());
            helper.setVisible(R.id.layoutVip, false);
            helper.getView(R.id.tvPrice).setVisibility(View.VISIBLE);
//                    helper.getView(R.id.iv02).setVisibility(View.VISIBLE);
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
