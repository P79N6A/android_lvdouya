package com.hhkj.dyedu.http;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.GlobalVariable;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.model.SmsBean;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.utils.LoggerUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;
import com.hhkj.dyedu.utils.LogUtil;

import static com.hhkj.dyedu.utils.LoggerUtils.printHttpMap;


/**
 * Created by zangyi_shuai_ge on 2017/9/27
 * 网络访问辅助类
 */

public class CallServer {

    public static final int HTTP_SUCCESS_CODE = 1;
    private static CallServer instance;

    public static CallServer getInstance() {
        if (instance == null)
            synchronized (CallServer.class) {
                if (instance == null)
                    instance = new CallServer();
            }
        return instance;
    }

    private RequestQueue queue;
    private String log = "";

    private CallServer() {
        queue = NoHttp.newRequestQueue(8);
    }


    public void getSMS(SmsBean smsBean, final HttpResponseListener httpResponseListener, final Context context){
        HttpRequest httpRequest=new HttpRequest(AppUrl.sms_send);
        httpRequest.add("event",smsBean.getEvent());
        httpRequest.add("mobile",smsBean.getMobile());
        httpRequest.add("areaCode",smsBean.getAreaCode());
        postRequest("获取验证码",httpRequest,httpResponseListener,context);
    }



    public void postRequest(
            final String TAG,
            final HttpRequest request,
            final HttpResponseListener httpResponseListener,
            final Context context) {

        //固定参数
        request.add("token", CacheUtils.getToken());
//        request.add("language", CacheUtils.getl());
//        request.add("token", "b1d21740-a12a-429d-bf5e-3d8d7572ccbe");
//        request.add("username", CacheUtils.getUsername(context));
//        request.add("uid", CacheUtils.getUid(context));

//        if(request.url().equals(AppUrl.login)|request.url().equals(AppUrl.third_party_login)|request.url().equals(AppUrl.bind_phone)){
//            request.add("device_tokens", PushAgent.getInstance(context).getRegistrationId());
////            request.add("device_type","android");
//        }


        //打印日志
        printHttpMap(TAG,request);
        //发起网络请求
        SimpleResponseListener<String> listener = new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                super.onSucceed(what, response);

                if (response.get() == null) {
                    httpResponseListener.onFailed(response.getException());
                    return;
                }
                //打印日志
                LoggerUtils.json(TAG, response.get());

                Gson gson = new Gson();
                //请求拿到数据
                try {
                    //把数据转换成基类
                    BaseHttpResponse baseHttpResponse = gson.fromJson(response.get(), BaseHttpResponse.class);
                    //为3的时候为token失效
                    if (baseHttpResponse.getCode()==401) {
                        //发送广播
                        Intent intent = new Intent();
                        intent.setAction(GlobalVariable.BROADCAST_TOKEN_ERROR);
                        context.sendBroadcast(intent);
                    } else {
                        //其余的交给Activity处理
                        httpResponseListener.onSucceed(response.get(), gson);
                    }
                } catch (JsonSyntaxException e) {
                    //装换失败
//                    LogUtil.i("报错 手动报错" + e + "\n\n" + response.get());
                    httpResponseListener.onFailed(e);

                    //提示打印日志
//                    Intent intent = new Intent();
//                    intent.setAction(GlobalVariable.BROADCAST_SERVER_EXCEPTION);
//                    intent.putExtra("log", log + "\n\n\n" + response.get());
//                    context.sendBroadcast(intent);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                super.onFailed(what, response);
                //服务器异常
                LogUtil.i("报错 真的报错" + response.getException());
                httpResponseListener.onFailed(response.getException());
                //提示打印日志
//                Intent intent = new Intent();
//                intent.setAction(GlobalVariable.BROADCAST_SERVER_EXCEPTION);
//                intent.putExtra("log", log + response.getException());
//                context.sendBroadcast(intent);
            }
        };
        queue.add(1, request, listener);
    }

    public void postRequest2(
            final String TAG,
            final HttpRequest request,
            final HttpResponseListener httpResponseListener) {


        //打印日志
        printHttpMap(TAG,request);
        //发起网络请求
        SimpleResponseListener<String> listener = new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                super.onSucceed(what, response);

                if (response.get() == null) {
                    httpResponseListener.onFailed(response.getException());
                }else {
                    Gson gson = new Gson();
                    LoggerUtils.json(TAG, response.get());
                    httpResponseListener.onSucceed(response.get(), gson);
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                super.onFailed(what, response);
                //服务器异常
                LogUtil.i("报错 真的报错" + response.getException());
                httpResponseListener.onFailed(response.getException());
            }
        };
        queue.add(1, request, listener);
    }

    public void request2(final HttpRequest request, final HttpResponseListener httpResponseListener, final Context context) {

        SimpleResponseListener<String> listener = new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                super.onSucceed(what, response);
                httpResponseListener.onSucceed(response.get(), new Gson());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                super.onFailed(what, response);
            }
        };
        queue.add(1, request, listener);
    }

    public <T> void request(int what, Request<T> request, SimpleResponseListener<T> listener) {
        queue.add(what, request, listener);
    }

    // 完全退出app时，调用这个方法释放CPU。
    public void stop() {
        queue.stop();
    }

    public void cancelBySign(Object sign) {
        queue.cancelBySign(sign);
    }


}
