package com.hhkj.dyedu.bean.gson;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/9/5
 *
 * @author zangyi 767450430
 */
public class BannerGson extends  BaseHttpResponse {


    /**
     * time : 1536115570
     * data : [{"image":"/uploads/20180611/1ecb5b3c166242589d667a37b0a8ae7d.png"},{"image":"/uploads/20180611/f3a2ba1eac50fbf17d73ec3e7c1254a2.png"},{"image":"/uploads/20180608/a2092e03e4f43ad09370f4cc108dfbe2.png"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() { return data;}

    public void setData(List<DataBean> data) { this.data = data;}

    public static class DataBean {
        /**
         * image : /uploads/20180611/1ecb5b3c166242589d667a37b0a8ae7d.png
         */

        private String image;

        public String getImage() { return image;}

        public void setImage(String image) { this.image = image;}
    }
}
