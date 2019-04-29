package com.hhkj.dyedu.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.BuildConfig;
import com.hhkj.dyedu.GlobalVariable;
import com.hhkj.dyedu.R;

import java.io.File;

/**
 * Created by zangyi_shuai_ge on 2017/11/22
 */

public class ImageLoaderUtils {
    public static void setImage(String url, ImageView imageView) {
        if(url==null){
            url="";
        }
        url= BuildConfig.API_HOST+url;

        Glide
                .with(imageView.getContext())
                .load(url)
                .asBitmap()
//                .error()
//                .placeholder(R.mipmap.cover)
                .into(imageView);
    }

    public static void setImage(Context context, File url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
//                .error()
//                .placeholder(R.mipmap.cover)
                .into(imageView);
    }

    public static void setImage2( String url, ImageView imageView) {
        Glide
                .with(imageView.getContext())
                .load(url)
//                .error()
//                .placeholder(R.mipmap.cover)
                .into(imageView);
    }
    /**
     * 加载头像
     */
    public static void setHeadImage( String url, ImageView imageView) {

        url=BuildConfig.API_HOST+url;

        Glide
                .with(imageView.getContext())
                .load(url)
                .asBitmap()
//                .error(R.mipmap.dy_38)
//                .placeholder(R.mipmap.dy_38)
                .animate(new ViewPropertyAnimation.Animator() {
                    @Override
                    public void animate(View view) {
                        view.setAlpha(0.3f);
                        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0.3f, 1f);
                        fadeAnim.setDuration(800);
                        fadeAnim.start();
                    }
                })
                .into(imageView);
    }

    /**
     * 加载课程图片
     */
    public static void setCourseImage( String url, ImageView imageView) {

        url=BuildConfig.API_HOST+url;
        Glide
                .with(imageView.getContext())
                .load(url)
                .asBitmap()
//                .error(R.mipmap.ic_launcher)
//                .placeholder(R.mipmap.ic_launcher)
                .animate(new ViewPropertyAnimation.Animator() {
                    @Override
                    public void animate(View view) {
                        view.setAlpha(0.3f);
                        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0.3f, 1f);
                        fadeAnim.setDuration(800);
                        fadeAnim.start();
                    }
                })
                .into(imageView);
    }
    public static void setCourseImage( int id, ImageView imageView) {

        Glide
                .with(imageView.getContext())
                .load(id)
                .asBitmap()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .animate(new ViewPropertyAnimation.Animator() {
                    @Override
                    public void animate(View view) {
                        view.setAlpha(0.3f);
                        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0.3f, 1f);
                        fadeAnim.setDuration(800);
                        fadeAnim.start();
                    }
                })
                .into(imageView);
    }

    public static void setImage( ImageView imageView, int pos) {

        String url = "";
        switch (pos) {
            case 0:
                url = GlobalVariable.TEST_IMAGE_URL1;
                break;
            case 1:
                url = GlobalVariable.TEST_IMAGE_URL2;
                break;
            case 2:
                url = GlobalVariable.TEST_IMAGE_URL3;
                break;
            case 3:
                url = GlobalVariable.TEST_IMAGE_URL4;
                break;
            case 4:
                url = GlobalVariable.TEST_IMAGE_URL5;
                break;
            case 6:
                url = GlobalVariable.TEST_IMAGE_URL6;
                break;
            case 7:
                url = GlobalVariable.TEST_IMAGE_URL7;
                break;
            case 8:
                url = GlobalVariable.TEST_IMAGE_URL8;
                break;
            default:
                url = GlobalVariable.TEST_IMAGE_URL9;
                break;
        }


        Glide
                .with(imageView.getContext())
                .load(url)
                .asBitmap()
//                .error()
//                .placeholder(R.mipmap.cover)
                .into(imageView);
    }

}
