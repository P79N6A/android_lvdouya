package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.ScheduleUnit2;

/**
 * Created by zangyi_shuai_ge on 2018/6/27
 */
public class show extends  BaseHttpResponse {


    /**
     * time : 1530064723
     * data : {"morning":{"Mon":[{"id":1,"week":"Mon","type":"morning","time":"8:00-9:00"},{"id":3,"week":"Mon","type":"morning","time":"10:00-11:00"},{"id":4,"week":"Mon","type":"morning","time":"11:00-12:00"},{"id":5,"week":"Mon","type":"morning","time":"9:00-10:00"}],"Tues":[{"id":6,"week":"Tues","type":"morning","time":"8:00-9:00"}],"Wed":[{"id":7,"week":"Wed","type":"morning","time":"8:00-9:00"}],"Thur":[{"id":8,"week":"Thur","type":"morning","time":"8:00-9:00"}],"Fri":[{"id":9,"week":"Fri","type":"morning","time":"8:00-9:00"}],"Sat":[{"id":10,"week":"Sat","type":"morning","time":"8:00-9:00"}],"Sun":[{"id":11,"week":"Sun","type":"morning","time":"8:00-9:00"}]},"afternoon":{"Mon":[{"id":12,"week":"Mon","type":"afternoon","time":"8:00-9:00"}],"Tues":[{"id":2,"week":"Tues","type":"afternoon","time":"10:00-11:00"}]},"teacherId":21,"nickname":"88888","avatar":""}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * morning : {"Mon":[{"id":1,"week":"Mon","type":"morning","time":"8:00-9:00"},{"id":3,"week":"Mon","type":"morning","time":"10:00-11:00"},{"id":4,"week":"Mon","type":"morning","time":"11:00-12:00"},{"id":5,"week":"Mon","type":"morning","time":"9:00-10:00"}],"Tues":[{"id":6,"week":"Tues","type":"morning","time":"8:00-9:00"}],"Wed":[{"id":7,"week":"Wed","type":"morning","time":"8:00-9:00"}],"Thur":[{"id":8,"week":"Thur","type":"morning","time":"8:00-9:00"}],"Fri":[{"id":9,"week":"Fri","type":"morning","time":"8:00-9:00"}],"Sat":[{"id":10,"week":"Sat","type":"morning","time":"8:00-9:00"}],"Sun":[{"id":11,"week":"Sun","type":"morning","time":"8:00-9:00"}]}
         * afternoon : {"Mon":[{"id":12,"week":"Mon","type":"afternoon","time":"8:00-9:00"}],"Tues":[{"id":2,"week":"Tues","type":"afternoon","time":"10:00-11:00"}]}
         * teacherId : 21
         * nickname : 88888
         * avatar :
         */

        private ScheduleUnit2 morning;
        private ScheduleUnit2 afternoon;
        private ScheduleUnit2 evening;

        public ScheduleUnit2 getEvening() {
            if(evening==null){
                return new ScheduleUnit2();
            }
            return evening;
        }

        public void setEvening(ScheduleUnit2 evening) {

            this.evening = evening;
        }

        private int teacherId;
        private String nickname;
        private String avatar;

        public ScheduleUnit2 getMorning() {
            if(morning==null){
                return new ScheduleUnit2();
            }

            return morning;}

        public void setMorning(ScheduleUnit2 morning) { this.morning = morning;}

        public ScheduleUnit2 getAfternoon() {
            if(afternoon==null){
                return new ScheduleUnit2();
            }

            return afternoon;}

        public void setAfternoon(ScheduleUnit2 afternoon) { this.afternoon = afternoon;}

        public int getTeacherId() { return teacherId;}

        public void setTeacherId(int teacherId) { this.teacherId = teacherId;}

        public String getNickname() { return nickname;}

        public void setNickname(String nickname) { this.nickname = nickname;}

        public String getAvatar() { return avatar;}

        public void setAvatar(String avatar) { this.avatar = avatar;}

    }
}
