package com.hhkj.dyedu.adapter;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.SmallPpt;
import com.hhkj.dyedu.utils.ImageLoaderUtils;
import com.joooonho.SelectableRoundedImageView;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 * 开始上课 ppt缩略图列表
 */

public class SmallPptAdapter extends MyBaseQuickAdapter<SmallPpt, BaseViewHolder> {

    private int nowChoosePosition = 0;

    private String url;

    private String updatetime;

    public SmallPptAdapter() {
        super(R.layout.rv_start_class_ppt_small_item);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, SmallPpt item) {
        int pos = viewHolder.getLayoutPosition();

//        viewHolder.setText(R.id.title, "标题" + item);

//        viewHolder.setText(R.id.content, "内容" + item);
//
//        viewHolder.addOnClickListener(R.id.content).addOnClickListener(R.id.title);
//                    .setText(R.id.lmi_actor, item.actors)
//                    .setText(R.id.lmi_grade, item.grade)
//                    .setText(R.id.lmi_describe, item.shortinfo);
//            Glide.with(mContext).load(item).into((ImageView) viewHolder.getView(R.id.lmi_avatar));
        String murl = url + "p" + (pos + 1) + ".png?time="+ updatetime;
        Log.i("111",murl);
//        ImageLoaderUtils.setHeadImage(murl,(SelectableRoundedImageView) viewHolder.getView(R.id.ivPpt));
        SelectableRoundedImageView selectableRoundedImageView = viewHolder.getView(R.id.ivPpt);
        ImageLoaderUtils.setImage( murl, selectableRoundedImageView);


        TextView textView = viewHolder.getView(R.id.tvPos);
        if (item.isChoose()) {
            selectableRoundedImageView.setBorderColor(Color.parseColor("#87C444"));
            textView.setBackgroundResource(R.drawable.bg_06);
        } else {
            selectableRoundedImageView.setBorderColor(Color.parseColor("#FDFEFF"));
            textView.setBackgroundResource(R.drawable.bg_09);
        }
        viewHolder.setText(R.id.tvPos, (pos + 1) + "")
                .addOnClickListener(R.id.ivPpt);

    }

    public int getNowChoosePosition() {
        return nowChoosePosition;
    }

    public void setChoosePosition(int position) {


        List<SmallPpt> smallPpts = this.getData();

        if (position >= smallPpts.size() | position < 0) {
            return;
        }

        for (int i = 0; i < smallPpts.size(); i++) {
            smallPpts.get(i).setChoose(false);
        }
        nowChoosePosition = position;


        smallPpts.get(position).setChoose(true);

//        this.replaceData(smallPpts);

        this.notifyDataSetChanged();
    }
}
