package com.hhkj.dyedu.ui.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.model.ThemeCategory;
import com.hhkj.dyedu.bean.model.ThemeCustomView;
import com.hhkj.dyedu.ui.activity.ThemeListActivity;
import com.hhkj.dyedu.ui.activity.WebViewActivity;
import com.hhkj.dyedu.ui.fragment.base.BaseFragment;
import com.hhkj.dyedu.utils.ImageLoaderUtils;
import com.hhkj.dyedu.utils.LogUtil;
import com.hhkj.dyedu.utils.LoggerUtils;

import java.util.ArrayList;
import java.util.List;

public class ThemeCenterFragment extends BaseFragment {
    private int layoutId;
    private ThemeCategory themeCategory;

    public void setViewType(int viewType) {
        LogUtil.i("拿到的布局"+viewType);
        switch (viewType) {
            case 1:
                layoutId = R.layout.fragment_theme_category_center_1;
                break;
            case 2:
                layoutId = R.layout.fragment_theme_category_center_2;
                break;
            case 3:
                layoutId = R.layout.fragment_theme_category_center_3;
                break;
            case 4:
                layoutId = R.layout.fragment_theme_category_center_4;
                break;
            case 5:
                layoutId = R.layout.fragment_theme_category_center_5;
                break;
            case 6:
                layoutId = R.layout.fragment_theme_category_center_6;
                break;
            case 7:
                layoutId = R.layout.fragment_theme_category_center_7;
                break;
            case 8:
                layoutId = R.layout.fragment_theme_category_center_8;
                break;
            default:
                layoutId = R.layout.fragment_theme_category_center_8;
                break;
        }
    }

    private List<ThemeCustomView> themeCustomViews;
    private List<ImageView> imageViews;

    public void setThemeCustomViews(List<ThemeCustomView> themeCustomViews) {
        this.themeCustomViews = themeCustomViews;
        LoggerUtils.json("拿到的布局",new Gson().toJson(themeCustomViews));

    }

    @Override
    public int setLayoutId() {
        return layoutId;
    }

    @Override
    public void initView() {

        imageViews=new ArrayList<>();
        imageViews.add(mView.findViewById(R.id.iv01));
        imageViews.add(mView.findViewById(R.id.iv02));
        imageViews.add(mView.findViewById(R.id.iv03));
        imageViews.add(mView.findViewById(R.id.iv04));
        imageViews.add(mView.findViewById(R.id.iv05));
        imageViews.add(mView.findViewById(R.id.iv06));
        imageViews.add(mView.findViewById(R.id.iv07));
//        imageViews.add(mView.findViewById(R.id.iv08));
        themeCategory = (ThemeCategory)getActivity().getIntent().getSerializableExtra("ThemeCategory");

        for (int i = 0; i <themeCustomViews.size() ; i++) {
            try{
                ThemeCustomView themeCustomView=themeCustomViews.get(i);
                ImageView imageView=imageViews.get(i);
                ImageLoaderUtils.setCourseImage(themeCustomView.getImage(),imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (themeCustomView.getTag() == 0){
                                if (!themeCustomView.getLink().equals("")){
                                    startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("url",themeCustomView.getLink()));
                                }
                            }else if (themeCustomView.getTag() == 1){
                                Intent intent = new Intent(getActivity(), ThemeListActivity.class);
                                intent.putExtra("ThemeCategory", themeCategory);
                                startActivity(intent);
                            }
                        }
                    });
            }catch (Exception e){

            }
        }

    }

}
