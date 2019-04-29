package com.hhkj.dyedu.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhkj.dyedu.ui.activity.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/11/8
 */

public abstract class BaseFragment extends Fragment {
    public BaseActivity activity;
    public View mView;
    Unbinder unbinder;

    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * Loading
     */
    public void showLoading() {
       activity. showLoading();
    }

    public void closeLoading() {
        activity.closeLoading();
    }

    public boolean isInitView=false;

    public boolean isGetData=false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(setLayoutId(), null);
        }
        unbinder = ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    public abstract int setLayoutId();

    public abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
