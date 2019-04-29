package com.hhkj.dyedu.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hhkj.dyedu.AppUrl;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.SmallPptAdapter;
import com.hhkj.dyedu.bean.gson.BaseHttpResponse;
import com.hhkj.dyedu.bean.gson.test1;
import com.hhkj.dyedu.bean.model.ClassTip;
import com.hhkj.dyedu.bean.model.Question;
import com.hhkj.dyedu.bean.model.SmallPpt;
import com.hhkj.dyedu.bean.model.Wars;
import com.hhkj.dyedu.cache.CacheUtils;
import com.hhkj.dyedu.callback.NarrowListener;
import com.hhkj.dyedu.callback.OnDirectionListener;
import com.hhkj.dyedu.callback.PptPageChangeListener;
import com.hhkj.dyedu.callback.SetTimeListener;
import com.hhkj.dyedu.http.CallServer;
import com.hhkj.dyedu.http.HttpRequest;
import com.hhkj.dyedu.http.HttpResponseListener;
import com.hhkj.dyedu.ui.activity.base.BaseActivity;
import com.hhkj.dyedu.ui.activity.user.PersonalCenterActivity;
import com.hhkj.dyedu.ui.utils.StartClassTools;
import com.hhkj.dyedu.utils.FileUtils;
import com.hhkj.dyedu.utils.ImageLoaderUtils;
import com.hhkj.dyedu.utils.LogUtil;
import com.hhkj.dyedu.view.CustomRemindDialog;
import com.hhkj.dyedu.view.DisRemindDialog;
import com.hhkj.dyedu.view.DownLoadDialog;
import com.hhkj.dyedu.view.FileDialog;
import com.hhkj.dyedu.view.MagnifierPop;
import com.hhkj.dyedu.view.MusicPop;
import com.hhkj.dyedu.view.PictureDialog;
import com.hhkj.dyedu.view.PptWebView;
import com.hhkj.dyedu.view.ScaleLinearLayout;
import com.hhkj.dyedu.view.SecRemindDialog;
import com.hhkj.dyedu.view.SetCountTimeDialog;
import com.hhkj.dyedu.view.SpotlightPop;
import com.hhkj.dyedu.view.SurfacePreView;
import com.hhkj.dyedu.view.TipPop;
import com.hhkj.dyedu.view.ToolsPop01;
import com.hhkj.dyedu.view.ToolsPop02;
import com.hhkj.dyedu.view.ToolsPop04;
import com.hhkj.dyedu.view.ToolsPop05;
import com.hhkj.dyedu.view.floatball.Service.StartFloatBallService;
import com.hhkj.dyedu.view.popup.AlarmPop;
import com.hhkj.dyedu.view.popup.BlackboardPop;
import com.hhkj.dyedu.view.popup.CompetitionPop;
import com.hhkj.dyedu.view.popup.GifPop;
import com.hhkj.dyedu.view.popup.QuestionPop;
import com.hhkj.dyedu.view.popup.WebViewPop;
import com.hpplay.callback.TransportCallBack;
import com.hpplay.link.HpplayLinkControl;
import com.joooonho.SelectableRoundedImageView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zuoni.common.dialog.choice.BottomGetPhotoDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {


    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final String mKey = "305a5dc575aa45f66d7bdf0761280f08";
    @BindView(R.id.mainView)
    FrameLayout mainView;
    @BindView(R.id.webView)
    PptWebView webView;
    @BindView(R.id.layoutMenuRight)
    RelativeLayout layoutMenuRight;
    @BindView(R.id.viewMenuRightControl)
    View viewMenuRightControl;
    @BindView(R.id.ivRightMenu01)
    ImageView ivRightMenu01;
    @BindView(R.id.ivRightMenu02)
    ImageView ivRightMenu02;
    @BindView(R.id.ivRightMenu03)
    ImageView ivRightMenu03;
    @BindView(R.id.ivRightMenu04)
    ImageView ivRightMenu04;
    @BindView(R.id.ivRightMenu05)
    ImageView ivRightMenu05;
    @BindView(R.id.ivStartClass)
    ImageView ivStartClass;
    //    @BindView(R.id.ivBottomBar01)
//    ImageView ivBottomBar01;
    PictureDialog.Builder builder;
    @BindView(R.id.ivPageLeft)
    ImageView ivPageLeft;
    @BindView(R.id.ivPageRight)
    ImageView ivPageRight;
    @BindView(R.id.layoutMenuBottomBar)
    LinearLayout layoutMenuBottomBar;
    @BindView(R.id.rvPpt)
    RecyclerView rvPpt;
    @BindView(R.id.ivHead)
    SelectableRoundedImageView ivHead;
    @BindView(R.id.tvCountDownTime)
    TextView tvCountDownTime;
    @BindView(R.id.layoutCountDownTime)
    LinearLayout layoutCountDownTime;
    @BindView(R.id.viewMenuRightControl2)
    RelativeLayout viewMenuRightControl2;
    @BindView(R.id.layoutBigRightMenu01)
    ScaleLinearLayout layoutBigRightMenu01;
    @BindView(R.id.layoutBigRightMenu02)
    ScaleLinearLayout layoutBigRightMenu02;
    @BindView(R.id.layoutBigRightMenu04)
    ScaleLinearLayout layoutBigRightMenu04;
    @BindView(R.id.layoutBigRightMenu06)
    ScaleLinearLayout layoutBigRightMenu06;
    @BindView(R.id.layoutBigRightMenu07)
    ScaleLinearLayout layoutBigRightMenu07;
    @BindView(R.id.ivSmallTools01Close)
    ImageView ivSmallTools01Close;
    @BindView(R.id.layoutSmallTools01)
    LinearLayout layoutSmallTools01;
    @BindView(R.id.tvTimeBar)
    TextView tvTimeBar;
    @BindView(R.id.layoutTimeBar)
    RelativeLayout layoutTimeBar;
    @BindView(R.id.ivBottomBar02)
    ImageView ivBottomBar02;
    @BindView(R.id.tvBottomBar03)
    TextView tvBottomBar03;
//    @BindView(R.id.ivBottomBar04)
//    ScaleImageView ivBottomBar04;
//    @BindView(R.id.viewProgress)
//    View viewProgress;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.pppp)
    FrameLayout pppp;
    @BindView(R.id.layoutSmallTools03)
    LinearLayout layoutSmallTools03;
    @BindView(R.id.layoutSmallTools04)
    LinearLayout layoutSmallTools04;
    @BindView(R.id.layoutSmallTools05)
    LinearLayout layoutSmallTools05;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ivStar01)
    ImageView ivStar01;
    @BindView(R.id.ivStar02)
    ImageView ivStar02;
    @BindView(R.id.ivStar03)
    ImageView ivStar03;
    @BindView(R.id.ivStar04)
    ImageView ivStar04;
    @BindView(R.id.ivStar05)
    ImageView ivStar05;
    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.ivNarrowIcon01)
    ImageView ivNarrowIcon01;
    @BindView(R.id.ivNarrowIcon02)
    ImageView ivNarrowIcon02;
    @BindView(R.id.ivNarrowIcon03)
    ImageView ivNarrowIcon03;
    @BindView(R.id.ivNarrowIcon04)
    ImageView ivNarrowIcon04;
    @BindView(R.id.ivBottomBar03)
    ImageView ivBottomBar03;
    @BindView(R.id.ivBottomBar05)
    ImageView ivBottomBar05;
    @BindView(R.id.ivBottomBar06)
    ImageView ivBottomBar06;
    @BindView(R.id.ivPL)
    ImageView ivPL;
    @BindView(R.id.ivPR)
    ImageView ivPR;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.circle01)
    RoundTextView circle01;
    @BindView(R.id.circle02)
    RoundTextView circle02;

    @BindView(R.id.circle03)
    RoundTextView circle03;
    @BindView(R.id.circle04)
    RoundTextView circle04;
    @BindView(R.id.circle05)
    RoundTextView circle05;

    //    @BindView(R.id.bt_file)
