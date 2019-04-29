package com.hhkj.dyedu.bean.gson;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/9/19
 *
 * @author zangyi 767450430
 */
public class center extends  BaseHttpResponse{


    /**
     * code : 1
     * msg : 请求成功
     * time : 1537930209
     * data : {"banner":[{"image":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg","link":"http://www.douyaedu.com/"},{"image":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg","link":"http://www.douyaedu.com/"},{"image":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg","link":"http://www.douyaedu.com/"},{"image":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg","link":"http://www.douyaedu.com/"}],"other":[{"image":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg","link":"http://www.douyaedu.com/"},{"image":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg","link":"http://www.douyaedu.com/"},{"image":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg","link":"http://www.douyaedu.com/"},{"image":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg","link":"http://www.douyaedu.com/"},{"image":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg","link":"http://www.douyaedu.com/"}]}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        private List<BannerBean> banner;
        private List<OtherBean> other;

        public List<BannerBean> getBanner() { return banner;}

        public void setBanner(List<BannerBean> banner) { this.banner = banner;}

        public List<OtherBean> getOther() {
            
            
            
            
            
            return other;}

        public void setOther(List<OtherBean> other) { this.other = other;}

        public static class BannerBean {
            /**
             * image : /uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg
             * link : http://www.douyaedu.com/
             */

            private String image;
            private String link;

            public String getImage() { return image;}

            public void setImage(String image) { this.image = image;}

            public String getLink() { return link;}

            public void setLink(String link) { this.link = link;}
        }

        public static class OtherBean {
            /**
             * image : /uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg
             * link : http://www.douyaedu.com/
             */

            private String image;
            private String link;

            public String getImage() { return image;}

            public void setImage(String image) { this.image = image;}

            public String getLink() { return link;}

            public void setLink(String link) { this.link = link;}
        }
    }
}
