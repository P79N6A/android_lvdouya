package com.hhkj.dyedu.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.cache.CacheUtils;


public class NoticeDialog extends Dialog {
    private ImageView iv_cancel;
    private TextView tv_title;
    private WebView webView;
    private CheckBox cb_noNext;
    private boolean isCheck = false;

    private String html;

    public void setUrl(String html) {
        this.html = html;
    }

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    private ClickListenerInterface clickListenerInterface;


    public interface ClickListenerInterface {
        public void doCancel();//取消事件

        void setIsSelect(boolean isChecked);//CheckBox的状态
    }

    public NoticeDialog(Context context) {
        super(context, R.style.Dialog_Msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_dialog);

        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();


        isCheck = CacheUtils.getCheck();
        if (isCheck) {
            cb_noNext.setChecked(true);
        } else {
            cb_noNext.setChecked(false);
        }

        webView.loadDataWithBaseURL( null, html , "text/html", "UTF-8", null ) ;

        tv_title.setText(title);

    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        webView = findViewById(R.id.webView);
        iv_cancel = findViewById(R.id.iv_cancel);
        cb_noNext = findViewById(R.id.cb_noNext);
        cb_noNext.setChecked(CacheUtils.getCheck());

        iv_cancel.setOnClickListener(new clickListener());
        cb_noNext.setOnCheckedChangeListener(new clickListener());


    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.iv_cancel:
                    clickListenerInterface.doCancel();
                    break;
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            clickListenerInterface.setIsSelect(isChecked);
        }
    }

//    public void setContent(String content){
//        contentstr = content;
//    }

}
