package com.hhkj.dyedu.bean.model;

/**
 * Created by zangyi_shuai_ge on 2018/7/27
 *
 * @author zangyi 767450430
 * 竞赛
 */
public class Competition {


    private  int score=0;

    private String time1="0";
    private String time2="0";
    private String time3="0";
    private String time4="0";

    private int rank=-1;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    private boolean isStop=false;

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public void setTime(String time1, String time2, String time3, String time4){
        this.time1=time1;
        this.time2=time2;
        this.time3=time3;
        this.time4=time4;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public String getTime4() {
        return time4;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }

    public Competition(int score) {
        this.score = score;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
