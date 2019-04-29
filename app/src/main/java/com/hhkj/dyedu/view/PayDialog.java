package com.hhkj.dyedu.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.callback.PayTypeListener;


/**
 * Created by zangyi_shuai_ge on 2018/6/8
 * 选择支付类型
 */
public class PayDialog extends Dialog {
    
    private Params params;
    
    public PayDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    
    private void setParams(Params params) {
        this.params = params;
    }
    
    
    public static class Builder {
        private final Context context;
        private final Params params;
        private PayDialog dialog;
        private ImageView tv01, tv02, tv03;
        
        
        public Builder(Context context) {
            this.context = context;
            params = new Params();
        }
        
        
        public void setOnClickListener(PayTypeListener onClickListener) {
            params.payTypeListener = onClickListener;
        }
        
        public PayDialog create() {
            dialog = new PayDialog(context, params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_pay, null);
            
            
            tv01 = view.findViewById(R.id.tv01);
            tv02 = view.findViewById(R.id.tv02);
            tv03 = view.findViewById(R.id.tv03);
            
            
            tv02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    params.payTypeListener.payType(PayTypeListener.TYPE_WE_CHAT);
                }
            });
            tv03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    params.payTypeListener.payType(PayTypeListener.TYPE_WALLET);
                }
            });
            tv01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    params.payTypeListener.payType(PayTypeListener.TYPE_ALI);//支付宝
                }
            });
            
            Window win = dialog.getWindow();
            assert win != null;
            win.getDecorView().setPadding(0, 0, 0, 0);
            
            int width = (int) context.getResources().getDimension(R.dimen.px_480);
            int height = (int) context.getResources().getDimension(R.dimen.px_480);
            
            
            WindowManager.LayoutParams lp = win.getAttributes();
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            
            lp.height = height;
            lp.width = width;
//            lp.height = height;
            win.setAttributes(lp);
            win.setGravity(Gravity.CENTER);
//            win.setWindowAnimations(R.style.Animation_Bottom_Rising);
            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(params.canCancel);//点击外部取消
            dialog.setCancelable(params.canCancel);
            dialog.setParams(params);
            
            return dialog;
        }
        
    }
    
    
    private static final class Params {
        private boolean shadow = true;
        private boolean canCancel = true;
        private String time = "";
        private PayTypeListener payTypeListener;
        
    }
}
