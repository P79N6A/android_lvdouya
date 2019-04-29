package com.zuoni.common.dialog.choice;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zuoni.common.R;
import com.zuoni.common.utils.DensityUtils;


/**
 * 从底部弹出的Dialog
 */
public class BottomGetPhotoDialog extends Dialog {

    private Params params;

    public BottomGetPhotoDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void setParams(Params params) {
        this.params = params;
    }


    public static class Builder {
        private final Context context;
        private final Params params;

        public Builder(Context context) {
            this.context = context;
            params = new Params();
        }


        public Builder setTakePhotoOnClickListener(View.OnClickListener takePhotoOnClickListener) {
            params.takePhotoOnClickListener = takePhotoOnClickListener;
            return this;
        }

        public Builder setGetPhotoOnClickListener(View.OnClickListener getPhotoOnClickListener) {
            params.getPhotoOnClickListener = getPhotoOnClickListener;
            return this;
        }

        public BottomGetPhotoDialog create() {
            final BottomGetPhotoDialog dialog = new BottomGetPhotoDialog(context, params.shadow ?
                    R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);

            View view = LayoutInflater.from(context).inflate(R.layout.bottom_get_photo_dialog, null);
            LinearLayout layoutRight = (LinearLayout) view.findViewById(R.id.layoutRight);
            LinearLayout layoutLeft = (LinearLayout) view.findViewById(R.id.layoutLeft);

            if(params.takePhotoOnClickListener!=null){
                layoutLeft.setOnClickListener(params.takePhotoOnClickListener);
            }
            if(params.getPhotoOnClickListener!=null){
                layoutRight.setOnClickListener(params.getPhotoOnClickListener);
            }

            Button btCancel=(Button)view.findViewById(R.id.btCancel);
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Window win = dialog.getWindow();
            assert win != null;
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();

            int width = DensityUtils.dp2px( context,320);
            int height =  DensityUtils.dp2px(context,220);

            lp.width = width;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
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

        private View.OnClickListener getPhotoOnClickListener;
        private View.OnClickListener takePhotoOnClickListener;
    }
}
