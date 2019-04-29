package com.hhkj.dyedu.ui.activity.splash;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.ui.activity.LoginSuccessActivity;
import com.hhkj.dyedu.ui.activity.base.BaseActivity;
import com.hhkj.dyedu.ui.activity.user.LoginActivity;
import com.hhkj.dyedu.view.NoticeDialog;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.hhkj.dyedu.utils.LogUtil;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/9/17
 *
 * @author zangyi 767450430
 * 启动页面
 */
public class SplashActivity extends BaseActivity {
    private static final int REQUEST_CODE_PERMISSION = 100;
    /**
     * 回调监听。
     */
    private static final int REQUEST_CODE_SETTING = 300;
    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：
            // AndPermission.rationaleDialog(Context, Rationale).show();
            
            // 自定义对话框。
            AlertDialog.newBuilder(getContext())
                    .setTitle("提示")
                    .setMessage("我们需要的一些必要权限被禁止，请授权给我们。")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("就不", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            LogUtil.i("获取权限成功");
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    
                    if (CacheUtils.isFirst()) {
                        CacheUtils.setFirst(false);
                        jumpToActivity(GuideActivity.class);
                        finish();
                        return;
                    }
                    
                    if (CacheUtils.isLogin()) {
                        jumpToActivity(LoginSuccessActivity.class);
                    } else {
                        jumpToActivity(LoginActivity.class);
                    }
                    finish();
//                    timer.start();
//                    Toast.makeText(MainActivity.this,, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        
        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    showToast("授权失败");
                    finish();
                    break;
                }
            }
            
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(getContext(), deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(SplashActivity.this, REQUEST_CODE_SETTING).show();
                
                // 第二种：用自定义的提示语。
//             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                     .setTitle("权限申请失败")
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                     .setPositiveButton("好，去设置")
//                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingHandle = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingHandle.execute();
//            你的dialog点击了取消调用：
//            settingHandle.cancel();
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getPermission();
//        CacheUtils.setLanguage("en");
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_splash;
    }
    
    private void getPermission() {
        
        // 申请权限。
        AndPermission.with(getContext())
                .requestCode(REQUEST_CODE_PERMISSION)
                .permission(Permission.STORAGE)
                .callback(permissionListener)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(rationaleListener)
                .start();
        
    }
}
