package com.hhkj.dyedu.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hhkj.dyedu.BuildConfig;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by zangyi_shuai_ge on 2017/11/20
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String url = BuildConfig.API_HOST + (String) path;
        LogUtil.i("轮播图片"+url);

        if(context==null){
            return;
        }
        try{
            Glide.with(context)
                    .load(url)
//                .error(R.mipmap.yyjy_67)
//                .placeholder(R.mipmap.yyjy_67)
                    .into(imageView);
        }catch (Exception e){
            LogUtil.i("图片加载报错"+e.toString());
        }


    }
}
