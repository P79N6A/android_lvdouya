package com.hhkj.dyedu;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hhkj.dyedu.utils.LoggerUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.yanzhenjie.nohttp.NoHttp;
import com.hhkj.dyedu.utils.LogUtil;

/**
 * Created by zangyi_shuai_ge on 2017/9/27
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initHttp();
        initLogger();
        //获取单位尺寸
        LogUtil.i("单位尺寸", String.valueOf(getResources().getDimension(R.dimen.px_1)));
        LogUtil.i("适配文件", String.valueOf(getResources().getDimension(R.dimen.px_test)));
    }

    public static int role = 0;

    /**
     * 网络访问框架初始化
     */
    private void initHttp() {
        NoHttp.initialize(this);
    }

    /**
     * 日志初始化
     */
    private void initLogger() {


        LogStrategy logStrategy = new LogStrategy() {

            private String[] prefix = {". ", " ."};
            private int index = 0;

            @Override
            public void log(int priority, @Nullable String tag, @NonNull String message) {
                index = index ^ 1;
                Log.println(priority, prefix[index] + tag, message);
            }
        };

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .logStrategy(logStrategy)
                .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(0)         // （可选）要显示的方法行数。 默认2
                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
//            .logStrategy(customLog) //（可选）更改要打印的日志策略。 默认LogCat
                .tag(LoggerUtils.TAG)   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
