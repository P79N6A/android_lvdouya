package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.Bill;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/25
 */

public class getUserBill extends BaseHttpResponse {


    /**
     * time : 1529895975
     * data : [{"createtime":1529893769,"subject":"申请提现333元","type":4},{"createtime":1529893768,"subject":"申请提现333元","type":4},{"createtime":1529893768,"subject":"申请提现333元","type":4},{"createtime":1529893767,"subject":"申请提现333元","type":4},{"createtime":1529893654,"subject":"申请提现333元","type":4},{"createtime":1529893570,"subject":"申请提现2888元","type":4},{"createtime":1529892953,"type":3,"subject":"充值22元"},{"createtime":1529892900,"type":3,"subject":"充值20000元"},{"createtime":1529892823,"type":3,"subject":"充值2元"},{"createtime":1529891678,"type":2,"subject":"购买会员30天"},{"createtime":1529890799,"type":2,"subject":"购买会员30天"},{"createtime":1529889599,"type":2,"subject":"购买会员30天"},{"createtime":1529889468,"type":2,"subject":"购买会员30天"},{"createtime":1529889419,"type":2,"subject":"购买会员30天"},{"createtime":1529889109,"type":2,"subject":"购买会员30天"},{"createtime":1529888861,"type":2,"subject":"购买会员365天"},{"createtime":1529660234,"type":2,"subject":"购买会员30天"},{"createtime":1529660080,"type":2,"subject":"购买会员30天"},{"createtime":1529660050,"type":2,"subject":"购买会员30天"},{"createtime":1529660004,"type":2,"subject":"购买会员365天"},{"createtime":1529659973,"type":2,"subject":"购买会员30天"},{"createtime":1529659773,"type":2,"subject":"购买会员30天"},{"createtime":1529659624,"type":2,"subject":"购买会员90天"},{"createtime":1529033162,"type":1,"subject":"重中之重"},{"createtime":1529032478,"type":1,"subject":"哦哦哦"},{"createtime":1529031448,"subject":"申请提现50元","type":4},{"createtime":1529031206,"type":1,"subject":"乐乐乐"},{"createtime":1529031130,"type":1,"subject":"哈哈哈"},{"createtime":1529031076,"type":1,"subject":"啦啦啦"}]
     */

    private List<Bill> data;

    public List<Bill> getData() { return data;}

    public void setData(List<Bill> data) { this.data = data;}

}
