package com.hhkj.dyedu.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.Theme;
import com.hhkj.dyedu.utils.ImageLoaderUtils;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 * 会员课程 和选择课程
 */

public class CourseAdapterType10 extends MyBaseQuickAdapter<Theme, BaseViewHolder> {

    public static final int TYPE_VIP_COURSE = 1;

    private int type = 0;

    private int itemWidth;
    private int itemHeight;
    private int imageHeight;

    private boolean isShowAdd = false;
    private boolean isShowVipTag = false;

    private int showTag = 1;//1 不显示已上小节数  2 显示已上小节数


    public CourseAdapterType10() {
        super(R.layout.rv_course_item_type_10);
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
    protected void convert(BaseViewHolder helper, Theme item) {
        //主题课程

        //设置边距
        int pos = helper.getLayoutPosition();
        FrameLayout roundFrameLayout=helper.getView(R.id.layoutContent01);
        int c=pos%4;
        ViewGroup.MarginLayoutParams  layoutParams= (ViewGroup.MarginLayoutParams) roundFrameLayout.getLayoutParams();


        float ccc=mContext.getResources().getDimension(R.dimen.px_1);

        if(c==1|c==2){
            //中间的2个
            layoutParams.setMargins((int) (35*ccc),0,(int) (35*ccc),(int) (30*ccc));
        }else if(c==0){
            layoutParams.setMargins((int) (18*ccc),0,(int) (35*ccc),(int) (30*ccc));
        }else {
            layoutParams.setMargins((int) (35*ccc),0,(int) (18*ccc),(int) (30*ccc));
        }
        roundFrameLayout.requestLayout();
        helper.addOnClickListener(R.id.layoutContent02).addOnClickListener(R.id.layoutContent03);
        setUiData(helper, item);


        if(item.getLock()==0){
            helper.setVisible(R.id.ivLock,false);
        }else {
            helper.setVisible(R.id.ivLock,true);
        }


    }

    private void setUiData(BaseViewHolder helper, Theme item) {

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

            if (showTag == 1) {
                //不显示
                helper.setText(R.id.knots, new SpanUtils()
                        .append("总共")
                        .append(item.getKnots() + "").setForegroundColor(Color.parseColor("#A5CB1F"))
                        .append("节").create());
            } else if (showTag == 2) {
                helper.setText(R.id.knots, new SpanUtils()
                        .append("已上")
                        .append(item.getFinish() + "").setForegroundColor(Color.parseColor("#FF5355"))
                        .append("节/总共")
                        .append(item.getKnots() + "").setForegroundColor(Color.parseColor("#A5CB1F"))
                        .append("节").create());
            }


        }
        if (item.getType() == 2) {
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
