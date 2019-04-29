package com.hhkj.dyedu.bean.model;

/**
 * Created by zangyi_shuai_ge on 2018/8/6
 *
 * @author zangyi 767450430
 */
public class ClassTip {


    /**
     * page : 1
     * text : 飞轮
     * image : /uploads/20180719/63b324372f50d537ec8e155cf626d475.png
     * video : /assets/img/wechat/2018-0319CVNZ123010504131155776.mp4
     */

    private String page;
    private String text;
    private String image;
    private String video;

    public String getPage() { return page;}

    public void setPage(String page) { this.page = page;}

    public String getText() {

//        if(text==null){
//            return "";
//        }
        return text;}

    public void setText(String text) { this.text = text;}

    public String getImage() { return image;}

    public void setImage(String image) { this.image = image;}

    public String getVideo() { return video;}

    public void setVideo(String video) { this.video = video;}
}
