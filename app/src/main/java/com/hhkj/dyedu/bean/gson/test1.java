package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.Question;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/5/28
 */

public class test1 extends  BaseHttpResponse {


    /**
     * time : 1527500748
     * data : {"url":"http://39.104.60.111:8088//?furl=http://39.104.60.111/uploads/20180528/007b34dca3843b72ef54f55c5021f773.pptx","prefix":"/public/pptzip/ppt76/","filenum":15}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

//    private DataBean.TipBean tip;
//
//    public DataBean.TipBean getTip() {
//        return tip;
//    }
//
//    public void setTip(DataBean.TipBean tip) {
//        this.tip = tip;
//    }

    public static class DataBean {
        /**
         * url : http://39.104.60.111:8088//?furl=http://39.104.60.111/uploads/20180528/007b34dca3843b72ef54f55c5021f773.pptx
         * prefix : /public/pptzip/ppt76/
         * filenum : 15
         *
         * 提醒
         *[{"page":"1","dis":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg",
         * "sec":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg",
         *"cus":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg"}]
         */

        private String url;
        private String prefix;
        private int filenum;

        private String updatetime;
        private String title;
        private int star;
        private String tips;

        private String wars;
        
        private String plan;
        
        private String pdf_3d;

        private String attachment;

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getPdf_3d() {
            return pdf_3d;
        }
    
        public void setPdf_3d(String pdf_3d) {
            this.pdf_3d = pdf_3d;
        }
    
        public String getPlan() {
            return plan;
        }
    
        public void setPlan(String plan) {
            this.plan = plan;
        }
    
        public String getWars() {
            return wars;
        }

        public void setWars(String wars) {
            this.wars = wars;
        }

        private String drawing;

        public String getDrawing() {
            return drawing;
        }

        public void setDrawing(String drawing) {
            this.drawing = drawing;
        }

        private List<Question>  quiz;

        public List<Question> getQuiz() {
            return quiz;
        }

        public void setQuiz(List<Question> quiz) {
            this.quiz = quiz;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getUrl() { return url;}

        public void setUrl(String url) { this.url = url;}

        public String getPrefix() { return prefix;}

        public void setPrefix(String prefix) { this.prefix = prefix;}

        public int getFilenum() { return filenum;}

        public void setFilenum(int filenum) { this.filenum = filenum;}

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        /**
         * "[{\"page\":\"10\",\"text\":\"此处是备注\",\"image\":\"\",\"video\":\"http:\/\/kqsvideo.douyaedu.com\/55769b183a7d4c9497ff4f8ef3d788b4\/6db6f50cff3c471c99cff7bc78dfb2e5-1df16aa85b686add99771e2e10e6768c-ld.mp4\"},
         * {\"page\":\"11\",\"text\":\"\",\"image\":\"https:\/\/timgsa.baidu.com\/timg?image&quality=80&size=b9999_10000&sec=1556262228&di=6d47d74dca4f0b180ba6b4446bfebd7c&imgtype=jpg&er=1&src=http%3A%2F%2Fs11.sinaimg.cn%2Fmw690%2F006hikKrzy7slvzPwSKba%26amp%3B690\",\"video\":\"\"},
         * {\"page\":\"12\",\"text\":\"\",\"image\":\"\",\"video\":\"http:\/\/kqsvideo.douyaedu.com\/259102fa29ec449ab69fb95e4abec3af\/fa76350a918040f8ae4143535b71289e-2dfac2337cc797ce3ac464162c59385c-ld.mp4\"}]"
         */


        private int page;
        private String tiptop;


        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getTiptop() {
            return tiptop;
        }

        public void setTiptop(String tiptop) {
            this.tiptop = tiptop;
        }

    }
}