//    Button bt_file;
    @BindView(R.id.tv_onePage)
    TextView tvOnePage;
    @BindView(R.id.tv_twoPage)
    TextView tvTwoPage;
    @BindView(R.id.li_page)
    LinearLayout liPage;
    @BindView(R.id.bt_file)
    LinearLayout bt_file;
    @BindView(R.id.bt_file2)
    LinearLayout bt_file2;
    @BindView(R.id.tvCountDownTime2)
    TextView tvCountDownTime2;
    @BindView(R.id.layoutCountDownTime2)
    LinearLayout layoutCountDownTime2;


    /**
     * 黑板弹窗
     */
    private BlackboardPop blackboardPop;
    /**
     * 画板
     */
    private BlackboardPop blackboardPop2;


    private boolean isSmall = true;
    private boolean menuRightIsClose = false;
    private boolean isShowBoardView = true;//是否显示画板
    private boolean isShowBlackboard = true;
    private Camera takePicture;
    private SurfacePreView surfacePreView;
    private FrameLayout mFrameLayout;
    private SmallPptAdapter smallPptAdapter;
    private String headUrl = "";
    /**
     * 上课时长倒计时
     */
    private CountDownTimer countDownTimer;
    private SetCountTimeDialog setCountTimeDialog;
    private int lastTime;
    private StartClassTools startClassTools;
    private int pptAllNums;
    private boolean isShowPrompt = true;
    private boolean isCastScreen = false;
    private int allTime = 0;
    private ToolsPop01 toolsPop01;
    private ToolsPop02 toolsPop02;
    private ToolsPop04 toolsPop04;
    private ToolsPop05 toolsPop05;
    private HpplayLinkControl mHpplayLinkControl;
    private long mPrevCastStopTimeStamp = 0;


    /**
     * 投屏反馈
     */
    private TransportCallBack transportCallBack = new TransportCallBack() {
        @Override
        public void onTransportData(Object o) {

        }
    };
    private int courseId;
    private String scheduleId = "-2";

    private boolean isFinish = false;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private BottomGetPhotoDialog bottomGetPhotoDialog;
    private boolean isInit = false;
    private Map<String, Object> map;
    private AlarmPop alarmPop;
    private CompetitionPop competitionPop;
    private QuestionPop questionPop;
    private GifPop gifPop;
    private TipPop tipPop, tipPop2, tipPop3;
    private MusicPop musicPop;
    private GifPop encouragePop01, encouragePop02, encouragePop03;
    private List<Question> questions;
    private SpotlightPop spotlightPop;
    private MagnifierPop magnifierPop;


    /**
     * 上课提示
     */
    private List<ClassTip> classTips;

    /**
     * 课堂提示框
     */

    private List<Wars> warsList;

    /**
     * 搭建图纸弹窗
     */
    private WebViewPop buildPicPop;

    /**
     * 缩小监听
     */
    private NarrowListener narrowListener;
    private boolean isShowNarrowIcon01 = false, isShowNarrowIcon02 = false, isShowNarrowIcon03 = false, isShowNarrowIcon04 = false;


    private android.support.v7.app.AlertDialog pptFinishDialog;
    /**
     * 获取提示
     *
     * @param pos 位置
     */
    private ClassTip classTip;

    private int listP = 0;
    private boolean isShowText = false;
    private Context context;

    private String tiptop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //获取邮箱
        String a = CacheUtils.getCacheEmail();


        takePhoto = getTakePhoto();

        classTips = new ArrayList<>();

        warsList = new ArrayList<>();
        narrowListener = new NarrowListener() {
            @Override
            public void onNarrowListener(int narrowPop) {
                if (narrowPop == NARROW_POP_TYPE_03) {
                    //黑板
                    isShowNarrowIcon03 = true;
                } else if (narrowPop == NARROW_POP_TYPE_04) {
                    //画笔
                    isShowNarrowIcon04 = true;
                } else if (narrowPop == NARROW_POP_TYPE_02) {
                    //竞赛
                    isShowNarrowIcon02 = true;
                } else if (narrowPop == NARROW_POP_TYPE_01) {
                    //提问环节
                    isShowNarrowIcon01 = true;
                }
                setNarrowIconUi();
            }
        };

        courseId = getIntent().getIntExtra("courseId", -1);
        scheduleId = getIntent().getStringExtra("scheduleId");
        if (scheduleId == null) {
            scheduleId = "-2";
        }
        startClassTools = new StartClassTools(getContext());

        setSmallWebView();

        mFrameLayout = (FrameLayout) findViewById(R.id.framelayout);

        webView.setPptPageChangeListener(new PptPageChangeListener() {
            @Override
            public void onPptPageChange(int position, String note) {
                LogUtil.i("ppt当前第" + position + "页");
                tvOnePage.setText(position + "");
                tvTwoPage.setText(pptAllNums + "");
//                tvBottomBar03.setText(note);
                if (position < 0) {
                    position = 1;
                }
                //设置列表进度
                if (!isSmall) {
                    if (position - 1 == smallPptAdapter.getNowChoosePosition() && position - 1 == smallPptAdapter.getData().size() - 1) {
                        //上次的页数等于当前 页数 且等于最大页数 那么就是最后一页了

                        if (pptFinishDialog == null) {

                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());

                            builder.setTitle("提示");

                            builder.setMessage("该PPT已全部播放");

                            builder.setCancelable(false);

                            builder.setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setSmallWebView();
                                    dialog.dismiss();
                                }
                            });
                            pptFinishDialog = builder.create();
                        }

                        if (!pptFinishDialog.isShowing()) {
                            pptFinishDialog.show();
                        }


                    }
                }


                if (smallPptAdapter.getData().size() != 0) {
                    smallPptAdapter.setChoosePosition(position - 1);
                    rvPpt.smoothScrollToPosition(position - 1);
                }
                getClassTips(position - 1);

                listP = position - 1;
                //设置ppt进度条
                // startClassTools.setTimeProgress(position, pptAllNums, viewProgress, tvProgress, pppp);
                if (!isSmall) {
                    if (isFinish) {
                        return;
                    }
                    if (smallPptAdapter.getData().size() == 0) {
                        return;
                    }

                    smallPptAdapter.getData().get(position - 1).setLook(true);
                    int size = smallPptAdapter.getData().size();
                    int look = 0;
                    for (int i = 0; i < size; i++) {

                        if (smallPptAdapter.getData().get(i).isLook()) {
                            look++;
                        }
                    }
                    if (look * 1.000 / size >= 0.8) {
                        finishppt();
                    }
                }
            }
        });


        webView.setOnDirectionListener(new OnDirectionListener() {
            @Override
            public void direction(int direction) {

                if (isSmall) {
                    return;
                }


                if (direction == LEFT) {
                    //打开
                    if (menuRightIsClose) {
                        setMenuRight();
                    }

                } else if (direction == RIGHT) {
                    //关闭
                    if (!menuRightIsClose) {
                        setMenuRight();
                    }
                }
            }
        });

        webView.init();
        initLeftSmallPptList();
        mHpplayLinkControl = HpplayLinkControl.getInstance();
        mHpplayLinkControl.setDebug(true);
        mHpplayLinkControl.initHpplayLink(this, mKey);
