package com.hhkj.dyedu.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.CourseAdapter04;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.gson.check;
import com.hhkj.dyedu.bean.gson.schedule_update;
import com.hhkj.dyedu.bean.gson.theme_info;
import com.hhkj.dyedu.bean.model.Theme;
import com.hhkj.dyedu.event.MoneyVipUpdateEvent;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseTitleHeadListActivity;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.utils.ImageLoaderUtils;
import com.hhkj.dyedu.view.ConfigureDialog;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.common.callback.SimpleTextWatcher;
import com.hhkj.dyedu.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zangyi_shuai_ge on 2018/6/8
 * 主题详情
 * 1、若该主题为会员课程 若已购买会员则可跳转到 播放ppt界面
 * 2、若是普通课程
 * 1、只能从我的课表选择某个老师中进去观看ppt
 * 2、其余均为
 */

public class CourseThemeActivity extends BaseTitleHeadListActivity {

    @BindView(R.id.ivHead)
    SelectableRoundedImageView ivHead;
    @BindView(R.id.tvHeader01)
    TextView tvHeader01;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.intro)
    TextView intro;
    @BindView(R.id.keywords)
    TextView keywords;
    @BindView(R.id.layoutVip01)
    LinearLayout layoutVip01;
    @BindView(R.id.layoutNoVip01)
    LinearLayout layoutNoVip01;
    @BindView(R.id.layoutVip02)
    View layoutVip02;
    @BindView(R.id.layoutNoVip02)
    LinearLayout layoutNoVip02;
    @BindView(R.id.etInputNum)
    EditText etInputNum;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.ivChooseAll)
    ImageView ivChooseAll;
    @BindView(R.id.layoutChooseAll)
    LinearLayout layoutChooseAll;
    @BindView(R.id.tvAllPrice)
    TextView tvAllPrice;
    @BindView(R.id.btBuy)
    Button btBuy;
    @BindView(R.id.tvVip)
    TextView tvVip;
    @BindView(R.id.btBuyVip)
    Button btBuyVip;
    @BindView(R.id.btBuyTheme)
    Button btBuyTheme;
    @BindView(R.id.ivChooseAll2)
    ImageView ivChooseAll2;
    @BindView(R.id.layoutChooseAll2)
    LinearLayout layoutChooseAll2;
    @BindView(R.id.layoutChooseCourseBar)
    LinearLayout layoutChooseCourseBar;
    @BindView(R.id.layoutMain01)
    LinearLayout layoutMain01;
    @BindView(R.id.layoutMain02)
    LinearLayout layoutMain02;


    private CourseAdapter04 courseAdapter04;

    private Theme theme;
    private boolean isChooseAll = false;
    private String scheduleId;


    private int showTag = 1;//显示类型 走购买模块  2查看 点击跳转ppt页面  3选择小节到我的课程中去
    private String url;
    private String title2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);


        layoutMain01.setVisibility(View.INVISIBLE);
        layoutMain02.setVisibility(View.INVISIBLE);

        //获取会员状态
        MoneyVipUpdateEvent event = new MoneyVipUpdateEvent();
        if (event.getVipTime().equals("0")) {
            tvVip.setText("本课程为会员课程，购买会员后可无限制使用");
        } else {
            tvVip.setText("您已是会员,可无限制使用本主题");
        }

        scheduleId = getIntent().getStringExtra("scheduleId");
        //显示类型 走购买模块  2查看 点击跳转ppt页面  3选择小节到我的课程中去
        //1 显示全部
        showTag = getIntent().getIntExtra("showTag", 1);
        //查看配置
        findViewById(R.id.ivLook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigureDialog.Builder builder = new ConfigureDialog.Builder(getContext());
                builder.setUrl(url);
                builder.setText(title2);
                builder.create().show();
            }
        });


        setTitle("课程分类");
        theme = (Theme) getIntent().getSerializableExtra("Theme");
        ImageLoaderUtils.setImage(theme.getImage(), ivHead);

//        setRefreshLayoutBg(Color.parseColor("#ffffff"));

