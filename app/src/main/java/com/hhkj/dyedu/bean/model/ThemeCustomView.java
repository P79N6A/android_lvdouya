package com.hhkj.dyedu.bean.model;



public class ThemeCustomView {
    /**
     * [{"link":"1111","tag":"0","image":"/uploads/20190322/771b7b77462cf09aaafa57ca65478cd6.png"},
     * {"link":"","tag":"0","image":"/uploads/20190322/93095034842182000d38dfc5a4e79ae4.png"},
     * {"link":"","tag":"0","image":"/uploads/20190322/0d4053b2c000d6aa4fdcdc8c0030a0c8.png"},
     * {"link":"","tag":"0","image":"/uploads/20190322/771b7b77462cf09aaafa57ca65478cd6.png"}]
     */

    private String link;
    private int tag;
    private String image;


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
