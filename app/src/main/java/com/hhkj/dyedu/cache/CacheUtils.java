package com.hhkj.dyedu.cache;

import com.blankj.utilcode.util.SPUtils;
import com.hhkj.dyedu.bean.model.UserInfo;

/**
 * Created by zangyi_shuai_ge on 2017/9/27
 * SharedPreferences 辅助类
 */

public class CacheUtils {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_data";

    public static void setUserInfo(UserInfo info) {
        setToken(info.getToken());
        setHeadImage(info.getAvatar());
        setUid(info.getUser_id() + "");
        setNickName(info.getNickname());
        setUsername(info.getUsername());
        setCacheHeadImage(info.getMobile(), info.getAvatar());
        setGroup(info.getGroup_id());
    }

    public static void setCacheHeadImage(String phone, String headImage) {
        if (headImage == null) {
            headImage = "";
        }
        getSPUtils().put("headImage=" + phone, headImage);
    }

    public static void setCacheEmail(String email){
        if (email == null){
            email = "";
        }
        getSPUtils().put("email",email);
    }

    public static void setCheck(boolean isChecked){
       getSPUtils().put("isChecked",isChecked);
    }
    public static boolean getCheck(){
        return getSPUtils().getBoolean("isChecked", false);
    }


    /**
     * 获取sp工具类
     */
    public static SPUtils getSPUtils() {
        return SPUtils.getInstance(FILE_NAME);
    }

    public static String getCacheHeadImage(String phone) {
        return getSPUtils().getString("headImage=" + phone, "");
    }

    public static String getCacheEmail(){
        return getSPUtils().getString("email","");
    }

    public static int getGroup() {
        return getSPUtils().getInt("groupId", -1);
    }

    public static void setGroup(int groupId) {
        getSPUtils().put("groupId", groupId);
    }

    //===============================================
    public static String getToken() {
        return getSPUtils().getString("token", "");
    }

    public static void setToken(String token) {
        getSPUtils().put("token", token);
    }

    //===============================================
    public static String getUid() {
        return getSPUtils().getString("uid", "");
    }

    public static void setUid(String uid) {
        getSPUtils().put("uid", uid);
    }

    //===============================================
    public static String getUsername() {
        return getSPUtils().getString("username", "");
    }

    public static void setUsername(String username) {
        getSPUtils().put("username", username);
    }

    //
    public static String getMoney() {
        return getSPUtils().getString("money", "0");
    }

    public static void setMoney(String info) {
        getSPUtils().put("money", info);
    }

    public static String getVipTime() {
        return getSPUtils().getString("vipTime", "0");
    }

    public static void setVipTime(String info) {
        getSPUtils().put("vipTime", info);
    }

    //===============================================
    public static String getNickName() {
        return getSPUtils().getString("nickname", "");
    }

    public static void setNickName(String info) {
        getSPUtils().put("nickname", info);
    }


    //===============================================
    public static String getHeadImage() {
        return getSPUtils().getString("headImage", "");
    }

    public static void setHeadImage(String headImage) {
        if (headImage == null) {
            headImage = "";
        }
        getSPUtils().put("headImage", headImage);
    }

    //===============================================
    public static boolean isLogin() {
        return getSPUtils().getBoolean("isLogin", false);
    }

    //获取登录状态
    public static void setLogin(boolean isLogin) {
        getSPUtils().put("isLogin", isLogin);
    }


    public static boolean isFirst() {
        return getSPUtils().getBoolean("isFirst", true);
    }

    public static void setFirst(boolean info) {
        getSPUtils().put("isFirst", info);
    }


    //是否记住密码

    public static boolean isRemember() {
        return getSPUtils().getBoolean("isRemember", false);
    }

    public static void setRemember(boolean isLogin) {
        getSPUtils().put("isRemember", isLogin);
    }

    public static String getPassword() {
        return getSPUtils().getString("Password");
    }

    //获取登录状态
    public static void setPassword(String Password) {
        getSPUtils().put("Password", Password);
    }
    
    
    
    
    
    
    
    
    public static String getLanguage() {
        String info=getSPUtils().getString("language");
//        if(info.equals("")){
//            return Locale.getDefault().getLanguage();
//        }else {
//            return info;
//        }
        
        return  "zh";
    }
    
    public static void setLanguage(String  info) {
        getSPUtils().put("language", info);
    }
}
