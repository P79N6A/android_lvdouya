package com.hhkj.dyedu.bean.gson;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/13
 */

public class shopcarInfo extends  BaseHttpResponse {


    /**
     * time : 1528872274
     * data : [{"head":"童年回忆","title":"全集","image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png","id":4,"num":18,"type":1,"price":400}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() { return data;}

    public void setData(List<DataBean> data) { this.data = data;}

    public static class DataBean {
        /**
         * head : 童年回忆
         * title : 全集
         * image : /uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png
         * id : 4
         * num : 18
         * type : 1
         * price : 400
         */

        private String head;
        private String title;
        private String image;
        private int id;
        private int num;
        private int type;
        private int price;
        private int total;
        private int shopId;
        private boolean isChoose;

        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choose) {
            isChoose = choose;
        }


        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getHead() { return head;}

        public void setHead(String head) { this.head = head;}

        public String getTitle() { return title;}

        public void setTitle(String title) { this.title = title;}

        public String getImage() { return image;}

        public void setImage(String image) { this.image = image;}

        public int getId() { return id;}

        public void setId(int id) { this.id = id;}

        public int getNum() { return num;}

        public void setNum(int num) { this.num = num;}

        public int getType() { return type;}

        public void setType(int type) { this.type = type;}

        public int getPrice() { return price;}

        public void setPrice(int price) { this.price = price;}
    }
}