//        initView();
        mHpplayLinkControl.setTransportCallBack(transportCallBack);


        getUrl();

    }

    @Override
    protected void onResume() {
        super.onResume();

        String headUrl2 = CacheUtils.getHeadImage();
        if (headUrl2 != null) {
            if (!headUrl2.equals(headUrl)) {
                headUrl = headUrl2;
                ImageLoaderUtils.setHeadImage(headUrl2, ivHead);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        mHpplayLinkControl.castDisconnectDevice();
        //销毁所有弹窗
        if (musicPop != null) {
            musicPop.onDestroy();
            musicPop = null;
        }
        if (alarmPop != null) {
            alarmPop.onDestroy();
            alarmPop = null;
        }
        if (competitionPop != null) {
            competitionPop.onDestroy();
            competitionPop = null;
        }
        webView.destroy();
        super.onDestroy();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }

        //配置压缩属性
//                CompressConfig config;
//                LubanOptions option = new LubanOptions.Builder().create();
//                config = CompressConfig.ofLuban(option);
//                config.enableReserveRaw(false);//压缩过后是否保存原图
//                takePhoto.onEnableCompress(config, false);
//
        return takePhoto;
    }

    /**
     * 设置虽小按钮图标
     */
    private void setNarrowIconUi() {
        setVisibility(isShowNarrowIcon01, ivNarrowIcon01);
        setVisibility(isShowNarrowIcon02, ivNarrowIcon02);
        setVisibility(isShowNarrowIcon03, ivNarrowIcon03);
        setVisibility(isShowNarrowIcon04, ivNarrowIcon04);
    }

    /**
     * 小屏webView
     */
    private void setSmallWebView() {

//        ivPL.setVisibility(View.GONE);
//        ivPR.setVisibility(View.GONE);
        liPage.setVisibility(View.GONE);

        initBigRightMenu();

        layoutTimeBar.setVisibility(View.GONE);
        //控制画板隐藏
        isShowBoardView = true;
        boardViewControl();
        //隐藏右边菜单栏
        layoutMenuRight.setVisibility(View.GONE);


        viewMenuRightControl2.setVisibility(View.GONE);

        startClassTools.dismissAllToolsPops();


        //隐藏工具栏按钮


        //隐藏黑板
        isShowBlackboard = true;
        blackboardControl();


        isSmall = true;

        startClassTools.setSmallWebView(webView);

        if (toolsPop02 != null) {
            toolsPop02.clickMenu(-1);
        }

//        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) webView.getLayoutParams();
//
////        layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
////        layoutParams.width = webViewWidth02;
//
//
//        layoutParams.setMargins(smallMarginLeft, smallMarginTop, smallMarginRight, smallMarginBottom);
//
//        webView.setLayoutParams(layoutParams);
    }

    private int nnnn;

    private void getClassTips(int pos) {
        nnnn = pos;
        ivBottomBar03.setVisibility(View.GONE);
        ivBottomBar05.setVisibility(View.GONE);
        tvBottomBar03.setVisibility(View.GONE);
        ivBottomBar02.setVisibility(View.GONE);
        ivBottomBar06.setVisibility(View.GONE);
        isShowText = false;
        classTip = null;
        for (int i = 0; i < classTips.size(); i++) {
            if (classTips.get(i).getPage().equals(String.valueOf((pos + 1)))) {
                classTip = classTips.get(i);
                if (!classTip.getImage().equals("")) {
                    ivBottomBar03.setVisibility(View.VISIBLE);
                }
//                if (!classTip.getPage().equals("")) {
//                    ivBottomBar02.setVisibility(View.VISIBLE);
//                }
                if (!classTip.getVideo().equals("")) {
                    ivBottomBar05.setVisibility(View.VISIBLE);
                }
                if (!classTip.getText().equals("")) {
                    ivBottomBar02.setVisibility(View.VISIBLE);
                }
                tvBottomBar03.setText(classTip.getText());
                break;
            }
        }


        //判断课堂提醒弹窗
        circle05.setVisibility(View.GONE);
        circle04.setVisibility(View.GONE);
        //circle03.setVisibility(View.GONE);


        if (toolsPop04 != null) {
            toolsPop04.showCircle01(false);
            toolsPop04.showCircle02(false);
            toolsPop04.showCircle03(false);
        }


        for (int i = 0; i < warsList.size(); i++) {
            if (warsList.get(i).getPage().equals(String.valueOf((pos + 1)))) {
                Wars wars = warsList.get(i);
                if (!wars.getDis().equals("")) {
                    circle03.setVisibility(View.VISIBLE);
                } else {
                    circle03.setVisibility(View.GONE);
                }
                if (!wars.getSec().equals("")) {
                    circle04.setVisibility(View.VISIBLE);
                } else {
                    circle04.setVisibility(View.GONE);
                }
                if (!wars.getCus().equals("")) {
                    circle05.setVisibility(View.VISIBLE);
                } else {
                    circle05.setVisibility(View.GONE);
                }

                if (toolsPop04 != null) {
                    if (!wars.getDis().equals("")) {
                        toolsPop04.showCircle01(true);
                    } else {
                        toolsPop04.showCircle01(false);
                    }
                    if (!wars.getSec().equals("")) {
                        toolsPop04.showCircle02(true);
                    } else {
                        toolsPop04.showCircle02(false);
                    }
                    if (!wars.getCus().equals("")) {
                        toolsPop04.showCircle03(true);
                    } else {
                        toolsPop04.showCircle03(false);
                    }
                }

                break;
            }
        }


    }

    private void finishppt() {
//        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.finish);//"观看结束"
        if (!scheduleId.equals("-2")) {
            httpRequest.add("scheduleId", scheduleId);
        }
        httpRequest.add("courseId", courseId);
        CallServer.getInstance().postRequest("观看结束", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                        if (info.getCode() == 1) {
                            isFinish = true;
                        } else {
                        }
                    }

                    @Override
                    public void onFailed(Exception exception) {
//                        closeLoading();
//                        showToast(getString(R.string.toast_server_error));
                    }
                }, getContext());
    }

    private void setMenuRight() {

        if (layoutMenuRight.getVisibility() == View.GONE) {
            layoutMenuRight.setVisibility(View.VISIBLE);
        }


        if (menuRightIsClose) {
//            if (preView.isOpen()) {
//                preView.controlZoomView();
//            }
            //打开
            menuRightIsClose = false;
            viewMenuRightControl2.setVisibility(View.GONE);

            startClassTools.openTools(layoutMenuRight, startClassTools.getBigRightMenuWidth());

//            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layoutMenuRight, "translationX", menuRightWidth01 - menuRightWidth02, 0);
//            objectAnimator.setDuration(300);
//            objectAnimator.start();
        } else {
            //关闭
            menuRightIsClose = true;
            viewMenuRightControl2.setVisibility(View.VISIBLE);

            startClassTools.dismissAllToolsPops();
            startClassTools.closeTools2(layoutMenuRight, startClassTools.getBigRightMenuWidth(), viewMenuRightControl2);

//            viewMenuRightControl2.setVisibility(View.VISIBLE);
//            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layoutMenuRight, "translationX", 0, menuRightWidth01 - menuRightWidth02);
//            objectAnimator.setDuration(300);
//            objectAnimator.start();
        }
    }

    /**
     *
     */
    private void initLeftSmallPptList() {

        rvPpt.setLayoutManager(new LinearLayoutManager(getContext()));

        smallPptAdapter = new SmallPptAdapter();

        smallPptAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ivPpt:
                        webView.gosld(position);
                        break;
                }
            }
        });
        rvPpt.setAdapter(smallPptAdapter);
        SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.TOP);
        snapHelperTop.attachToRecyclerView(rvPpt);
    }

    String plain = "";


    private test1 pptAllInfo;

    private String pdf_3d;

    private void getUrl() {
        showLoading();
        HttpRequest httpRequest = new HttpRequest(AppUrl.getPpt);//"获取详情"
        if (!scheduleId.equals("-2")) {
            httpRequest.add("scheduleId", scheduleId);
        }
        httpRequest.add("courseId", courseId);
//        httpRequest.add("courseId", "110");
        CallServer.getInstance().postRequest("获取详情", httpRequest,
                new HttpResponseListener() {
                    @Override
                    public void onSucceed(String response, Gson gson) {
                        closeLoading();
                        isInit = false;
                        test1 info = gson.fromJson(response, test1.class);
                        pptAllInfo = info;

                        if (info.getData().getAttachment().equals("")){
                            bt_file2.setVisibility(View.VISIBLE);
                            bt_file2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showToast("此课程暂时没有附件");
                                }
                            });
                        }else {
                            bt_file.setVisibility(View.VISIBLE);
                        }

                        if (info.getCode() == 1) {
                            webView.loadUrl(info.getData().getUrl());
                            pdf_3d = info.getData().getPdf_3d();
                            List<SmallPpt> list = new ArrayList<>();

                            pptAllNums = info.getData().getFilenum();
                            //  startClassTools.setTimeProgress(1, pptAllNums, viewProgress, tvProgress, pppp);

                            for (int i = 0; i < pptAllNums; i++) {
                                list.add(new SmallPpt());
                            }

                            smallPptAdapter.setUrl(info.getData().getPrefix());
                            smallPptAdapter.setUpdatetime(info.getData().getUpdatetime());

                            smallPptAdapter.replaceData(list);

                            smallPptAdapter.setChoosePosition(0);


                            title.setText(info.getData().getTitle());
                            setStarNum(info.getData().getStar(), ivStar01, ivStar02, ivStar03, ivStar04, ivStar05);
//                            webView.setWebChromeClient(new WebChromeClient() {
//                                @Override
//                                public void onProgressChanged(WebView view, int newProgress) {
//                                    super.onProgressChanged(view, newProgress);
//                                    LogUtil.i("加载进度" + newProgress);
//                                    if (newProgress == 100) {
//                                        if (!isInit) {
//                                            isInit = true;
//                                            webView.postDelayed(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    webView.gosld(0);//加载第0
//                                                }
//                                            },200);
//                                        }
//                                    }
//                                }
//                            });
//                            Map<String, String> mapData = gson.fromJson(json.toString(), new TypeToken<Map<String, String>>(){}.getType());
                            classTips = new Gson().fromJson(info.getData().getTips(), new TypeToken<List<ClassTip>>() {

                            }.getType());
                            warsList = new Gson().fromJson(info.getData().getWars(), new TypeToken<List<Wars>>() {
                            }.getType());
                            getClassTips(0);

                            questions = info.getData().getQuiz();
                            initBuildPicPop(info.getData().getDrawing());

                            plain = info.getData().getPlan();
                            findViewById(R.id.iv100).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (plain.equals("")) {
                                        showToast("暂无教案");
                                    } else {
                                        startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", plain).putExtra("title", ""));
                                    }
                                }
                            });

                            //判断是否有图纸
                            if (info.getData().getDrawing().trim().equals("")) {
                                //没有图纸
                                circle01.setVisibility(View.INVISIBLE);
                            } else {
                                circle01.setVisibility(View.VISIBLE);
                            }

                            if (info.getData().getQuiz().size() == 0) {
                                //没有提问环节
                                circle02.setVisibility(View.INVISIBLE);
                            } else {
                                circle02.setVisibility(View.VISIBLE);
                            }

                        } else {
                            showToast(info.getMsg());
                            finish();
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

    private void setVisibility(boolean isShow, View view) {
        if (isShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void initBigRightMenu() {
//        layoutBigRightMenu01.setBackgroundColor(Color.parseColor("#00000000"));
//        layoutBigRightMenu02.setBackgroundColor(Color.parseColor("#00000000"));
//        layoutBigRightMenu04.setBackgroundColor(Color.parseColor("#00000000"));
//        layoutBigRightMenu06.setBackgroundColor(Color.parseColor("#00000000"));
//        layoutBigRightMenu07.setBackgroundColor(Color.parseColor("#00000000"));


        layoutBigRightMenu01.setChoose(false);
        layoutBigRightMenu02.setChoose(false);
        layoutBigRightMenu04.setChoose(false);
        layoutBigRightMenu06.setChoose(false);
        layoutBigRightMenu07.setChoose(false);
    }

    private void boardViewControl() {

        if (isShowBoardView) {
            isShowBoardView = false;
            //关闭画板
        } else {
            isShowBoardView = true;
        }
    }

    private void blackboardControl() {
        if (isShowBlackboard) {
            isShowBlackboard = false;
            //关闭画板
        } else {
            isShowBlackboard = true;
        }
    }

    private void setStarNum(int star, ImageView iv01, ImageView iv02, ImageView iv03, ImageView iv04, ImageView iv05) {

        switch (star) {
            default:
            case 1:
                iv01.setVisibility(View.VISIBLE);
                iv02.setVisibility(View.GONE);
                iv03.setVisibility(View.GONE);
                iv04.setVisibility(View.GONE);
                iv05.setVisibility(View.GONE);
                break;

            case 2:
                iv01.setVisibility(View.VISIBLE);
                iv02.setVisibility(View.VISIBLE);
                iv03.setVisibility(View.GONE);
                iv04.setVisibility(View.GONE);
                iv05.setVisibility(View.GONE);
                break;
            case 3:
                iv01.setVisibility(View.VISIBLE);
                iv02.setVisibility(View.VISIBLE);
                iv03.setVisibility(View.VISIBLE);
                iv04.setVisibility(View.GONE);
                iv05.setVisibility(View.GONE);
                break;
            case 4:
                iv01.setVisibility(View.VISIBLE);
                iv02.setVisibility(View.VISIBLE);
                iv03.setVisibility(View.VISIBLE);
                iv04.setVisibility(View.VISIBLE);
                iv05.setVisibility(View.GONE);
                break;
            case 5:
                iv01.setVisibility(View.VISIBLE);
                iv02.setVisibility(View.VISIBLE);
                iv03.setVisibility(View.VISIBLE);
                iv04.setVisibility(View.VISIBLE);
                iv05.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 初始化搭建图纸弹窗
     *
     * @param drawing 图纸url
     */
    private void initBuildPicPop(final String drawing) {
        buildPicPop = new WebViewPop(getContext(), drawing);
    }

    // if((0!=mPrevCastStopTimeStamp)&&(System.currentTimeMillis()-mPrevCastStopTimeStamp)<3000)
//    {
//        Toast.makeText(HpplayUISDKActivity.this,"投屏点击太频繁,请稍后重试",Toast.LENGTH_SHORT).show();
//        return;
//    }
//    mPrevCastStopTimeStamp=System.currentTimeMillis();
//
//                mHpplayLinkControl.showHpplayWindow(HpplayUISDKActivity.this, null);
    @OnClick(R.id.layoutMenuRight)
    public void onViewClicked() {

    }

    /**
     * 右侧菜单打开关闭
     *
     * @param view
     */
    @OnClick({R.id.viewMenuRightControl, R.id.viewMenuRightControl2})
    public void menuRightControl(View view) {
        switch (view.getId()) {
            case R.id.viewMenuRightControl:
                menuRightIsClose = false;
                break;
            case R.id.viewMenuRightControl2:
                menuRightIsClose = true;
                break;
        }
        setMenuRight();
    }

    @OnClick({R.id.ivRightMenu01, R.id.ivRightMenu02, R.id.ivRightMenu03, R.id.ivRightMenu04,
            R.id.ivRightMenu05, R.id.ivStartClass, R.id.tvStartClass})
    public void onRightMenuSmall(View view) {
        switch (view.getId()) {
            case R.id.ivRightMenu01:
                if (pdf_3d != null) {
                    circle03.setVisibility(View.VISIBLE);
                } else {
                    circle03.setVisibility(View.INVISIBLE);
                }
                //学科工具
                startClassTools.openTools(layoutSmallTools01, startClassTools.getSmallRightWidth());
                break;
            case R.id.ivRightMenu02:
                //常用工具
                startClassTools.openTools(layoutSmallTools05, startClassTools.getSmallRightWidth());
                break;
            case R.id.ivRightMenu03:
                //提醒
                startClassTools.openTools(layoutSmallTools03, startClassTools.getSmallRightWidth());
                break;
            case R.id.ivRightMenu04:
                //表扬鼓励
                startClassTools.openTools(layoutSmallTools04, startClassTools.getSmallRightWidth());

                break;
            case R.id.ivRightMenu05:
                break;
            case R.id.tvStartClass:
            case R.id.ivStartClass:
                //开始上课
                LogUtil.i("开始上课");
//                preView.controlZoomView();
                if (isSmall) {
                    //开始上课
                    setBigWebView();

                } else {
                    webView.gosld(smallPptAdapter.getNowChoosePosition());
                    setSmallWebView();
                }

                break;

        }
    }

    /**
     * 大屏webView
     */
    private void setBigWebView() {

//        ivPL.setVisibility(View.VISIBLE);
//        ivPR.setVisibility(View.VISIBLE);
        liPage.setVisibility(View.VISIBLE);

        tvOnePage.setText(smallPptAdapter.getNowChoosePosition() + 1 + "");
        tvTwoPage.setText(pptAllNums + "");

        isSmall = false;

        layoutTimeBar.setVisibility(View.VISIBLE);

//        if(menuRightIsClose){
//            layoutMenuRight.setVisibility(View.GONE);
//            viewMenuRightControl2.setVisibility(View.VISIBLE);
//        }else {
//            viewMenuRightControl2.setVisibility(View.GONE);
//            menuRightIsClose=true;
        menuRightIsClose = !menuRightIsClose;
        setMenuRight();
//        }

        startClassTools.setBigWebView(webView);


//        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) webView.getLayoutParams();
//
////        layoutParams.height = webViewHeight01;
////        layoutParams.width = webViewWidth01;
//        layoutParams.setMargins(bigMarginLeft, bigMarginTop, bigMarginRight, bigMarginBottom);
//        webView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        Log.e("---------", "----onActivityResult-------");
        mHpplayLinkControl.castStartMirrorResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @OnClick({R.id.ivBottomBar02, R.id.ivBottomBar06})
    public void onBottomBar(View view) {
        switch (view.getId()) {
//            case R.id.ivBottomBar01:
//                //拍照
//                LogUtil.i("拍照");
////                takePhotoBackground();
//
//                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
//                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//                Uri imageUri = Uri.fromFile(file);
//                takePhoto.onPickFromCapture(imageUri);
//                break;
            case R.id.ivBottomBar06:
            case R.id.ivBottomBar02:
                HttpRequest httpRequest = new HttpRequest(AppUrl.getPpt);
                if (!scheduleId.equals("-2")) {
                    httpRequest.add("scheduleId", scheduleId);
                }
                httpRequest.add("courseId", courseId);
                CallServer.getInstance().postRequest("文字提示", httpRequest,
                        new HttpResponseListener() {
                            @Override
                            public void onSucceed(String response, Gson gson) {
                                closeLoading();

                                FileDialog fileDialog = new FileDialog(getContext());
                                fileDialog.setFiletip(classTips.get(0).getText());

                                fileDialog.setClicklistener(new FileDialog.ClickListenerInterface() {
                                    @Override
                                    public void isCancel() {
                                        fileDialog.dismiss();
                                    }
                                });
                                fileDialog.show();
                            }

                            @Override
                            public void onFailed(Exception exception) {

                            }
                        }, getContext());

//                isShowText = !isShowText;
//                if (isShowText) {
//                    ivBottomBar02.setVisibility(View.VISIBLE);
//                    ivBottomBar06.setVisibility(View.GONE);
//                    tvBottomBar03.setVisibility(View.VISIBLE);
//                } else {
//                    ivBottomBar02.setVisibility(View.GONE);
//                    ivBottomBar06.setVisibility(View.VISIBLE);
//                    tvBottomBar03.setVisibility(View.VISIBLE);
//                }

                break;
//            case R.id.ivBottomBar04:
//                isCastScreen = !isCastScreen;
//                if (!isCastScreen) {
//                    //关闭
//                    mHpplayLinkControl.castStopMirror();
//                    ivBottomBar04.setImageResource(R.mipmap.broadcast_2);
//
//                } else {
//
//                    //upnp投屏操作
//                    mHpplayLinkControl.showHpplayWindow(this, new HpplayWindowPlayCallBack() {
//                        @Override
//                        public void onHpplayWindowDismiss() {
////                        P.c("隐藏");
//                        }
//
//                        @Override
//                        public void onIsConnect(boolean b) {
//
//                        }
//
//                        @Override
//                        public void onIsPlaySuccess(boolean b) {
////                        P.c("投屏成功");
//                            mHpplayLinkControl.dismissHpplayWindow();
//                            ivBottomBar04.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ivBottomBar04.setImageResource(R.mipmap.broadcast_3);
//                                }
//                            });
//
//
//                        }
//                    });
//                }
//
//
//                break;
        }
    }

    /**
     * 拍照
     */
    private void takePhotoBackground() {

        // 申请权限。
        AndPermission.with(getContext())
                .requestCode(REQUEST_CODE_PERMISSION)
                .permission(Permission.STORAGE, Permission.CAMERA)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        //获取权限成功
                        try {
                            //清除原来的相机组件

                            if (surfacePreView != null) {
                                try {
                                    mFrameLayout.removeView(surfacePreView);

                                } catch (Exception e) {
                                }
                                surfacePreView = null;
                            }

                            //把原来的相机关闭掉
                            try {
                                takePicture.stopPreview();
                                takePicture.release();
                                takePicture = null;
                            } catch (Exception e) {

                            }

                            //重新获取相机
                            takePicture = Camera.open(0);
                            takePicture.startPreview();
                            surfacePreView = new SurfacePreView(getContext(), takePicture);
                            mFrameLayout.addView(surfacePreView);
                            //由于相机加载需要时间 这里延迟500毫秒
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        takePicture.takePicture(null, null,
                                                new Camera.PictureCallback() {
                                                    @Override
                                                    public void onPictureTaken(byte[] data, Camera camera) {
                                                        LogUtil.i("图片拿到啦");
                                                        try {

                                                            File file = FileUtils.saveToSDCard(data); // 保存图片到sd卡中

                                                            builder = new PictureDialog.Builder(getContext());
                                                            builder.create();
                                                            builder.show(file);

                                                            //销毁相机下次重新加载
                                                            takePicture.release(); // 释放照相机
                                                            takePicture = null;
                                                            //弹弹窗
                                                            mFrameLayout.removeView(surfacePreView);
                                                            surfacePreView = null;
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                    } catch (Exception e) {

                                    }

                                }
                            }, 500);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        showToast("获取权限失败");
                    }
                })

                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                        LogUtil.i("77777showRequestPermissionRationale");
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
                })
                .start();

    }

    /**
     * 左边列表上下点击事件
     */
    @OnClick({R.id.ivPageLeft, R.id.ivPageRight})
    public void onPptChange(View view) {
        switch (view.getId()) {
            case R.id.ivPageLeft:
                webView.pgLeft();
                break;
            case R.id.ivPageRight:
                webView.pgRight();
                break;
        }
    }

    /**
     * 小屏 状态下顶部栏
     */
    @OnClick({R.id.ivHead})
    public void onTopBarClick(View view) {
        switch (view.getId()) {
            case R.id.ivHead:
                jumpToActivity(PersonalCenterActivity.class);
                break;

        }
    }

    /**
     * 下载附件
     */
    @OnClick(R.id.bt_file)
    public void onDownLoadFile() {
        //点击弹出对话框
        final DownLoadDialog downLoadDialog = new DownLoadDialog(getContext());
        downLoadDialog.setTitle("请输入电子邮箱");
        downLoadDialog.setYesOnclickListener("确定", new DownLoadDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String email) {
                if (TextUtils.isEmpty(email)) {
                    ToastUtils.showShort("请输入电子邮箱", getParent());
                } else {
                    CacheUtils.setCacheEmail(email);
                    LogUtil.i("==" + CacheUtils.getCacheEmail());

                    if (downLoadDialog.isEmail(email)){
                        HttpRequest httpRequest = new HttpRequest(AppUrl.downloadfile);//下载附件
                        if (!scheduleId.equals("-2")) {
                            httpRequest.add("scheduleId", scheduleId);
                        }
                        httpRequest.add("courseId", courseId);
                        httpRequest.add("receiver", email);
                        CallServer.getInstance().postRequest("下载附件", httpRequest,
                                new HttpResponseListener() {
                                    @Override
                                    public void onSucceed(String response, Gson gson) {
                                        BaseHttpResponse info = gson.fromJson(response, BaseHttpResponse.class);
                                        if (info.getCode() == 1) {
                                            isFinish = true;
                                        } else {

                                        }
                                        showToast("资料发送成功，请注意查收");
                                    }

                                    @Override
                                    public void onFailed(Exception exception) {

                                    }
                                }, getContext());
                    }else {
                        showToast("邮箱格式错误，请重新输入");
                    }
                    downLoadDialog.dismiss();
                }
            }
        });
        downLoadDialog.setNoOnclickListener("取消", new DownLoadDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                downLoadDialog.dismiss();
            }
        });
        downLoadDialog.show();
    }


    @OnClick(R.id.layoutCountDownTime)
    public void onCountDownTime() {

        SetCountTimeDialog.Builder builder = new SetCountTimeDialog.Builder(getContext());

        builder.setTime(lastTime + "");

        builder.setOnClickListener(new SetTimeListener() {
            @Override
            public void setTime(String time) {

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }

                allTime = Integer.parseInt(time);

                countDownTimer = new CountDownTimer(allTime * 1000 * 60, 60 * 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int a = (int) (millisUntilFinished / (1000 * 60));
                        lastTime = a;
                        LogUtil.i("剩余时间" + a);
                        tvCountDownTime.setText(a + "");

                        if (tvCountDownTime.getText().equals(a + "0")){
                            layoutCountDownTime.setVisibility(View.GONE);
                            layoutCountDownTime2.setVisibility(View.VISIBLE);
                        }else {
                            layoutCountDownTime.setVisibility(View.VISIBLE);
                            layoutCountDownTime2.setVisibility(View.GONE);
                        }

                        tvTimeBar.setText(a + "");

                        //
//                        获取进度条总长度

                        int line = startClassTools.getScreenHeight() - SizeUtils.dp2px(115);

                        int line2 = (int) (line * (1 - lastTime * 1.000 / allTime));

                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvTimeBar.getLayoutParams();
                        layoutParams.setMargins(0, line2, 0, 0);
                        tvTimeBar.setLayoutParams(layoutParams);
                    }

                    @Override
                    public void onFinish() {
                        tvCountDownTime.setText("0");

                        if (tvCountDownTime.getText().equals("0")){
                            layoutCountDownTime.setVisibility(View.GONE);
                            layoutCountDownTime2.setVisibility(View.VISIBLE);
                        }

                        lastTime = 0;
                        tvTimeBar.setText("0");
                        int line = startClassTools.getScreenHeight() - SizeUtils.dp2px(115);

                        int line2 = (int) (line * (1 - lastTime * 1.000 / allTime));

                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvTimeBar.getLayoutParams();
                        layoutParams.setMargins(0, line2, 0, 0);
                        tvTimeBar.setLayoutParams(layoutParams);
                    }
                };

                countDownTimer.start();

                setCountTimeDialog.dismiss();
            }
        });

        setCountTimeDialog = builder.create();

        setCountTimeDialog.show();

    }

    @OnClick(R.id.layoutCountDownTime2)
    public void onCountDownTimeOther() {

        onCountDownTime();

    }


    @OnClick(R.id.ivBack)
    public void onBackClicked() {
        myFinish();
    }

    @OnClick({R.id.layoutBigRightMenu01, R.id.layoutBigRightMenu02, R.id.layoutBigRightMenu04, R.id.layoutBigRightMenu06, R.id.layoutBigRightMenu07})
    public void onBigRightMenuClicked(View view) {
        initBigRightMenu();
        startClassTools.delayClick(view);
        switch (view.getId()) {
            case R.id.layoutBigRightMenu01:
                //提醒
                layoutBigRightMenu01.setChoose(true);
                //layoutBigRightMenu01.setBackgroundColor(Color.parseColor("#a1c813"));
                if (toolsPop04 == null) {
                    toolsPop04 = new ToolsPop04(getContext());

                    toolsPop04.showCircle01(false);
                    toolsPop04.showCircle02(false);
                    toolsPop04.showCircle03(false);
                    for (int i = 0; i < warsList.size(); i++) {
                        if (warsList.get(i).getPage().equals(String.valueOf((nnnn + 1)))) {
                            Wars wars = warsList.get(i);
                            if (!wars.getDis().equals("")) {
                                toolsPop04.showCircle01(true);
                            } else {
                                toolsPop04.showCircle01(false);
                            }
                            if (!wars.getSec().equals("")) {
                                toolsPop04.showCircle02(true);
                            } else {
                                toolsPop04.showCircle02(false);
                            }
                            if (!wars.getCus().equals("")) {
                                toolsPop04.showCircle03(true);
                            } else {
                                toolsPop04.showCircle03(false);
                            }
                            break;
                        }
                    }


                    startClassTools.addToolsPops(toolsPop04);
                    toolsPop04.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.layout01:
                                    //提醒
                                    DisRemindDialog.Builder builder = new DisRemindDialog.Builder(getContext());
                                    builder.create().show();
                                    toolsPop04.dismiss();

                                    break;
                                case R.id.layout02:
                                    SecRemindDialog.Builder builder1 = new SecRemindDialog.Builder(getContext());
                                    builder1.create().show();
                                    toolsPop04.dismiss();
                                    break;
                                case R.id.layout03:
                                    CustomRemindDialog.Builder builder2 = new CustomRemindDialog.Builder(getContext());
                                    builder2.create().show();
                                    toolsPop04.dismiss();
                                    break;

                            }
                        }
                    });
                }

                startClassTools.showToolsPops(toolsPop04, layoutBigRightMenu01);


