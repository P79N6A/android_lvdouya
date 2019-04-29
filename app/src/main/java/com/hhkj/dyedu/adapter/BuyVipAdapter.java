package com.hhkj.dyedu.adapter;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.BuyVip;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 */

public class BuyVipAdapter extends MyBaseQuickAdapter<BuyVip, BaseViewHolder> {
    private int itemWidth;
    private int itemHeight;
    private int imageHeight;
    public BuyVipAdapter() {
        super(R.layout.rv_buy_vip_item);
        int screenWidth = ScreenUtils.getScreenWidth();
//        LogUtil.i("尺寸" + screenHeight + "   " + screenWidth);
        //计算尺寸
        itemWidth = (int) (screenWidth / 960.00 * 150);
        imageHeight = (int) (itemWidth / 190.00 * 125.00);
        itemHeight = (int) (itemWidth / 150.00 * 175.00);
    }

    @Override
    protected void convert(BaseViewHolder helper, BuyVip item) {
        //主题课程
//        ImageLoaderUtils.setImage(item.getImage(), (ImageView) helper.getView(R.id.ivHead));
        helper.setText(R.id.price,"￥ "+item.getPrice());
        helper.setText(R.id.remark,item.getRemark());


        if(item.isChoose()){
            helper.getView(R.id.layoutMain).setBackgroundResource(R.drawable.bg_27);
        }else {
            helper.getView(R.id.layoutMain).setBackgroundResource(R.drawable.bg_26);
        }

//        setSize( helper.getView(R.id.layoutMain),itemHeight,itemWidth);
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
