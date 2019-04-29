package com.hhkj.dyedu.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.callback.SetTimeListener;
import com.hhkj.dyedu.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by zangyi_shuai_ge on 2018/5/30
 * 从底部弹出的Dialog
 */
public class ConfigureDialog extends Dialog {

    private Params params;

    public ConfigureDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void setParams(Params params) {
        this.params = params;
    }


    public static class Builder {
        private final Context context;
        private final Params params;
        private ConfigureDialog dialog;
        private TextView tv01;
        private ImageView ivHead,ivClose;

        private Banner banner;

        private String url;
        private String text;

        public void setUrl(String url) {
            this.url = url;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Builder(Context context) {
            this.context = context;
            params = new Params();
        }

        public void setTime(String time){
            params.time=time;
        }

        public void setOnClickListener(SetTimeListener onClickListener) {
            params.setTimeListener = onClickListener;
        }

        public ConfigureDialog create() {
            dialog = new ConfigureDialog(context, params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);

            View view = LayoutInflater.from(context).inflate(R.layout.dialog_configure, null);

//
            tv01 = view.findViewById(R.id.tv01);
            ivHead = view.findViewById(R.id.ivHead);
            ivClose= view.findViewById(R.id.ivClose);
            banner= view.findViewById(R.id.banner);
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            List<String> list=new ArrayList<>();

            if(url.equals("")){
                list.add("");
            }else {
                String[] split=  url.split(",");
                Collections.addAll(list, split);
            }
            //设置图片集合
            banner.setImages(list);
            banner.setImageLoader(new GlideImageLoader());
//            banner.setBannerAnimation(ZoomOutSlideTransformer.class);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
            banner.isAutoPlay(false);
            banner.stopAutoPlay();
            tv01.setText(text);
//
//
//            if(!params.time.equals("")){
//                et01.setText(params.time);
//            }
//
//            if(params.setTimeListener!=null){
//                tv01.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(et01.getText().toString().trim().equals("")){
//                            ToastUtils.showToast(context,"请输入时间");
//                        }else {
//                            params.setTimeListener.setTime(et01.getText().toString());
//                        }
//                    }
//                });
//            }

//            this.getWindow().setFlags(
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


            Window win = dialog.getWindow();
            assert win != null;
            win.getDecorView().setPadding(0, 0, 0, 0);
    
            win.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            int width =(int) context.getResources().getDimension(R.dimen.px_640);
//            int height =  SizeUtils.dp2px(350);


            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height =  WindowManager.LayoutParams.MATCH_PARENT;

//            lp.height=height;
//            lp.width = width;
//            lp.height = height;
            win.setAttributes(lp);
            win.setGravity(Gravity.CENTER);
//            win.setWindowAnimations(R.style.Animation_Bottom_Rising);
            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(true);//点击外部取消
            dialog.setCancelable(params.canCancel);
            dialog.setParams(params);

            return dialog;
        }

    }


    private static final class Params {
        private boolean shadow = true;
        private boolean canCancel = true;
        private String time="";
        private SetTimeListener setTimeListener;

    }
}