//                if (toolsPop01.isShowing()) {
//                    toolsPop01.dismiss();
//                } else {
//                    toolsPop01.showAtLocation(layoutBigRightMenu01);
//                }


                break;
            case R.id.layoutBigRightMenu02:
                //表扬鼓励
                //layoutBigRightMenu02.setBackgroundColor(Color.parseColor("#a1c813"));
                layoutBigRightMenu02.setChoose(true);
                if (toolsPop01 == null) {
                    toolsPop01 = new ToolsPop01(getContext());
                    toolsPop01.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.layout01:
                                    //气球
                                    showEncouragePop01();
                                    break;
                                case R.id.layout02:
                                    //点赞
                                    showEncouragePop02();
                                    break;
                                case R.id.layout03:
                                    //掌声
                                    showEncouragePop03();
                                    break;
                            }
                            toolsPop01.dismiss();
                        }
                    });
                    startClassTools.addToolsPops(toolsPop01);
                }
                startClassTools.showToolsPops(toolsPop01, layoutBigRightMenu02);
//                if (toolsPop01.isShowing()) {
//                    toolsPop01.dismiss();
//                } else {
//                    toolsPop01.showAtLocation(layoutBigRightMenu02);
//                }
                break;
            case R.id.layoutBigRightMenu04:
                //画笔

                showBlackboardPop2();


