package com.hhkj.dyedu.ui.activity.splash;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.DepthPageTransformer;
import com.hhkj.dyedu.adapter.ViewPagerAdatper;
import com.hhkj.dyedu.ui.activity.base.BaseActivity;
import com.hhkj.dyedu.ui.activity.splash.SplashActivity;
import com.hhkj.dyedu.view.PageIndicator;
import com.hhkj.dyedu.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zangyi_shuai_ge on 2018/9/17
 *
 * @author zangyi 767450430
 */
public class GuideActivity extends BaseActivity {
    
    
    @BindView(R.id.indicator)
    PageIndicator indicator;
    private ViewPager mIn_vp;
    private List<View> mViewList;
    private int mDistance;
    private LinearLayout mBtn_next;
    
    @Override
    public int setLayoutId() {
        return R.layout.activity_guide;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initView();
        initData();
        mIn_vp.setAdapter(new ViewPagerAdatper(mViewList));
        moveDots();
        mIn_vp.setPageTransformer(true, new DepthPageTransformer());
        
        indicator.setChooseSrcId(R.mipmap.circle_02);
        indicator.setNormalSrcId(R.mipmap.circle_01);
        indicator.addIndicator(mViewList.size(), 25, 15);
        
        
        mBtn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(SplashActivity.class);
                finish();
            }
        });
    }
    
    private int nowState;
    private boolean isJump = false;
    
    private void moveDots() {
        mIn_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
                LogUtil.i("onPageScrolled", position + "positionOffset=" + positionOffset + "positionOffsetPixels" + positionOffsetPixels);
                
                LogUtil.i("" + mIn_vp.getCurrentItem());
                if(position==2&&nowState==1){
                    if(!isJump){
                        isJump=true;
                        jumpToActivity(SplashActivity.class);
                        finish();
                    }
                }
                
//                if (position < mIn_vp.getCurrentItem() && nowState == 1) {
//                    if (!isJump) {
//                        isJump = true;
//
//                        finish();
//                    }
//                }
                
                
            }
            
            @Override
            public void onPageSelected(int position) {
                //页面跳转时，设置小圆点的margin
//                float leftMargin = mDistance * position;
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLight_dots.getLayoutParams();
//                params.leftMargin = (int) leftMargin;
//                mLight_dots.setLayoutParams(params);
                if (position == 2) {
                    mBtn_next.setVisibility(View.VISIBLE);
                }
                if (position != 2 && mBtn_next.getVisibility() == View.VISIBLE) {
                    mBtn_next.setVisibility(View.GONE);
                }
                indicator.updateIndicator(position);
            }
            
            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtil.i("onPageScrollStateChanged", "positionOffset=" + state);
                nowState = state;
            }
        });
    }
    
    
    private void initData() {
        mViewList = new ArrayList<View>();
        LayoutInflater lf = getLayoutInflater().from(getContext());
        View view1 = lf.inflate(R.layout.we_indicator1, null);
        View view2 = lf.inflate(R.layout.we_indicator2, null);
        View view3 = lf.inflate(R.layout.we_indicator3, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
    }
    
    private void initView() {
        mIn_vp = (ViewPager) findViewById(R.id.in_viewpager);
//        mLight_dots = (ImageView) findViewById(R.id.iv_light_dots);
        mBtn_next = findViewById(R.id.bt_next);
    }
}
