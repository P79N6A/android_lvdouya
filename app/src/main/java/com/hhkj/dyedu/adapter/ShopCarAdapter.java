package com.hhkj.dyedu.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.shopcarInfo;
import com.hhkj.dyedu.utils.ImageLoaderUtils;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 */

public class ShopCarAdapter extends BaseQuickAdapter<shopcarInfo.DataBean, BaseViewHolder> {
    public ShopCarAdapter() {
        super(R.layout.rv_shop_car_item);
//        this.openLoadAnimation(null);
    }

    @Override
    protected void convert(BaseViewHolder helper, shopcarInfo.DataBean item) {
        //主题课程
        ImageLoaderUtils.setImage(item.getImage(), (ImageView) helper.getView(R.id.ivHead));
        helper.setText(R.id.head,item.getHead());
        helper.setText(R.id.title,item.getTitle());

        helper.setText(R.id.price,"￥ "+item.getPrice());
        helper.setText(R.id.num,item.getNum()+"");


        if(item.getType()==1){
            //全集
            helper.setVisible(R.id.layoutTotal,true);
            helper.setText(R.id.total,"共"+item.getTotal()+"节");

        }else {
            helper.setVisible(R.id.layoutTotal,false);
        }

        if(item.isChoose()){
            helper.setImageResource(R.id.ivChoose,R.mipmap.zx_107);
        }else {
            helper.setImageResource(R.id.ivChoose,R.mipmap.zx_108);
        }



        //总金额计算
        double all=item.getPrice()*item.getNum();

        helper.setText(R.id.priceAll,"￥ "+all);

        helper.addOnClickListener(R.id.tvAdd);

        helper.addOnClickListener(R.id.tvReduce);

        helper.addOnClickListener(R.id.num);

        helper.addOnClickListener(R.id.ivChoose);

        helper.addOnClickListener(R.id.del);
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
