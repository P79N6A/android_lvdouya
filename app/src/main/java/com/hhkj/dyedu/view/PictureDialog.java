package com.hhkj.dyedu.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.utils.ImageLoaderUtils;

import java.io.File;


/**
 * 从底部弹出的Dialog
 */
public class PictureDialog extends Dialog {

    private Params params;

    public PictureDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void setParams(Params params) {
        this.params = params;
    }


    public static class Builder {
        private final Context context;
        private final Params params;
        private ImageView ivPicture;
        private PictureDialog dialog;


        public Builder(Context context) {
            this.context = context;
            params = new Params();
        }

        public void show(File file){

            ImageLoaderUtils.setImage(context,file,ivPicture);

            if(!dialog.isShowing()){
                dialog.show();
            }
        }

        public PictureDialog create() {
            dialog = new PictureDialog(context, params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);

            View view = LayoutInflater.from(context).inflate(R.layout.dialog_picture_01, null);


            ivPicture = view.findViewById(R.id.ivPicture);


            Window win = dialog.getWindow();
            assert win != null;
            win.getDecorView().setPadding(0, 0, 0, 0);

            int width = SizeUtils.dp2px( 410);
            int height = width;


            WindowManager.LayoutParams lp = win.getAttributes();
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

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
    }
}
