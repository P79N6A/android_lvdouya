package com.hhkj.dyedu.ui.fragment.base;

/**
 * Created by zangyi_shuai_ge on 2018/3/21
 * Fragment 懒加载
 */

public abstract class LazyFragment extends BaseFragment {

    public boolean needRefresh = true;

    public void setNeedRefresh(boolean needRefresh) {
        this.needRefresh = needRefresh;
    }

    @Override
    public void initView() {
        if (getUserVisibleHint() && needRefresh) {
            lazyLoad();
        }
    }

    public abstract void lazyLoad();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getView() != null && needRefresh) {
            lazyLoad();
        }
    }

}