//                if (preView.isOpen()) {
//                    preView.controlZoomView();
//                }
//                if (telescopeView.getVisibility() == View.VISIBLE) {
//                    telescopeView.setVisibility(View.GONE);
//                }
//                if (toolsPop02 != null) {
//                    toolsPop02.clickMenu(-1);
//                }
//
//
//                boardView.setPaintType(AnnotationConfig.PaintType.Paint_Red);
//                isShowBoardView = false;
//                boardViewControl();
////                menuRightIsClose = false;
////                setMenuRight();
//
//                layoutBigRightMenu04.setBackgroundColor(Color.parseColor("#a1c813"));
                startClassTools.dismissAllToolsPops();
                if (!menuRightIsClose) {
                    setMenuRight();
                }
                break;
            case R.id.layoutBigRightMenu06:
                //学科工具
                if (pptAllInfo == null) {
                    return;
                }
                if (pdf_3d != null) {
                    circle03.setVisibility(View.VISIBLE);
                } else {
                    circle03.setVisibility(View.INVISIBLE);
                }
                if (toolsPop05 == null) {
                    toolsPop05 = new ToolsPop05(getContext(), pptAllInfo);
                    toolsPop05.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.layout01:
                                    //三维图纸
                                    showBuildPicPop();
                                    toolsPop05.dismiss();
                                    break;
                                case R.id.layout02:
                                    //提问环节
                                    showQuestionPop();
                                    toolsPop05.dismiss();
                                    break;
                                case R.id.layout03:
                                    //搭建参考
                                    if (pdf_3d != null) {
                                        startActivity(new Intent(getContext(), WebViewActivity.class)
                                                .putExtra("url", pdf_3d)
                                                .putExtra("title", ""));
                                        toolsPop05.dismiss();
                                    } else {
                                        showToast("检查一下，没有体现");
                                    }
                                    toolsPop05.dismiss();
                                    break;
                            }
                        }
                    });
                    startClassTools.addToolsPops(toolsPop05);
                }
                startClassTools.showToolsPops(toolsPop05, layoutBigRightMenu06);
