package com.hhkj.dyedu.view.dialog;

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
import com.hhkj.dyedu.callback.EditInfoListener;


/**
 * Created by zangyi_shuai_ge on 2018/7/27
 *
 * @author zangyi 767450430
 * 密码输入
 */
public class InputPasswordDialog extends Dialog {
    
    private TextView tv01, tv02, tvTitle;
    
    private EditText et01;
    
    private EditInfoListener editInfoListener;
    
    private Context context;
    
    public void setEditInfoListener(EditInfoListener editInfoListener) {
        this.editInfoListener = editInfoListener;
    }
    
    public InputPasswordDialog(final Context context) {
        super(context, R.style.Theme_Light_NoTitle_Dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_password, null);
        this.context = context;
        tv01 = view.findViewById(R.id.tv01);
        tv02 = view.findViewById(R.id.tv02);
        tvTitle = view.findViewById(R.id.tvTitle);
        et01 = view.findViewById(R.id.et01);
        
        
        tv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputPasswordDialog.this.dismiss();
            }
        });
        tv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (et01.getText().toString().trim().equals("")) {
                    com.zuoni.common.utils.ToastUtils.showToast(context, "请输入密码");
                } else {
                    editInfoListener.info(et01.getText().toString());
                }
            }
        });
        
        setDialogParams(view);
        
    }
    
    /**
     * 设置dialog 参数
     *
     * @param view
     */
    private void setDialogParams(View view) {
        
        Window win = this.getWindow();
        assert win != null;
        win.getDecorView().setPadding(0, 0, 0, 0);
        
        int width = (int) context.getResources().getDimension(R.dimen.px_780);
        int height = (int) context.getResources().getDimension(R.dimen.px_490);
        WindowManager.LayoutParams lp = win.getAttributes();
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = height;
        lp.width = width;
        win.setAttributes(lp);
        win.setGravity(Gravity.CENTER);
//            win.setWindowAnimations(R.style.Animation_Bottom_Rising);
        this.setContentView(view);
        this.setCanceledOnTouchOutside(false);//点击外部取消
        this.setCancelable(false);
    }
}
