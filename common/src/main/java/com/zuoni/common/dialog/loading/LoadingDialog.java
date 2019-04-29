package com.zuoni.common.dialog.loading;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zuoni.common.R;


/**
 * LoadingDialog
 */
public class LoadingDialog extends Dialog {

    private Params params;

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void setParams(Params params) {
        this.params = params;
    }


    public static class Builder {
        private  Context context;
        private  Params params;
        private TextView tvMessage;
        private LoadingDialog loadingDialog;
        private long lastClickTime=0;


        public void setTvMessage(String message) {
            if (tvMessage != null) {
                tvMessage.setText(message);
            }
        }

        public Builder(Context context) {
            this.context = context;
            params = new Params();
        }

        public Builder setMessage(String message) {
            params.message = message;
            return this;
        }



        public LoadingDialog create() {
            loadingDialog = new LoadingDialog(context, params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
            tvMessage = (TextView) view.findViewById(R.id.tvMessage);

            if (!params.message.equals("")) {
                tvMessage.setText(params.message);
            }
            Window win = loadingDialog.getWindow();
            assert win != null;
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
            win.setGravity(Gravity.CENTER);
//            win.setWindowAnimations(R.style.Animation_Bottom_Rising);
            loadingDialog.setContentView(view);
            loadingDialog.setCanceledOnTouchOutside(params.canCancel);//点击外部取消
            loadingDialog.setCancelable(params.canCancel);
            loadingDialog.setParams(params);
            loadingDialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //抬起
                        if(event.getAction()==KeyEvent.ACTION_DOWN){
                            if(System.currentTimeMillis()-lastClickTime<=1000){
                                if(loadingDialog.isShowing()){
                                    loadingDialog.dismiss();
                                }
                                lastClickTime=0;
                            }else {
                                lastClickTime=System.currentTimeMillis();
                            }
                        }
                    }
                    return false;
                }
            });
            return loadingDialog;
        }
    }

    private static final class Params {
        private boolean shadow = false;
        private boolean canCancel = true;
        private String message = "";
    }
}
