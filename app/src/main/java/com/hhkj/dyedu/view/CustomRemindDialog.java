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
import com.hhkj.dyedu.bean.model.Wars;
import com.hhkj.dyedu.callback.SetTimeListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义提醒弹窗
 */
public class CustomRemindDialog extends Dialog {

    public CustomRemindDialog( Context context, int themeResId) {
        super(context, themeResId);
    }
    private Params params;

    private void setParams(Params params) {
        this.params = params;
    }

    public static class Builder {
        private final Context context;
        private final Params params;
        private CustomRemindDialog customRemindDialog;
        private TextView tv_custom_remind;
        private ImageView ivHead,ivClose;

        private Banner banner;
        private List<Wars> warsList;
        private String text;

        public Builder(Context context) {
            this.context = context;
            params = new Params();
        }



        public void setText(String text) {
            this.text = text;
        }

        public void setWarsList(List<Wars> warsList) {
            this.warsList = warsList;
        }

        public void setTime(String time){
            params.time=time;
        }

        public void setOnClickListener(SetTimeListener onClickListener) {
            params.setTimeListener = onClickListener;
        }

        public CustomRemindDialog create(){
            customRemindDialog = new CustomRemindDialog(context, params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_remid, null);

            tv_custom_remind = view.findViewById(R.id.tv_custom_remind);
            ivHead = view.findViewById(R.id.ivHead);
            ivClose= view.findViewById(R.id.ivClose);
            banner= view.findViewById(R.id.banner);
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customRemindDialog.dismiss();
                }
            });
            List<String> list=new ArrayList<>();
            banner.setImages(list);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
            banner.isAutoPlay(false);
            banner.stopAutoPlay();

            tv_custom_remind.setText(text);
            Window win = customRemindDialog.getWindow();
            assert win != null;
            win.getDecorView().setPadding(0, 0, 0, 0);

            win.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height =  WindowManager.LayoutParams.MATCH_PARENT;

            win.setAttributes(lp);
            win.setGravity(Gravity.CENTER);

            customRemindDialog.setContentView(view);
            customRemindDialog.setCanceledOnTouchOutside(true);//点击外部取消
            customRemindDialog.setCancelable(params.canCancel);
            customRemindDialog.setParams(params);
            return customRemindDialog;
        }
    }

    private static final class Params {
        private boolean shadow = true;
        private boolean canCancel = true;
        private String time="";
        private SetTimeListener setTimeListener;

    }
}