//        smartRefreshLayout.setPadding(34, 34, 34, 34);
//        smartRefreshLayout.setBackgroundResource(R.drawable.bg_45);


        setNoDataVisibility(View.GONE);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setEnableRefresh(false);

        courseAdapter04 = new CourseAdapter04();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(courseAdapter04);

        etInputNum.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculationMoney();
            }
        });

        findViewById(R.id.btBuyTheme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopcarAdd(1, theme.getId() + "", "1");
            }
        });


        theme_info();


    }

    private void calculationMoney() {

        double money = 0;
        int num = 0;
        String a = etInputNum.getText().toString().trim();
        if (a.equals("")) {
            num = 0;
        } else {
            num = Integer.parseInt(a);
        }

        if (num == 0) {
            tvAllPrice.setText("￥ " + "0.0");
            return;
        }

        int size = courseAdapter04.getData().size();
        for (int i = 0; i < size; i++) {
            if (courseAdapter04.getData().get(i).isChoose()) {
                money = money + num * Double.parseDouble(courseAdapter04.getData().get(i).getPrice());
            }
        }
        tvAllPrice.setText("￥ " + money);
    }

    private void shopcarAdd(int type, String id, String num) {

        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.shopcarAdd);//"加入购物车"
        httpRequest.add("type", type);
        httpRequest.add("curriculumId", id);
        httpRequest.add("num", num);
        CallServer.getInstance().postRequest("加入购物车", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
//                        showToast(info.getMsg());
                        if (info.getCode() == 1) {
                            jumpToActivity(ShoppingCartActivity.class);
                        } else {
                            showToast(info.getMsg());
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());


    }

    private void theme_info() {
        CallServer.getInstance().cancelBySign(TAG);//取消上次请求
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.theme_info);//"主题课程详情"
        httpRequest.add("themeId", theme.getId());

        /**
         * type : 1 所有  2已分配的 3、已购购买未分配 4已购买
         * type 对应显示的UI
         * vip: 1、对应显示所有课程 从课表选课入口进入的  隐藏普通课程购买的UI
         *      2、对应从我的课表中入口进去点击已添加的课程  这时候可直接观看
         *
         */


        //scheduleId : 2、3 状态要传


        httpRequest.add("type", showTag);

        if (scheduleId != null && !scheduleId.equals("-2")) {
            httpRequest.add("scheduleId", scheduleId);
        }

        if (showTag == 1) {
            courseAdapter04.setShowBuy(true);
        }


        httpRequest.setCancelSign(TAG);
        CallServer.getInstance().postRequest("主题课程详情", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        layoutMain01.setVisibility(View.VISIBLE);
                        layoutMain02.setVisibility(View.VISIBLE);
                        theme_info info = gson.fromJson(response, theme_info.class);
                        if (info.getCode() == 1) {

                            setTitle(info.getData().getHeader().getLevel());

                            tvHeader01.setText( info.getData().getHeader().getLevel2() + "  " + info.getData().getHeader().getLevel3());
//                            tvHeader02.setText(info.getData().getHeader().getLevel2());
//                            tvHeader03.setText(info.getData().getHeader().getLevel3());

                            title.setText(info.getData().getTheme().getTitle());
                            intro.setText(info.getData().getTheme().getIntro());
                            keywords.setText("关键字：" + info.getData().getTheme().getKeywords());
                            ImageLoaderUtils.setImage(info.getData().getTheme().getImage(), ivHead);
                            //主题下面的 课程
                            courseAdapter04.replaceData(info.getData().getInfo());

                            //设置UI
                            setUiByTag(info.getData().getTheme().getType());


                            //vip
//                            if (info.getData().getTheme().getType() == 2) {
//                                courseAdapter04.setType(2);
//                                courseAdapter04.notifyDataSetChanged();
//                                if (showTag == 1) {
//                                    layoutNoVip01.setVisibility(View.GONE);
//                                    layoutNoVip02.setVisibility(View.GONE);
//
//                                    layoutVip01.setVisibility(View.VISIBLE);
//                                    layoutVip02.setVisibility(View.VISIBLE);
//                                    courseAdapter04.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                                            startActivity(new Intent(getContext(), MainActivity.class).putExtra("scheduleId", "-2")
//                                                    .putExtra("courseId", courseAdapter04.getData().get(position).getId()));
//                                        }
//                                    });
//
//                                } else if (showTag == 2) {
//
//
////                                    courseAdapter04.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
////                                        @Override
////                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                                            jumpToActivity(MainActivity.class);
////
////                                        }
////                                    });
//                                } else if (showTag == 3) {
//
//                                }
//
//
//                            } else {
//                                courseAdapter04.setType(1);
//                                courseAdapter04.notifyDataSetChanged();
//                                if (showTag == 1) {
//                                    layoutNoVip01.setVisibility(View.VISIBLE);
//                                    layoutNoVip02.setVisibility(View.VISIBLE);
//
//                                    layoutVip01.setVisibility(View.GONE);
//                                    layoutVip02.setVisibility(View.GONE);
//
//                                } else if (showTag == 2) {
//
//                                } else if (showTag == 3) {
//
//                                }
//                                if (showTag == 2 && scheduleId.equals("-2")) {
//                                    courseAdapter04.setOnItemClickListener(null);
//                                }
//
//
//                            }


                            price.setText("￥ " + info.getData().getTheme().getPrice() + "/全集 ");
                            total.setText("共" + info.getData().getTheme().getTotal() + "节");

                            url = info.getData().getTheme().getConfig();
                            title2 = info.getData().getTheme().getConfig_title();


                        } else {
                            showToast(info.getMsg());
                        }
                        if (courseAdapter04.getData().size() > 0) {
                            setNoDataVisibility(View.GONE);
                        } else {
                            setNoDataVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                        finish();
                    }
                }, getContext());
    }

    /**
     * 设置UI
     *
     * @param vipType 2为vip
     */
    private void setUiByTag(int vipType) {
        //设置UI类型
        //隐藏其他模块
        layoutChooseCourseBar.setVisibility(View.GONE);
        layoutVip02.setVisibility(View.GONE);
        layoutNoVip02.setVisibility(View.GONE);

        layoutVip01.setVisibility(View.GONE);
        layoutNoVip01.setVisibility(View.GONE);


        courseAdapter04.setUiType(showTag, vipType);


        if (showTag == 1) {
            //显示全部课程  即课表选课 走购买流程
            if (vipType == 2) {
                //vip
                LogUtil.i(TAG, "课表选课vip课程");
                //显示购买会员按钮
                layoutVip01.setVisibility(View.VISIBLE);
                layoutVip02.setVisibility(View.VISIBLE);

                //校长点击直接观看ppt
                courseAdapter04.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        //如果是 vip 则直接跳转到ppt播放页面
                        check(courseAdapter04.getData().get(position).getId());
                    }
                });

            } else {
                //普通课程
                LogUtil.i(TAG, "课表选课 普通课程");
                //显示结算菜单
                layoutNoVip02.setVisibility(View.VISIBLE);
                //显示购买全集按钮
                layoutNoVip01.setVisibility(View.VISIBLE);

                //普通课程 点击播放页面跳转到 ppt介绍页面 点击下面部分选择

                courseAdapter04.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                        switch (view.getId()) {
                            case R.id.layoutBo:
                                courseAdapter04.getData().get(position).setChoose(!courseAdapter04.getData().get(position).isChoose());
                                courseAdapter04.notifyItemChanged(position);
                                int size = courseAdapter04.getData().size();
                                boolean hava = false;
                                for (int i = 0; i < size; i++) {
                                    if (!courseAdapter04.getData().get(i).isChoose()) {
                                        isChooseAll = false;
                                        hava = true;
                                        ivChooseAll.setImageResource(R.mipmap.dy_42);
                                        ivChooseAll2.setImageResource(R.mipmap.dy_42);
                                        break;
                                    }
                                }
                                if (!hava) {
                                    isChooseAll = true;
                                    ivChooseAll.setImageResource(R.mipmap.content_14);
                                    ivChooseAll2.setImageResource(R.mipmap.content_14);
                                }
                                if (etInputNum.getText().toString().equals("0") | etInputNum.getText().toString().equals("")) {
                                    etInputNum.setText("1");
                                }
                                calculationMoney();
                                break;
                            case R.id.ivHead:
                                String url = courseAdapter04.getData().get(position).getPreview();
                                if (url.trim().equals("")) {
                                    showToast("该课程暂无预览");
                                    return;
                                }
                                startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", url));
                                break;
                        }
                    }
                });

            }
        } else if (showTag == 2) {
            //已分配的  教师从我的课表中进去的
            if (vipType == 2) {
                //vip
                LogUtil.i(TAG, "我的课表vip课程");
                layoutVip02.setVisibility(View.VISIBLE);
            } else {
                //普通课程
                LogUtil.i(TAG, "我的课表 普通课程");
                layoutVip02.setVisibility(View.VISIBLE);
            }

            //点击item 直接进去ppt课程播放页面
            courseAdapter04.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(getContext(), MainActivity.class).putExtra("scheduleId", scheduleId)
                            .putExtra("courseId", courseAdapter04.getData().get(position).getId()));
                }
            });

        } else if (showTag == 3) {
            //已购购买未分配  校长为老师选课

            //显示添加到课表中按钮
            layoutChooseCourseBar.setVisibility(View.VISIBLE);

            if (vipType == 2) {
                //vip
                LogUtil.i(TAG, "已购购买未分配vip课程");

            } else {
                //普通课程
                LogUtil.i(TAG, "已购购买未分配 普通课程");
            }
//            //点击item 直接进去ppt课程播放页面
            courseAdapter04.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    courseAdapter04.getData().get(position).setChoose(!courseAdapter04.getData().get(position).isChoose());
                    courseAdapter04.notifyItemChanged(position);
                    int size = courseAdapter04.getData().size();
                    boolean hava = false;
                    for (int i = 0; i < size; i++) {
                        if (!courseAdapter04.getData().get(i).isChoose()) {
                            isChooseAll = false;
                            hava = true;
                            ivChooseAll.setImageResource(R.mipmap.dy_42);
                            ivChooseAll2.setImageResource(R.mipmap.dy_42);
                            break;
                        }
                    }
                    //是否全选
                    if (!hava) {
                        isChooseAll = true;
                        ivChooseAll.setImageResource(R.mipmap.content_14);
                        ivChooseAll2.setImageResource(R.mipmap.content_14);
                    }
                }
            });

        } else if (showTag == 4) {
            //已购买  校长已购买的课程
            layoutVip02.setVisibility(View.VISIBLE);
            if (vipType == 2) {
                //vip
                LogUtil.i(TAG, "已购购买未分配vip课程");
                //校长点击直接观看ppt
                courseAdapter04.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        //判断是不是会员
                        check(courseAdapter04.getData().get(position).getId());
                    }
                });
            } else {
                //普通课程
                LogUtil.i(TAG, "已购购买未分配 普通课程");
                courseAdapter04.setOnItemChildClickListener(null);
            }


        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        if (showTag == 3) {
//            //添加到课表中去
//            scheduleId = getIntent().getStringExtra("scheduleId");
//            //显示课表
//            layoutChooseCourseBar.setVisibility(View.VISIBLE);
//
//            layoutNoVip01.setVisibility(View.GONE);
//            layoutNoVip02.setVisibility(View.GONE);
//
//            layoutVip01.setVisibility(View.GONE);
//            layoutVip02.setVisibility(View.GONE);
//        } else if (showTag == 2 | showTag == 4) {
//            //教师查看课程详情
//            scheduleId = getIntent().getStringExtra("scheduleId");
//            layoutChooseCourseBar.setVisibility(View.GONE);
//
//            findViewById(R.id.view02).setVisibility(View.VISIBLE);
//
//            layoutNoVip01.setVisibility(View.GONE);
//            layoutNoVip02.setVisibility(View.GONE);
//
//            layoutVip01.setVisibility(View.GONE);
//            layoutVip02.setVisibility(View.GONE);
//        }
    }

    private void check(int courseId) {
        showLoading();
        //""
        HttpRequest httpRequest = new HttpRequest(AppUrl.check);
        httpRequest.add("courseId", courseId);
        CallServer.getInstance().postRequest("", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        check info = gson.fromJson(response, check.class);
                        if (info.getCode() == CallServer.HTTP_SUCCESS_CODE) {
                            startActivity(
                                    new Intent(getContext(), MainActivity.class)
                                            .putExtra("scheduleId", "-2")
                                            .putExtra("courseId", info.getData()));
                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            builder.setTitle("提示");

                            builder.setMessage("该课程只有会员才能观看,是否购买会员");

                            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivityForResult(new Intent(getContext(), PersonalCenterActivity.class).putExtra("doType", 2), 100);

                                }
                            });
                            builder.setNegativeButton("否", null);
                            builder.create().show();
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_course_theme;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MoneyVipUpdateEvent event) {
        if (event.getVipTime().equals("0")) {
            tvVip.setText("本课程为会员课程，购买会员后可无限制使用");
        } else {
            tvVip.setText("您已是会员,可无限制使用本主题");
        }
    }

    @OnClick({R.id.tvReduce, R.id.tvAdd})
    public void onAddClicked(View view) {
        String input = etInputNum.getText().toString().trim();
        int num = 0;
        if (input.equals("")) {
            num = 0;
        } else {
            num = Integer.parseInt(input);
        }
        switch (view.getId()) {
            case R.id.tvAdd:
                num = num + 1;
                etInputNum.setText(num + "");
                calculationMoney();
                break;
            case R.id.tvReduce:
                num = num - 1;
                if (num < 0) {
                    num = 0;
                }
                etInputNum.setText(num + "");
                calculationMoney();
                break;
        }
    }

    @OnClick(R.id.btShopCar)
    public void onViewClicked() {
        jumpToActivity(ShoppingCartActivity.class);
    }

    @OnClick({R.id.layoutChooseAll2, R.id.btBuy, R.id.btBuyVip, R.id.layoutChooseAll, R.id.btAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutChooseAll:
            case R.id.layoutChooseAll2:
                isChooseAll = !isChooseAll;
                int size = courseAdapter04.getData().size();
                for (int i = 0; i < size; i++) {
                    courseAdapter04.getData().get(i).setChoose(isChooseAll);
                }
                courseAdapter04.notifyDataSetChanged();
                if (isChooseAll) {
                    ivChooseAll.setImageResource(R.mipmap.content_14);
                    ivChooseAll2.setImageResource(R.mipmap.content_14);
                } else {
                    ivChooseAll.setImageResource(R.mipmap.dy_42);
                    ivChooseAll2.setImageResource(R.mipmap.dy_42);
                }
                calculationMoney();

                break;

            case R.id.btBuy:
                //立即结算
                String id = "";
                for (int i = 0; i < courseAdapter04.getData().size(); i++) {
                    if (courseAdapter04.getData().get(i).isChoose()) {
                        id = id + courseAdapter04.getData().get(i).getId() + ",";
                    }
                }
                if (id.equals("")) {
                    showToast("选择课程");
                    return;
                }
                String num = etInputNum.getText().toString().trim();
                if (num.equals("") | num.equals("0")) {
                    showToast("请输入购买数量");
                    return;
                }

                shopcarAdd(2, id, num);
                break;
            case R.id.btAdd:
                //添加到课表中去

                String id2 = "";
                for (int i = 0; i < courseAdapter04.getData().size(); i++) {
                    if (courseAdapter04.getData().get(i).isChoose()) {
                        id2 = id2 + courseAdapter04.getData().get(i).getId() + ",";
                    }
                }
                if (id2.equals("")) {
                    showToast("选择课程");
                    return;
                }

                schedule_update(id2);


                break;
            case R.id.btBuyVip:
                //Vip购买
                startActivityForResult(new Intent(getContext(), PersonalCenterActivity.class).putExtra("doType", 2), 100);
                break;
        }
    }

    private void schedule_update(String courseId) {

        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.schedule_update);//"修改课表的内容"
        httpRequest.add("scheduleId", scheduleId);
        httpRequest.add("themeId", theme.getId());
        httpRequest.add("courseId", courseId);
        CallServer.getInstance().postRequest("修改课表的内容", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        schedule_update info = gson.fromJson(response, schedule_update.class);
                        if (info.getCode() == 1) {
                            setResult(10086);
                            showToast(info.getMsg());
                            finish();
                        } else {
                            showToast(info.getMsg());
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        closeLoading();
                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());

    }

}
