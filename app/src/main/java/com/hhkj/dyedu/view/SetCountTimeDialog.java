package com.hhkj.dyedu.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.callback.SetTimeListener;
import com.zuoni.common.utils.ToastUtils;


/**
 * 从底部弹出的Dialog
 */
public class SetCountTimeDialog extends Dialog {

    private Params params;

    public SetCountTimeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void setParams(Params params) {
        this.params = params;
    }


    public static class Builder {
        private final Context context;
        private final Params params;
        private SetCountTimeDialog dialog;
        private TextView tv01;
        private EditText et01;


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

        public SetCountTimeDialog create() {
            dialog = new SetCountTimeDialog(context, params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);

            View view = LayoutInflater.from(context).inflate(R.layout.dialog_set_count_down_time, null);


            tv01 = view.findViewById(R.id.tv02);
            et01 = view.findViewById(R.id.et01);
            if(!params.time.equals("")){
                et01.setText(params.time);
            }

            if(params.setTimeListener!=null){
                tv01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(et01.getText().toString().trim().equals("")){
                            ToastUtils.showToast(context,"请输入时间");
                        }else {
                            params.setTimeListener.setTime(et01.getText().toString());
                        }
                    }
                });
            }
            Window win = dialog.getWindow();
            assert win != null;
            win.getDecorView().setPadding(0, 0, 0, 0);

            int width =(int) context.getResources().getDimension(R.dimen.px_776);
            int height =(int) context.getResources().getDimension(R.dimen.px_490);
            WindowManager.LayoutParams lp = win.getAttributes();
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            lp.height=height;
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
        private String time="";
        private SetTimeListener setTimeListener;

    }
}