//                layoutBigRightMenu06.setBackgroundColor(Color.parseColor("#a1c813"));
//                layoutBigRightMenu06.setChoose(true);
                break;
            case R.id.layoutBigRightMenu07:
                //常用工具
//                layoutBigRightMenu07.setBackgroundColor(Color.parseColor("#a1c813"));
//                layoutBigRightMenu07.setChoose(true);

                if (toolsPop02 == null) {
                    toolsPop02 = new ToolsPop02(getContext());
                    toolsPop02.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.layout01:
                                    //黑板
                                    showBlackboardPop();
                                    break;
                                case R.id.layout02:

                                    if (spotlightPop == null) {
                                        spotlightPop = new SpotlightPop(getContext());
                                    }
                                    spotlightPop.showAtLocation(mainFrameLayout);
                                    toolsPop02.dismiss();

                                    break;
                                case R.id.layout03:

                                    if (magnifierPop == null) {
                                        magnifierPop = new MagnifierPop(getContext());
                                    }
                                    toolsPop02.dismiss();
                                    magnifierPop.showAtLocation(mainFrameLayout);

                                    break;
                                case R.id.layout04:
                                    //背景音乐
                                    showMusicPop();
                                    toolsPop02.dismiss();
                                    break;
                                case R.id.layout05:
                                    //打开计时器
                                    showAlarmPop();
                                    toolsPop02.dismiss();
                                    break;
                                case R.id.layout06:
                                    //下课
                                    showClassOverPop();
                                    toolsPop02.dismiss();
                                    break;
                                case R.id.layout07:
                                    //竞赛
                                    showCompetitionPop();
                                    toolsPop02.dismiss();
                                    break;
                            }
                        }
                    });
                    startClassTools.addToolsPops(toolsPop02);
                }
                startClassTools.showToolsPops(toolsPop02, layoutBigRightMenu07);
                break;
        }
    }

    /**
     * 显示画板弹窗
     */
    private void showBlackboardPop2() {
        if (blackboardPop2 == null) {
            blackboardPop2 = new BlackboardPop(getContext(), false);
            blackboardPop2.setNarrowListener(narrowListener);
        }
        blackboardPop2.showAtLocation(mainFrameLayout);

        isShowNarrowIcon04 = false;
        setNarrowIconUi();
    }

    /**
     * 显示黑板弹窗
     */
    private void showBlackboardPop() {
        if (blackboardPop == null) {
            blackboardPop = new BlackboardPop(getContext(), true);
            blackboardPop.setNarrowListener(narrowListener);
        }
        blackboardPop.showAtLocation(mainFrameLayout);
        isShowNarrowIcon03 = false;
        setNarrowIconUi();
    }

    /**
     * 小屏幕状态下工具栏1事件
     * 学科工具
     */
    @OnClick({R.id.ivSmallTools01Close, R.id.ivSmallTools01_1, R.id.ivSmallTools01_2, R.id.ivSmallTools01_3})
    public void onSmallTools01Clicked(View view) {
        switch (view.getId()) {
            case R.id.ivSmallTools01Close:
                //关闭工具栏 1
                startClassTools.closeTools(layoutSmallTools01, startClassTools.getSmallRightWidth());
                break;
            case R.id.ivSmallTools01_1:
                //三维图纸
                showBuildPicPop();
                break;
            case R.id.ivSmallTools01_2:
                //提问环节
                showQuestionPop();
                break;
            case R.id.ivSmallTools01_3:
                //搭建参考
                if (pdf_3d != null) {
                    startActivity(new Intent(getContext(), WebViewActivity.class)
                            .putExtra("url", pdf_3d)
                            .putExtra("title", ""));
                } else {
                    showToast("检查一下，没有体现");
                }
                break;
        }
    }

    /**
     * 显示搭建参考弹窗
     */
    private void showReferPop() {
        if (pptAllInfo == null) {
            startActivity(new Intent(getContext(), WebViewActivity.class)
                    .putExtra("url", pdf_3d)
                    .putExtra("title", ""));
            return;
        }
        if (pptAllInfo.getData().getPdf_3d().trim().equals("")) {
            showToast("检查一下，没有体现");
            return;
        }

    }

    /**
     * 显示三维图纸弹窗
     */
    private void showBuildPicPop() {
        if (pptAllInfo == null) {
            return;
        }

        if (pptAllInfo.getData().getDrawing().trim().equals("")) {
            showToast("该课程没有图纸");
            return;
        }
        buildPicPop.showAtLocation(mainFrameLayout);
    }

    /**
     * 展示提问环节
     */
    private void showQuestionPop() {

        if (pptAllInfo == null) {
            return;
        }

        if (pptAllInfo.getData().getQuiz().size() == 0) {
            showToast("该课程没有提问环节");
            return;
        }


        if (questionPop == null) {
            questionPop = new QuestionPop(getContext(), questions);
            questionPop.setNarrowListener(narrowListener);
        }
        questionPop.showAtLocation(mainFrameLayout);

        isShowNarrowIcon01 = false;
        setNarrowIconUi();
    }

    /**
     * 小屏 提醒菜单栏
     *
     * @param view .
     */
    @OnClick({R.id.ivSmallTools03Close, R.id.layoutSmallTools03_1, R.id.layoutSmallTools03_2, R.id.layoutSmallTools03_3})
    public void onSmallTools03Clicked(View view) {
        switch (view.getId()) {
            case R.id.ivSmallTools03Close:
                startClassTools.closeTools(layoutSmallTools03, startClassTools.getSmallRightWidth());
                break;
            case R.id.layoutSmallTools03_1:
                //提醒

                showLoading();
                HttpRequest httpRequest = new HttpRequest(AppUrl.getPpt);//"获取详情"
                if (!scheduleId.equals("-2")) {
                    httpRequest.add("scheduleId", scheduleId);
                }
                httpRequest.add("courseId", courseId);
                CallServer.getInstance().postRequest("纪律提醒", httpRequest,
                        new HttpResponseListener() {
                            @Override
                            public void onSucceed(String response, Gson gson) {
                                DisRemindDialog.Builder builder = new DisRemindDialog.Builder(getContext());
//                                builder.setWarsList(warsList);
//                                builder.setText(warsList.get(0).getDis());
                                builder.create().show();
                            }

                            @Override
                            public void onFailed(Exception exception) {
                                closeLoading();
                            }
                        },getContext());
                break;
            case R.id.layoutSmallTools03_2:
                showLoading();
                HttpRequest httpRequest2 = new HttpRequest(AppUrl.getPpt);//"获取详情"
                if (!scheduleId.equals("-2")) {
                    httpRequest2.add("scheduleId", scheduleId);
                }
                httpRequest2.add("courseId", courseId);
                CallServer.getInstance().postRequest("安全提醒", httpRequest2,
                        new HttpResponseListener() {
                            @Override
                            public void onSucceed(String response, Gson gson) {
                                SecRemindDialog.Builder builder1 = new SecRemindDialog.Builder(getContext());
//                                builder1.setText(warsList.get(0).getSec());
                                builder1.create().show();
                            }

                            @Override
                            public void onFailed(Exception exception) {
                                closeLoading();
                            }
                        },getContext());

                break;
            case R.id.layoutSmallTools03_3:
                showLoading();
                HttpRequest httpRequest3 = new HttpRequest(AppUrl.getPpt);//"获取详情"
                if (!scheduleId.equals("-2")) {
                    httpRequest3.add("scheduleId", scheduleId);
                }
                httpRequest3.add("courseId", courseId);
                CallServer.getInstance().postRequest("自定义提醒", httpRequest3,
                        new HttpResponseListener() {
                            @Override
                            public void onSucceed(String response, Gson gson) {
                                CustomRemindDialog.Builder builder2 = new CustomRemindDialog.Builder(getContext());
//                                builder2.setText(warsList.get(0).getCus());
                                builder2.create().show();
                            }

                            @Override
                            public void onFailed(Exception exception) {
                                closeLoading();
                            }
                        },getContext());

                break;
        }
    }

    private void showTip(int type) {
        if (tipPop == null) {
            tipPop = new TipPop(getContext(), 1);
        }
        boolean h = false;
        LogUtil.i("==" + listP);
        for (int i = 0; i < warsList.size(); i++) {
            if (warsList.get(i).getPage().equals(String.valueOf(listP + 1))) {
                //说明有
                if (type == 1) {
                    h = true;
                    //纪律提醒
                    tipPop.setIvBg(warsList.get(i).getDis());
                    break;
                } else if (type == 2) {
                    h = true;
                    tipPop.setIvBg(warsList.get(i).getSec());
                } else if (type == 3) {
                    h = true;
                    tipPop.setIvBg(warsList.get(i).getCus());
                }
            }
        }

        if (!h) {
            tipPop.setIvBg(R.mipmap.tip_05);
        }
        tipPop.showAtLocation(mainFrameLayout);
    }

    /**
     * 小屏 表扬鼓励菜单栏
     *
     * @param view .
     */
    @OnClick({R.id.ivSmallTools04Close, R.id.layoutSmallTools04_1, R.id.layoutSmallTools04_2, R.id.layoutSmallTools04_3})
    public void onSmallTools04Clicked(View view) {
        switch (view.getId()) {
            case R.id.ivSmallTools04Close:
                startClassTools.closeTools(layoutSmallTools04, startClassTools.getSmallRightWidth());
                break;
            case R.id.layoutSmallTools04_1:
                //气球
                showEncouragePop01();
                break;
            case R.id.layoutSmallTools04_2:
                //点赞
                showEncouragePop02();
                break;
            case R.id.layoutSmallTools04_3:
                //掌声
                showEncouragePop03();
                break;
        }
    }

    /**
     * 气球
     */
    private void showEncouragePop01() {
        if (encouragePop01 == null) {
            encouragePop01 = new GifPop(getContext(), R.mipmap.gif_04);
        }
        encouragePop01.showAtLocation(mainFrameLayout);
    }

    /**
     * 点赞
     */
    private void showEncouragePop02() {
        if (encouragePop02 == null) {
            encouragePop02 = new GifPop(getContext(), R.mipmap.gif_03);
        }
        encouragePop02.showAtLocation(mainFrameLayout);
    }

    /**
     * 掌声
     */
    private void showEncouragePop03() {
        if (encouragePop03 == null) {
            encouragePop03 = new GifPop(getContext(), R.mipmap.gif_02);
        }
        encouragePop03.showAtLocation(mainFrameLayout);
    }

    /**
     * 小屏 常用工具菜单栏
     *
     * @param view .
     */
    @OnClick({R.id.ivSmallTools05Close, R.id.layoutSmallTools05_1, R.id.layoutSmallTools05_2, R.id.layoutSmallTools05_3, R.id.layoutSmallTools05_4})
    public void onSmallTools05Clicked(View view) {
        switch (view.getId()) {
            case R.id.ivSmallTools05Close:
                //关闭常用工具菜单栏
                startClassTools.closeTools(layoutSmallTools05, startClassTools.getSmallRightWidth());
                break;
            case R.id.layoutSmallTools05_1:
                //背景音乐
                showMusicPop();
                break;
            case R.id.layoutSmallTools05_2:
                //计时器
                showAlarmPop();
                break;
            case R.id.layoutSmallTools05_3:
                //下课
                showClassOverPop();
                break;
            case R.id.layoutSmallTools05_4:
                //竞赛
                showCompetitionPop();
                break;
        }
    }

    /**
     * 显示背景音乐弹窗
     */
    private void showMusicPop() {
        if (musicPop == null) {
            musicPop = new MusicPop(getContext());
        }
        musicPop.showAtLocation(mainFrameLayout);
    }

    /**
     * 计时器弹窗
     */
    private void showAlarmPop() {
        if (alarmPop == null) {
            alarmPop = new AlarmPop(getContext());
        }
        alarmPop.showAtLocation2(mainFrameLayout);
    }

    /**
     * 下课弹窗
     */
    private void showClassOverPop() {
        if (gifPop == null) {
            gifPop = new GifPop(getContext(), R.mipmap.gif_01);
        }
        gifPop.showAtLocation(mainFrameLayout);
    }

    /**
     * 展示竞赛弹窗
     */
    private void showCompetitionPop() {
        if (competitionPop == null) {
            competitionPop = new CompetitionPop(getContext());
            competitionPop.setNarrowListener(narrowListener);
        }
        competitionPop.showAtLocation(mainFrameLayout);
        isShowNarrowIcon02 = false;
        setNarrowIconUi();
    }

    /**
     * 工具栏最小化
     *
     * @param view .
     */
    @OnClick({R.id.ivNarrowIcon01, R.id.ivNarrowIcon02, R.id.ivNarrowIcon03, R.id.ivNarrowIcon04})
    public void onNarrowIconClicked(View view) {
        switch (view.getId()) {
            case R.id.ivNarrowIcon01:
                showQuestionPop();
                break;
            case R.id.ivNarrowIcon02:
                showCompetitionPop();
                break;
            case R.id.ivNarrowIcon03:
                showBlackboardPop();
                break;
            case R.id.ivNarrowIcon04:
                showBlackboardPop2();
                break;
        }
    }

    @OnClick(R.id.iv10)
    public void onTop() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);

        Intent intent2 = new Intent(this, StartFloatBallService.class);
        startService(intent2);
