package com.hhkj.dyedu.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.callback.OnDirectionListener;
import com.hhkj.dyedu.callback.PptPageChangeListener;
import com.hhkj.dyedu.utils.LogUtil;
import com.zuoni.common.utils.ToastUtils;

/**
 * Created by zangyi_shuai_ge on 2018/5/25
 */

public class PptWebView extends WebView {
    private static final int FLING_MIN_DISTANCE = 50;
    private static final int FLING_MIN_VELOCITY = 0;
    private Context context;
    private JavaScriptInterface mJavaScriptInterface;
    private GestureDetector mGestureDetector;
    private PptPageChangeListener pptPageChangeListener;
    private OnDirectionListener onDirectionListener;


    public void setOnDirectionListener(OnDirectionListener onDirectionListener) {
        this.onDirectionListener = onDirectionListener;
    }

    private View view;

    public PptWebView(Context context) {
        super(context);
        this.context = context;
    }

    public PptWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PptWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setPptPageChangeListener(PptPageChangeListener pptPageChangeListener) {
        this.pptPageChangeListener = pptPageChangeListener;
    }

    public void pgLeft() {
        this.post(new Runnable() {
            @Override
            public void run() {
                PptWebView.this.loadUrl("javascript:pgLeft()");
            }
        });
    }

    public void pgRight() {
        this.post(new Runnable() {
            @Override
            public void run() {
                PptWebView.this.loadUrl("javascript:pgRight()");
            }
        });
    }

    public void gosld(final int num) {
        this.post(new Runnable() {
            @Override
            public void run() {
                PptWebView.this.loadUrl("javascript:gosld(" + num + ")");
            }
        });
    }

    public void init() {
        mJavaScriptInterface = new JavaScriptInterface(context, pptPageChangeListener);
//        webView.loadUrl("http://39.104.60.111:8088/ppt/?f=MzkuMTA0LjYwLjExMS44MFxkZW1vLnBwdHg%3D");
        WebSettings webSettings = this.getSettings();
        //设置浏览标识
        webSettings.setUserAgentString("android");
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        //设定支持viewport
        webSettings.setUseWideViewPort(true);
        //
        this.addJavascriptInterface(mJavaScriptInterface, "android");

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

        });
        mGestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
//                LogUtil.i("GestureDetector", "onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                LogUtil.i("GestureDetector", "onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
//                LogUtil.i("GestureDetector", "onSingleTapUp");
//                int x = (int) event.getX();
//                if (x > view.getWidth() / 2) {
//                    PptWebView.this.getHandler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            LogUtil.i("GestureDetector", "onSingleTapUp--右");
//                            loadUrl("javascript:btmRight()");
//                        }
//                    });
//                } else {
//                    PptWebView.this.getHandler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            LogUtil.i("GestureDetector", "onSingleTapUp--左");
//                            loadUrl("javascript:btmLeft()");
//                        }
//                    });
//                }


                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                LogUtil.i("GestureDetector", "onScroll");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
//                LogUtil.i("GestureDetector", "onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                LogUtil.i("GestureDetector", "onFling");
                // TODO Auto-generated method stub
                if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    // Fling left
                    onDirectionListener.direction(OnDirectionListener.LEFT);
//                    LogUtil.i("GestureDetector", "onFling--左");
                } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    // Fling right
                    onDirectionListener.direction(OnDirectionListener.RIGHT);
//                    LogUtil.i("GestureDetector", "onFling--右");
                }
                return true;
            }
        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                view = v;
//                mGestureDetector.onTouchEvent(event);
                //事件不传递给webView
                return mGestureDetector.onTouchEvent(event);
            }
        });


        this.setHorizontalScrollBarEnabled(false);//水平不显示
        this.setVerticalScrollBarEnabled(false); //垂直不显示
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private class JavaScriptInterface {
        private Context mContext;
        private PptPageChangeListener pptPageChangeListener;

        JavaScriptInterface(Context c, PptPageChangeListener pptPageChangeListener) {
            mContext = c;
            this.pptPageChangeListener = pptPageChangeListener;
        }

        @JavascriptInterface
        public void scan() {
            LogUtil.i("JS调用原生代码");
        }

        @JavascriptInterface
        public void nowPageNum(final int page, final String note) {
            LogUtil.i("nowPageNum="+page+note);
            PptWebView.this.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    pptPageChangeListener.onPptPageChange(page,note);

//                    PptWebView.this.getHandler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            loadUrl("javascript:btmLeft()");
//                        }
//                    });

                }
            });
        }


        @JavascriptInterface
        public void showToast(String a) {
            ToastUtils.showToast(mContext, a);
            LogUtil.i("JS调用原生代码" + "payAction===" + a);
        }
        @JavascriptInterface
        public void getNote(String a) {
            LogUtil.i("JS调用原生代码" + "payAction===" + a);
        }
    }
}
