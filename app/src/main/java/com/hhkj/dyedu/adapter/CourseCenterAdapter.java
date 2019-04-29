//package com.hhkj.dyedu.adapter;
//
//import android.content.Intent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
////
//import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.hhkj.dyedu.R;
//import com.hhkj.dyedu.bean.model.BarInfo;
//import com.hhkj.dyedu.bean.model.CourseCenterBean;
//import com.hhkj.dyedu.ui.activity.ThemeListActivity;
//import com.hhkj.dyedu.ui.activity.CourseThemeActivity;
//import com.hhkj.dyedu.ui.activity.ThemeCategoryListActivity;
//import com.hhkj.dyedu.utils.ImageLoaderUtils;
//
//import java.util.List;
//
///**
// * Created by zangyi_shuai_ge on 2018/6/8
// *//*
//
//
//public class CourseCenterAdapter extends BaseMultiItemQuickAdapter<CourseCenterBean, BaseViewHolder> {
//
//    */
///**
//     * Same as QuickAdapter#QuickAdapter(Context,int) but with
//     * some initialization data.
//     *
//     * @param data A new list is created out of this one to avoid mutable list
//     *//*
//
//    public CourseCenterAdapter(List<CourseCenterBean> data) {
//        super(data);
//        addItemType(CourseCenterBean.ITEM_1, R.layout.rv_start_class_ppt_small_item);
////        addItemType(2, R.layout.rv_course_center_item_01);
//        addItemType(CourseCenterBean.LINE_4, R.layout.line_view_4);
//
//        addItemType(CourseCenterBean.ITEM_2, R.layout.rv_theme_item);
//        addItemType(CourseCenterBean.ITEM_3, R.layout.rv_category_item);
//        addItemType(CourseCenterBean.BAR, R.layout.rv_course_center_item_01);
//        addItemType(CourseCenterBean.LINE_1, R.layout.line_view_1);
//    }
//
//
//
//    @Override
//    protected void convert(BaseViewHolder helper, final CourseCenterBean item) {
//        switch (helper.getItemViewType()) {
//            case CourseCenterBean.ITEM_2:
//                //主题课程
//                helper.getView(R.id.ivHead).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext, CourseThemeActivity.class);
//                        intent.putExtra("Theme", item.getTheme());
//                        mContext.startActivity(intent);
//                    }
//                });
//                ImageLoaderUtils.setImage(item.getTheme().getImage(), (ImageView) helper.getView(R.id.ivHead));
//                helper.setText(R.id.tvTitle, item.getTheme().getTitle());
//                //会员免费
//                if(item.getTheme().getType()==2){
//                    helper.setVisible(R.id.layoutVip,true);
//                    helper.setText(R.id.tvPrice, "");
//                }else {
//                    helper.setText(R.id.tvPrice, "￥ " + item.getTheme().getPrice());
//                    helper.setVisible(R.id.layoutVip,false);
//                }
//
//                break;
//            case CourseCenterBean.ITEM_3:
//                //课程分类
//                ImageView ivHead = (ImageView) helper.getView(R.id.ivHead);
//                ImageLoaderUtils.setImage(item.getCategory().getImage(), ivHead);
//                helper.setText(R.id.tvName, item.getCategory().getName());
//                ivHead.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext, ThemeListActivity.class);
//                        intent.putExtra("categoryId", item.getCategory().getId());
//                        mContext.startActivity(intent);
//                    }
//                });
//                break;
//            case CourseCenterBean.BAR:
//                //课程分类
//                ImageView ivIcon = (ImageView) helper.getView(R.id.ivIcon);
//
//                if (item.getBarInfo().getType() == BarInfo.TYPE_1) {
//                    ivIcon.setImageResource(R.mipmap.home_3);
//                    helper.setText(R.id.tvTitle, item.getBarInfo().getTitle());
//                    TextView tvGo = helper.getView(R.id.tvGo);
//                    tvGo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(mContext, ThemeListActivity.class);
//                            intent.putExtra("categoryId", item.getBarInfo().getId());
////                            intent.putExtra("name",item.get)
//                            mContext.startActivity(intent);
////                            Theme theme = new Theme();
////                            theme.setId(item.getBarInfo().getId());
////                            Intent intent = new Intent(mContext, CourseThemeActivity.class);
////                            intent.putExtra("Theme", theme);
////                            mContext.startActivity(intent);
//                        }
//                    });
//
//
//                } else if (item.getBarInfo().getType() == BarInfo.TYPE_2) {
//                    ivIcon.setImageResource(R.mipmap.home_4);
//                    helper.setText(R.id.tvTitle, "课程分类");
//                    TextView tvGo = helper.getView(R.id.tvGo);
//                    tvGo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
////                            Theme theme = new Theme();
////                            theme.setId(item.getBarInfo().getId());
//                            Intent intent = new Intent(mContext, ThemeCategoryListActivity.class);
////                            intent.putExtra("Theme", theme);
//                            mContext.startActivity(intent);
//                        }
//                    });
//                }
//
//
////                ImageLoaderUtils.setHeadImage(item.getCategory().getImage(), (ImageView) helper.getView(R.id.ivHead));
////                helper.setText(R.id.tvName,item.getCategory().getName());
//                break;
//        }
//    }
//
//}*/