//        FloatMessagerMainWindow.getFloatMessagerMainWindow(getContext(), null);
    }

    @Override
    public void takeSuccess(TResult result) {
        LogUtil.i("获取图片位置" + result.getImages().get(0).getCompressPath() + "   " + result.getImages().get(0).getOriginalPath());
        File file = new File(result.getImages().get(0).getOriginalPath());
        if (file.exists()) {
            LogUtil.i("获取图片位置存在");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        }

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

    /**
     * 底部提示点击
     *
     * @param view
     */
    @OnClick({R.id.ivBottomBar03, R.id.ivBottomBar05})
    public void onBottomBarClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBottomBar03:
                //
                if (classTip != null) {
                    startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", classTip.getImage()).putExtra("title", ""));
                }
                break;
//            case R.id.ivBottom02:
//                if (classTip !=null){
//
//                }
//                break;
            case R.id.ivBottomBar05:
                if (classTip != null) {
                    startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", classTip.getVideo()).putExtra("title", ""));
                }
                break;
        }
    }

    @OnClick({R.id.ivPL, R.id.ivPR})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivPL:
                webView.pgLeft();
                break;
            case R.id.ivPR:
                webView.pgRight();
                break;
        }
    }


//
//            @OnClick({R.id.iv01, R.id.iv02, R.id.iv03})
//            public void onGetPhotoClicked(View view) {
//                //头像
//                BottomGetPhotoDialog.Builder builder = new BottomGetPhotoDialog.Builder(getContext());
//                builder.setGetPhotoOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
//                        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//                        Uri imageUri = Uri.fromFile(file);
//        //                takePhoto.onPickFromGalleryWithCrop(imageUri, new CropOptions.Builder().setAspectX(84).setAspectY(84).setWithOwnCrop(false).create());
//                        takePhoto.onPickFromGallery();
//                        bottomGetPhotoDialog.dismiss();
//                    }
//                });
//                builder.setTakePhotoOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
//                        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//                        Uri imageUri = Uri.fromFile(file);
//        //                takePhoto.onPickFromCaptureWithCrop(imageUri, new CropOptions.Builder().setAspectX(84).setAspectY(84).setWithOwnCrop(false).create());
//                        takePhoto.onPickFromCapture(imageUri);
//
//                        bottomGetPhotoDialog.dismiss();
//                    }
//                });
//                bottomGetPhotoDialog = builder.create();
//                bottomGetPhotoDialog.show();
//                switch (view.getId()) {
//                    case R.id.iv01:
//                        break;
//                    case R.id.iv02:
//                        break;
//                    case R.id.iv03:
//                        break;
//                }
//            }
}
