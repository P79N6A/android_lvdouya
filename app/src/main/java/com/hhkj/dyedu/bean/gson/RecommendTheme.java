package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.Theme;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/9/17
 *
 * @author zangyi 767450430
 */
public class RecommendTheme extends  BaseHttpResponse{


    /**
     * time : 1537168699
     * data : [{"id":19,"title":"《百变创造力》","type":2,"image":"/uploads/20180914/d71f1ed992b7cf81cfce759840e6f27c.jpg","price":"0.00","count":12},{"id":20,"title":"《大机械世界》","type":2,"image":"/uploads/20180914/cc97374eeeeb6292f361db50b735af1e.jpg","price":"0.00","count":12},{"id":18,"title":"《动物世界》","type":2,"image":"/uploads/20180913/dd502f9a939d81bfa98bab9dc5ef1959.jpg","price":"0.00","count":12}]
     */

    private List<Theme> data;


    public List<Theme> getData() { return data;}

    public void setData(List<Theme> data) { this.data = data;}


}
