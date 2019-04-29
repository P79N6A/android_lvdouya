package com.hhkj.dyedu.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.callback.SetClassTimeListener;
import com.hhkj.dyedu.callback.SetTimeListener;
import com.zuoni.common.dialog.picker.view.LoopView;
import com.zuoni.common.utils.ToastUtils;

import java.util.Arrays;
import java.util.List;


/**
 * 从底部弹出的Dialog
 */
public class SetClassTimeDialog extends Dialog {

    private Params params;

    public SetClassTimeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 将数字传化为集合，并且补充0
     *
     * @param startNum 数字起点
     * @param count    数字个数
     * @return
     */
    private static List<String> d(int startNum, int count) {
        String[] values = new String[count];
        for (int i = startNum; i < startNum + count; i++) {
            String tempValue = (i < 10 ? "0" : "") + i;
            values[i - startNum] = tempValue;
        }
        return Arrays.asList(values);
    }

    private void setParams(Params params) {
        this.params = params;
    }

    public static class Builder {
        private final Context context;
        private final Params params;
        private SetClassTimeDialog dialog;

        private int start;
        private int end;

        public void setStart(int start) {
            this.start = start;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        private int timeType = 1;//1 早上 2下午 3晚上
        private LoopView loopView01, loopView02, loopView03, loopView04;

        private SetClassTimeListener setClassTimeListener;

        public Builder(Context context) {
            this.context = context;
            params = new Params();
        }

        public void setSetClassTimeListener(SetClassTimeListener setClassTimeListener) {
            this.setClassTimeListener = setClassTimeListener;
        }

        public void setTimeType(int timeType) {
            this.timeType = timeType;
        }

        public void setTime(String time) {
            params.time = time;
        }

        public void setOnClickListener(SetTimeListener onClickListener) {
            params.setTimeListener = onClickListener;
        }

        public SetClassTimeDialog create() {
            dialog = new SetClassTimeDialog(context, params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_set_class_time, null);

            List<String> list1;
            List<String> list2;

            if (timeType == 1) {
                list1 = d(7, 6);// 07:00 --12:59
            } else if (timeType == 2) {
                list1 = d(13, 5);//13:00 -- 17:59
            } else {
                list1 = d(18, 6);//16:00 -- 23:59
            }

            list2 = d(0, 60);


            loopView01 = view.findViewById(R.id.loopView01);
            loopView01.setCustomWidth(SizeUtils.dp2px(50));
            loopView01.setArrayList(list1);
            loopView01.setCurrentItem(4);
            loopView01.setNotLoop();






            loopView02 = view.findViewById(R.id.loopView02);
            loopView02.setCustomWidth(SizeUtils.dp2px(50));
            loopView02.setArrayList(list2);
            loopView02.setCurrentItem(5);
            loopView02.setNotLoop();


            loopView03 = view.findViewById(R.id.loopView03);
            loopView03.setCustomWidth(SizeUtils.dp2px(50));
            loopView03.setArrayList(list1);
            loopView03.setCurrentItem(4);
            loopView03.setNotLoop();


            loopView04 = view.findViewById(R.id.loopView04);
            loopView04.setCustomWidth(SizeUtils.dp2px(50));
            loopView04.setArrayList(list2);
            loopView04.setCurrentItem(5);
            loopView04.setNotLoop();

            //设置默认值


            if(start!=-1){

                String a1=add0(start/60);
                String a2=add0(start%60);

                String a3=add0(end/60);
                String a4=add0(end%60);

                for (int i = 0; i <list1.size() ; i++) {
                    if(list1.get(i).equals(a1)){
                        loopView01.setCurrentItem(i);
                        break;
                    }
                }
                for (int i = 0; i <list2.size() ; i++) {
                    if(list2.get(i).equals(a2)){
                        loopView02.setCurrentItem(i);
                        break;
                    }
                }
                for (int i = 0; i <list1.size() ; i++) {
                    if(list1.get(i).equals(a3)){
                        loopView03.setCurrentItem(i);
                        break;
                    }
                }
                for (int i = 0; i <list2.size() ; i++) {
                    if(list2.get(i).equals(a4)){
                        loopView04.setCurrentItem(i);
                        break;
                    }
                }
            }



//
            TextView tv01 = view.findViewById(R.id.tv01);
            TextView tv02 = view.findViewById(R.id.tv02);

            tv02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int a1=Integer.parseInt(loopView01.getCurrentItemValue());
                    int a2=Integer.parseInt(loopView02.getCurrentItemValue());
                    int a3=Integer.parseInt(loopView03.getCurrentItemValue());
                    int a4=Integer.parseInt(loopView04.getCurrentItemValue());

                    int start=a1*60+a2;
                    int end=a3*60+a4;

                    String des=loopView01.getCurrentItemValue()+":"+loopView02.getCurrentItemValue()+" ~ "+loopView03.getCurrentItemValue()+":"+loopView04.getCurrentItemValue();

                    if(end<=start){
                        ToastUtils.showToast(context,"结束时间必须大于开始时间");
                    }else {
                        setClassTimeListener.setClassTime(start,end,des,dialog);
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

            int width =(int) context.getResources().getDimension(R.dimen.px_776);
            int height = (int) context.getResources().getDimension(R.dimen.px_490);


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
        private String add0(int a1) {
            if (a1 < 10) {
                return "0" + a1;
            } else {
                return String.valueOf(a1);
            }
        }
    }

    private static final class Params {
        private boolean shadow = true;
        private boolean canCancel = true;
        private String time = "";
        private SetTimeListener setTimeListener;

    }
}
