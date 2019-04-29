package com.hhkj.dyedu.view;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhkj.dyedu.R;

public class FileDialog extends Dialog {
    private ImageView iv_no;
    private TextView tv_file;

    private String filetip;

    public void setFiletip(String filetip) {
        this.filetip = filetip;
    }

    private ClickListenerInterface clickListenerInterface;

    public FileDialog(Context context) {
        super(context, R.style.Dialog_Msg);
    }
    public interface ClickListenerInterface{
        public void isCancel();//取消事件
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_dialog);

        setCanceledOnTouchOutside(false);

        initView();

        tv_file.setText(filetip);

    }
    public void initView(){
        iv_no = findViewById(R.id.iv_no);
        tv_file = findViewById(R.id.tv_file);

        iv_no.setOnClickListener(new clickListener());


    }
    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }
    private class clickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.iv_no:
                    clickListenerInterface.isCancel();
                    break;
            }
        }
    }
}
