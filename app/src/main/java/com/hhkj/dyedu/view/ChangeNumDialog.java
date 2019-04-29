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
import com.hhkj.dyedu.callback.NumChangeListener;


/**
 * 从底部弹出的Dialog
 */
public class ChangeNumDialog extends Dialog {

    private Params params;

    public ChangeNumDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void setParams(Params params) {
        this.params = params;
    }


    public static class Builder {
        private final Context context;
        private final Params params;
        private ChangeNumDialog dialog;
        private TextView tv01,tv02,tvReduce,tvAdd;
        private EditText et01;


        public Builder(Context context) {
            this.context = context;
            params = new Params();
        }

        public void setPos(int pos){
            params.pos=pos;
        }
        public void setNum(int pos){
            params.num=pos;
        }
        public void setOnClickListener(NumChangeListener onClickListener) {
            params.numChangeListener = onClickListener;
        }

        public ChangeNumDialog create() {
            dialog = new ChangeNumDialog(context, params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);

            View view = LayoutInflater.from(context).inflate(R.layout.dialog_change_num, null);


            tv01 = view.findViewById(R.id.tv01);
            tv02 = view.findViewById(R.id.tv02);

            tvReduce = view.findViewById(R.id.tvReduce);
            tvAdd = view.findViewById(R.id.tvAdd);


            et01 = view.findViewById(R.id.etInputNum);

            et01.setText(params.num+"");

            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num;
                    if(et01.getText().toString().trim().equals("")){
                        num=1;
                    }else {
                        num=Integer.parseInt(et01.getText().toString().trim());
                    }
                    num=num+1;

                    et01.setText(num+"");


                }
            });
            tvReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int num;

                    if(et01.getText().toString().trim().equals("")){
                        num=1;

                    }else {
                        num=Integer.parseInt(et01.getText().toString().trim());
                    }
                    num=num-1;
                    if( num<1){
                        num=1;
                    }
                    et01.setText(num+"");
                }
            });


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
                    int num;

                    if(et01.getText().toString().trim().equals("")){
                        num=1;
                    }else {
                        num=Integer.parseInt(et01.getText().toString().trim());
                    }

                    if(num==0){
                        params.numChangeListener.numChange(params.num,params.pos);
                    }else {
                        params.numChangeListener.numChange(num,params.pos);
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
        private NumChangeListener numChangeListener;


        private int pos;
        private int num;

    }
}
