package com.hhkj.dyedu.ui.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.hhkj.dyedu.R;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.Locale;

/**
 * Created by zangyi_shuai_ge on 2018/8/21
 *
 * @author zangyi 767450430
 */
public class LanguageUtil {
    /**
     * @param isEnglish true  ：点击英文，把中文设置未选中
     *                  false ：点击中文，把英文设置未选中
     */
    public static void set(boolean isEnglish, Context context) {

        Configuration configuration = context.getResources().getConfiguration();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文
            configuration.locale = Locale.ENGLISH;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        context.getResources().updateConfiguration(configuration, displayMetrics);


        //更新其他资源
        setRefreshLanguage(context);
        setLoading(context);
    }

    private static void setLoading(Context context) {
//        LoadingDialog.LOADING = context.getString(R.string.srl_header_loading);
    }

    private static void setRefreshLanguage(Context context) {

        ClassicsHeader.REFRESH_HEADER_PULLING = context.getString(R.string.srl_header_pulling);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = context.getString(R.string.srl_header_refreshing);
        ClassicsHeader.REFRESH_HEADER_LOADING = context.getString(R.string.srl_header_loading);
        ClassicsHeader.REFRESH_HEADER_RELEASE = context.getString(R.string.srl_header_release);
        ClassicsHeader.REFRESH_HEADER_FINISH = context.getString(R.string.srl_header_finish);
        ClassicsHeader.REFRESH_HEADER_FAILED = context.getString(R.string.srl_header_failed);
        ClassicsHeader.REFRESH_HEADER_UPDATE = context.getString(R.string.srl_header_update);
        ClassicsHeader.REFRESH_HEADER_SECONDARY = context.getString(R.string.srl_header_secondary);


        ClassicsFooter.REFRESH_FOOTER_PULLING = context.getString(R.string.srl_footer_pulling);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = context.getString(R.string.srl_footer_release);
        ClassicsFooter.REFRESH_FOOTER_LOADING = context.getString(R.string.srl_footer_loading);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = context.getString(R.string.srl_footer_refreshing);
        ClassicsFooter.REFRESH_FOOTER_FINISH = context.getString(R.string.srl_footer_finish);
        ClassicsFooter.REFRESH_FOOTER_FAILED = context.getString(R.string.srl_footer_failed);
        ClassicsFooter.REFRESH_FOOTER_NOTHING = context.getString(R.string.srl_footer_nothing);
    }
}
