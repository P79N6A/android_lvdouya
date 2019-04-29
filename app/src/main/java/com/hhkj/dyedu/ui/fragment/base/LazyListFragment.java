package com.hhkj.dyedu.ui.fragment.base;

import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.DefaultAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/3/21
 * 列表fragment 基类
 */

public abstract class LazyListFragment extends LazyFragment {

//    public int pageNum = 1;

    @BindView(R.id.refreshLayout)
    public SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.ivNoData)
    ImageView ivNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.layoutNoData)
    LinearLayout layoutNoData;

    @Override
    public void initView() {
        //
        super.initView();
        //使上拉加载具有弹性效果：
        smartRefreshLayout.setEnableAutoLoadMore(false);
        List<String> defaultList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            defaultList.add("默认列表" + i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DefaultAdapter quickAdapter = new DefaultAdapter();

        recyclerView.setAdapter(quickAdapter);

        quickAdapter.replaceData(defaultList);

        quickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.title:
                        activity.showToast("点击标题" + position);
                        break;
                    case R.id.content:
                        break;
                }
            }
        });

    }

    /**
     * @param resId 没有数据图片资源
     * @param text  文字提示
     */
    public void setNoDataInfo(@DrawableRes int resId, String text) {
        ivNoData.setImageResource(resId);
        tvNoData.setText(text);
    }

    /**
     * @param visibility 没有数据布局可见状态
     */
    public void setNoDataVisibility(int visibility) {
        layoutNoData.setVisibility(visibility);
    }

    /**
     * @param left   the left padding in pixels
     * @param top    the top padding in pixels
     * @param right  the right padding in pixels
     * @param bottom the bottom padding in pixels
     */
    public void setRefreshLayoutPadding(int left, int top, int right, int bottom) {

        left = SizeUtils.dp2px(left);
        top = SizeUtils.dp2px(top);
        right = SizeUtils.dp2px(right);
        bottom = SizeUtils.dp2px(bottom);

        smartRefreshLayout.setPadding(left, top, right, bottom);
    }

}
