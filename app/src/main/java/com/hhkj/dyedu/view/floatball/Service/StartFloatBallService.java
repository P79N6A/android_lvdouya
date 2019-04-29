package com.hhkj.dyedu.view.floatball.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hhkj.dyedu.view.floatball.Manager.ViewManager;


public class StartFloatBallService extends Service {

    public StartFloatBallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ViewManager manager = ViewManager.getInstance(this);
        manager.showFloatBall();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
