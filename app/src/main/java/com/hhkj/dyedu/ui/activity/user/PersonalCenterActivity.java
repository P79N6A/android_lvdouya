package com.hhkj.dyedu.ui.activity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.MainApplication;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.gson.profile;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.ActivityCollector;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadActivity;
import com.hhkj.dyedu.ui.activity.splash.SplashActivity;
import com.hhkj.dyedu.ui.fragment.BillFragment;
import com.hhkj.dyedu.ui.fragment.BuyVipFragment;
import com.hhkj.dyedu.ui.fragment.CashFragment;
import com.hhkj.dyedu.ui.fragment.MyCourseFragment;
import com.hhkj.dyedu.ui.fragment.PayWebViewFragment;
import com.hhkj.dyedu.ui.fragment.PersonalInfoFragment;
import com.hhkj.dyedu.ui.fragment.RechargeFragment;
import com.hhkj.dyedu.ui.fragment.SettingsFragment;
import com.hhkj.dyedu.ui.fragment.WalletFragment;
import com.hhkj.dyedu.utils.ImageLoaderUtils;
import com.hhkj.dyedu.utils.LogUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.zuoni.common.dialog.choice.BottomGetPhotoDialog;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by zangyi_shuai_ge on 2018/5/25
 * <p>
 * 个人中心
 */

