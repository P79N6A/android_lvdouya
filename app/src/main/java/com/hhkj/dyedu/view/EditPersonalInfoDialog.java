package com.hhkj.dyedu.view;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.callback.EditInfoListener;
import com.zuoni.common.utils.ToastUtils;


/**
 * 从底部弹出的Dialog
 */
public class EditPersonalInfoDialog extends Dialog {

    private Params params;

    public EditPersonalInfoDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void setParams(Params params) {
        this.params = params;
    }


    public static class Builder {
        private final Context context;
        private final Params params;
        private EditPersonalInfoDialog dialog;
        private TextView tv01,tv02,tvTitle;
        private EditText et01;


        public Builder(Context context) {
            this.context = context;
            params = new Params();
        }

        public void doType(int  info){
            params.doType=info;
        }
        public void setTitle(String title){
            params.title=title;
        }
        public void setOldInfo(String info){
            params.oldInfo=info;
        }
        public void setOnClickListener(EditInfoListener onClickListener) {
            params.listener = onClickListener;
        }

        public EditPersonalInfoDialog create() {
            dialog = new EditPersonalInfoDialog(context, params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);

            View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_personal_info, null);





            tv01 = view.findViewById(R.id.tv01);
            tv02 = view.findViewById(R.id.tv02);

            tvTitle = view.findViewById(R.id.tvTitle);


            et01 = view.findViewById(R.id.et01);

            et01.setText(params.oldInfo);
            tvTitle.setText(params.title);

            if(params.doType==1){
                //年龄
                et01.setInputType(InputType.TYPE_CLASS_NUMBER);
                et01.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
            }


//            tvReduce.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    int num;
//
//                    if(et01.getText().toString().trim().equals("")){
//                        num=1;
//
//                    }else {
//                        num=Integer.parseInt(et01.getText().toString().trim());
//                    }
//                    num=num-1;
//                    if( num<1){
//                        num=1;
//                    }
//                    et01.setText(num+"");
//                }
//            });


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

            tv02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     params.listener.info(et01.getText().toString().trim());
                     String info=et01.getText().toString().trim();

                     if(info.equals("")){
                         if(params.doType==0){
                             ToastUtils.showToast(context,"请输入昵称");
                         }else if(params.doType==1){
                             ToastUtils.showToast(context,"请输入年龄");
                         }else if(params.doType==3){
                             ToastUtils.showToast(context,"请输入职业");
                         }else if(params.doType==4){
                             ToastUtils.showToast(context,"请输入班级名称");
                         }
                     }else {
                         params.listener.info(et01.getText().toString().trim());
                         dialog.dismiss();
                     }

                }
            });

            tv01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Window win = dialog.getWindow();
            assert win != null;
            win.getDecorView().setPadding(0, 0, 0, 0);

            int width = (int) context.getResources().getDimension(R.dimen.px_640);
            int height =(int) context.getResources().getDimension(R.dimen.px_440);


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
        private EditInfoListener listener;


        private int pos;
        private int num;

        private String title;
        private String oldInfo;
        private int doType;



    }
}
