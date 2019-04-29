package com.hhkj.dyedu.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.BaseHeaderAdapter;
import com.hhkj.dyedu.bean.model.Bill;
import com.hhkj.dyedu.bean.model.PinnedHeaderEntity;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/25
 */

public class BillAdapter extends BaseHeaderAdapter<PinnedHeaderEntity<Bill>> {

    public BillAdapter(List<PinnedHeaderEntity<Bill>> data) {

        super(data);
    }

    @Override
    protected void addItemTypes() {
        addItemType(BaseHeaderAdapter.TYPE_HEADER, R.layout.rv_bill_head_item);
        addItemType(BaseHeaderAdapter.TYPE_DATA, R.layout.rv_bill_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, PinnedHeaderEntity<Bill> item) {
        switch (helper.getItemViewType()) {

            case BaseHeaderAdapter.TYPE_HEADER:
                helper.setText(R.id.tvTag,item.getData().getM());
                break;
            case BaseHeaderAdapter.TYPE_DATA:
                int position = helper.getLayoutPosition();

//                helper.setImageResource(R.mipmap.)
                if (item.getData().getType() == 4) {
                    //提现
                    helper.setImageResource(R.id.type, R.mipmap.dy_99);

                } else if (item.getData().getType() == 2) {
                    //会员购买
                    helper.setImageResource(R.id.type, R.mipmap.dy_110);

                } else if (item.getData().getType() == 3) {
                    //充值
                    helper.setImageResource(R.id.type, R.mipmap.dy_98);
                }else {
                    helper.setImageResource(R.id.type, R.mipmap.dy_95);
                }

                helper.setText(R.id.subject,item.getData().getCreatetimeS()+"  "+item.getData().getSubject());


                break;
        }
    }
}