public class PersonalCenterActivity extends BaseTitleHeadActivity
        implements TakePhoto.TakeResultListener, InvokeListener {
    
    @BindView(R.id.iv01)
    ImageView iv01;
    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.layout01)
    LinearLayout layout01;
    @BindView(R.id.iv02)
    ImageView iv02;
    @BindView(R.id.tv02)
    TextView tv02;
    @BindView(R.id.layout02)
    LinearLayout layout02;
    @BindView(R.id.iv03)
    ImageView iv03;
    @BindView(R.id.tv03)
    TextView tv03;
    @BindView(R.id.layout03)
    LinearLayout layout03;
    @BindView(R.id.layout05)
    LinearLayout layout05;
    @BindView(R.id.iv0100)
    ImageView iv0100;
    @BindView(R.id.iv06)
    ImageView iv06;
    @BindView(R.id.tv06)
    TextView tv06;
    @BindView(R.id.layout06)
    LinearLayout layout06;
    
    private FragmentManager fragmentManager;
    private int nowPage;
    
    
    //fragment
    
    //个人资料
    private PersonalInfoFragment personalInfoFragment;
    
    
    //我的课程
    private MyCourseFragment myCourseFragment;
    
    
    //我的钱包
    
    private WalletFragment walletFragment;
    
    private BuyVipFragment buyVipFragment;
    private RechargeFragment rechargeFragment;
    private CashFragment cashFragment;
    
    private PayWebViewFragment payWebViewFragment;
    
    //我
    
    private BillFragment billFragment;
    
    
    private SettingsFragment settingsFragment;
    
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private BottomGetPhotoDialog bottomGetPhotoDialog;
    private File headImageFile;
    
    @Override
    public int setLayoutId() {
        return R.layout.activity_personal_center;
    }
    
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        back();
    }
    
    private void back() {
        
        Fragment fragment = getVisibleFragment();
        if (fragment == null) {
            finish();
        } else {
            String name = fragment.getClass().getSimpleName();
            LogUtil.i("名称" + name);
            if (
                    name.equals("RechargeFragment")
                            | name.equals("PayWebViewFragment")
                            | name.equals("CashFragment")
                            | name.equals("BillFragment")
                            | name.equals("BuyVipFragment")
                    ) {
                resetMenu(2);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_content, walletFragment);
                transaction.commitAllowingStateLoss();
            } else {
                finish();
            }
        }
    }
    
    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }
    
    private void resetMenu(int i) {
        
        nowPage = i;
        
        layout01.setBackgroundColor(Color.parseColor("#61BDEC"));
        layout02.setBackgroundColor(Color.parseColor("#61BDEC"));
        layout03.setBackgroundColor(Color.parseColor("#61BDEC"));
        layout06.setBackgroundColor(Color.parseColor("#61BDEC"));
        
        tv01.setTextColor(Color.parseColor("#FFFFFF"));
        tv02.setTextColor(Color.parseColor("#FFFFFF"));
        tv03.setTextColor(Color.parseColor("#FFFFFF"));
        tv06.setTextColor(Color.parseColor("#FFFFFF"));
        
        iv01.setImageResource(R.mipmap.new_93);
        iv02.setImageResource(R.mipmap.new_90);
        iv03.setImageResource(R.mipmap.new_83);
        iv06.setImageResource(R.mipmap.setting_01);
        
        
        if (i == 0) {
            layout01.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv01.setTextColor(Color.parseColor("#61BDEC"));
            iv01.setImageResource(R.mipmap.new_84);
        } else if (i == 1) {
            layout02.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv02.setTextColor(Color.parseColor("#61BDEC"));
            iv02.setImageResource(R.mipmap.new_80);
        } else if (i == 2) {
            layout03.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv03.setTextColor(Color.parseColor("#61BDEC"));
            iv03.setImageResource(R.mipmap.new_92);
        } else if (i == 8) {
            layout06.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv06.setTextColor(Color.parseColor("#61BDEC"));
          iv06.setImageResource(R.mipmap.setting_02);
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_personal_center));
        getTakePhoto().onCreate(savedInstanceState);
        
        int doType = getIntent().getIntExtra("doType", 0);
        
        
        if (MainApplication.role == 2) {
            layout01.setVisibility(View.INVISIBLE);
            layout02.setVisibility(View.INVISIBLE);
            layout03.setVisibility(View.INVISIBLE);
        }
        
        
        fragmentManager = getSupportFragmentManager();
        
        
        personalInfoFragment = new PersonalInfoFragment();
        personalInfoFragment.setPersonalCenterActivity(this);
        
        myCourseFragment = new MyCourseFragment();
        myCourseFragment.setPersonalCenterActivity(this);
        
        walletFragment = new WalletFragment();
        walletFragment.setPersonalCenterActivity(this);
        
        buyVipFragment = new BuyVipFragment();
        buyVipFragment.setPersonalCenterActivity(this);
        
        rechargeFragment = new RechargeFragment();
        rechargeFragment.setPersonalCenterActivity(this);
        
        
        payWebViewFragment = new PayWebViewFragment();
        payWebViewFragment.setPersonalCenterActivity(this);
        
        cashFragment = new CashFragment();
        cashFragment.setPersonalCenterActivity(this);
        
        
        settingsFragment = new SettingsFragment();
        settingsFragment.setPersonalCenterActivity(this);
        
        billFragment = new BillFragment();
        billFragment.setPersonalCenterActivity(this);
        iv0100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeadClick();
            }
        });
        ivTopTitleHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeadClick();
            }
        });
        
        
        if (doType == 1) {
            //从购物车进来支付
            ivTopTitleHead.post(new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    payWebViewFragment.setOrderInfo(getIntent().getStringExtra("orderInfo"));
                    payWebViewFragment.setType(getIntent().getStringExtra("PayType"));
                    payWebViewFragment.setDoType(3);//购物车支付
                    transaction.replace(R.id.main_content, payWebViewFragment);
                    transaction.commitAllowingStateLoss();
                    resetMenu(2);
                }
            });
            
        } else if (doType == 2) {
            //购买会员
            ivTopTitleHead.post(new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    
                    transaction.replace(R.id.main_content, buyVipFragment);
                    transaction.commitAllowingStateLoss();
                    resetMenu(2);
                }
            });
        } else {
            ivTopTitleHead.post(new Runnable() {
                @Override
                public void run() {
                    //部分机型可能在fragment 没有初始化完成的时候就去加载导致报错
                    //这里post一下界面
                    onViewClicked(layout01);
                }
            });
        }
        
        
        setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }
    
    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }
    
    public void onHeadClick() {
        BottomGetPhotoDialog.Builder builder = new BottomGetPhotoDialog.Builder(getContext());
        builder.setGetPhotoOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                Uri imageUri = Uri.fromFile(file);
                takePhoto.onPickFromGalleryWithCrop(imageUri, new CropOptions.Builder().setAspectX(84).setAspectY(84).setWithOwnCrop(false).create());
                bottomGetPhotoDialog.dismiss();
            }
        });
        builder.setTakePhotoOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                takePhoto.onPickFromCaptureWithCrop(imageUri, new CropOptions.Builder().setAspectX(84).setAspectY(84).setWithOwnCrop(false).create());
                bottomGetPhotoDialog.dismiss();
            }
        });
        bottomGetPhotoDialog = builder.create();
        bottomGetPhotoDialog.show();
    }
    
    @OnClick({R.id.layout01, R.id.layout02, R.id.layout03,R.id.layout06})
    public void onViewClicked(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.layout01:
                //账户管理
                resetMenu(0);
                transaction.replace(R.id.main_content, personalInfoFragment);
//                transaction.commit();
                transaction.commitAllowingStateLoss();
                break;
            case R.id.layout02:
                //我的课程
                resetMenu(1);
                transaction.replace(R.id.main_content, myCourseFragment);
                transaction.commitAllowingStateLoss();
                break;
            case R.id.layout03:
                //我的钱包
                resetMenu(2);
                transaction.replace(R.id.main_content, walletFragment);
                transaction.commitAllowingStateLoss();
                break;
            case R.id.layout06:
                //设置
                resetMenu(8);
                transaction.replace(R.id.main_content, settingsFragment);
                transaction.commitAllowingStateLoss();
                break;
        }
    }
    
    public void rechargeFragmentEvent(String type, String money) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        payWebViewFragment.setMoney(money);
        payWebViewFragment.setType(type);
        payWebViewFragment.setDoType(2);
        transaction.replace(R.id.main_content, payWebViewFragment);
        transaction.commitAllowingStateLoss();
        resetMenu(2);
    }
    
    public void cashFragmentEvent(int event) {
        //doType 回调
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        
        switch (event) {
            
            case 1:
                //提现  回到我的钱包
                transaction.replace(R.id.main_content, walletFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
        
    }
    
    public void payWebViewFragmentEvent(int event) {
        //doType 回调
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        
        switch (event) {
            case 1:
                //vip购买
                transaction.replace(R.id.main_content, buyVipFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                //充值  回到我的钱包
                transaction.replace(R.id.main_content, walletFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                setResult(10086);
                finish();
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
        
    }
    
    public void walletFragmentEvent(int event) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        
        switch (event) {
            case 1:
                //vip购买
                transaction.replace(R.id.main_content, buyVipFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                //充值
                transaction.replace(R.id.main_content, rechargeFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                //提现
                transaction.replace(R.id.main_content, cashFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 4:
                transaction.replace(R.id.main_content, billFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 5:
                break;
            case 6:
                break;
        }
        
    }
    
    public void buyVipFragmentEvent(String type, int cardId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        payWebViewFragment.setCardId(cardId);
        payWebViewFragment.setType(type);
        payWebViewFragment.setDoType(1);//vip支付
        transaction.replace(R.id.main_content, payWebViewFragment);
        transaction.commitAllowingStateLoss();
        resetMenu(2);
    }
    
    public void personalInfoFragmentEvent(int event) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        
        switch (event) {
            case 1:
                //vip购买
                transaction.replace(R.id.main_content, buyVipFragment);
                transaction.commitAllowingStateLoss();
                resetMenu(2);
                
                break;
            case 2:
                
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
        
    }
    
    @OnClick(R.id.layout05)
    public void onViewClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("是否退出登录？");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();
                CacheUtils.setLogin(false);
                CacheUtils.setToken("");
                jumpToActivity(SplashActivity.class);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }
    
    @Override
    public void takeSuccess(TResult result) {
        //开启鲁班压缩
        Luban.with(this)
                .load(new File(result.getImages().get(0).getOriginalPath()))   //传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                    
                    }
                    
                    @Override
                    public void onSuccess(File file) {
//                        isSetBookImage = true;
                        if (!file.exists()) {
                            return;
                        }
                        headImageFile = file;
//                        Glide.with(getContext())
//                                .load(file)
//                                .asBitmap()
//                                .into(ivTopTitleHead);
                        
                        profile(headImageFile);
                    }
                    
                    @Override
                    public void onError(Throwable e) {
                    
                    }
                }).launch();    //启动压缩
    }
    
    private void profile(File headImageFile) {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.profile);//"修改头像"
        httpRequest.add("img", headImageFile);
        CallServer.getInstance().postRequest("修改头像", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        
                        profile info = gson.fromJson(response, profile.class);
                        showToast(info.getMsg());
                        
                        if (info.getCode() == 1) {
                            
                            CacheUtils.setHeadImage(info.getData());
                            CacheUtils.setCacheHeadImage(CacheUtils.getUsername(), info.getData());
                            ImageLoaderUtils.setHeadImage(info.getData(), ivTopTitleHead);
//                            CacheUtils.set
                        } else {
                        }
                    }
                    
                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
        
        
    }
    
    @Override
    public void takeFail(TResult result, String msg) {
    
    }
    
    @Override
    public void takeCancel() {
    
    }
    
    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }
}
