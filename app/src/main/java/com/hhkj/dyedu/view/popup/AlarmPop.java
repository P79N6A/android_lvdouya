package com.hhkj.dyedu.view.popup;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.view.MyPopupWindow;
import com.hhkj.dyedu.view.NumTextView;
import com.hhkj.dyedu.view.ToolsPop;

import static com.hhkj.dyedu.GlobalVariable.SECOND;

/**
 * Created by zangyi_shuai_ge on 2018/5/30
 *
 * @author zangyi 767450430
 * 计时器 定时器
 */

public class AlarmPop extends ToolsPop {


    Handler handler = new Handler();
    int offsetX = 500, offsetY = 500;
    //背景
    //切换计时器 与倒计时的按钮
    private View view01, view02;
    private ImageView ivBg;
    private ImageView ivClose;
    //
    private LinearLayout layout01, layout02;
    //计时器的4个数字
    private NumTextView tvTimer01, tvTimer02, tvTimer03, tvTimer04;
    //
    private ImageView ivBt01, ivBt02;
    //倒计时
    //4个 加 按钮
    private ImageView ivCountdownAdd01, ivCountdownAdd02, ivCountdownAdd03, ivCountdownAdd04;
    //4个数字
    private NumTextView tvCountdown01, tvCountdown02, tvCountdown03, tvCountdown04;
    //4个减按钮
    private ImageView ivCountdownReduce01, ivCountdownReduce02, ivCountdownReduce03, ivCountdownReduce04;
    private CountDownTimer countDownTimer;
    private int w, h;
    private int nowType = 1;// 1为计时器  2为倒计时
    private boolean isStop = false;
    private boolean isStopTimer = true;
    private int recLen = 0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isStopTimer) {
                recLen++;//单位是秒
                //
                int a = recLen / 60;//分
                int c = a / 10;
                int d = a % 10;
                tvTimer01.setText("" + c);
                tvTimer02.setText("" + d);


                int b = recLen % 60;//秒

                int e = b / 10;
                int f = b % 10;

                tvTimer03.setText("" + e);
                tvTimer04.setText("" + f);

                if (recLen >= 60 * 100) {
                    isStopTimer = true;
                    ivBt02.setImageResource(R.mipmap.alarm_06);
                }

                handler.postDelayed(this, SECOND);
            }

        }
    };
    private View view;
    private boolean isShowed = false;

    public AlarmPop(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.pop_alarm, null);
        int width = (int) context.getResources().getDimension(R.dimen.px_688);
        int height =(int) context.getResources().getDimension(R.dimen.px_464);
        popupWindow = new MyPopupWindow(view, width, height, false);
        w = ScreenUtils.getScreenWidth();
        h = ScreenUtils.getScreenHeight();

        view.setOnTouchListener(new View.OnTouchListener() {
            int orgX, orgY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        orgX = (int) event.getX();
                        orgY = (int) event.getY();
//                        LogUtil.i("点击的坐标"+orgX+"  "+orgY+"   "+view.getX());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        offsetX = (int) event.getRawX() - orgX;
                        offsetY = (int) event.getRawY() - orgY;
                        // popupWindow 已左下角为原点
                        popupWindow.update(offsetX, offsetY, -1, -1, true);
                        break;
                }
                return true;
            }
        });


        view01 = view.findViewById(R.id.view01);
        view02 = view.findViewById(R.id.view02);

        layout01 = view.findViewById(R.id.layout01);
        layout02 = view.findViewById(R.id.layout02);


        //计时器
        view01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //把倒计时暂停了先
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                //显示UI
                ivBg.setImageResource(R.mipmap.alarm_01);
                layout02.setVisibility(View.VISIBLE);
                layout01.setVisibility(View.GONE);
                //按钮设置为开始的状态
                ivBt02.setImageResource(R.mipmap.alarm_06);
                //改标志
                nowType = 1;

            }
        });
        //倒计时
        view02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把计时器暂停了先
                isStopTimer = true;
                //显示UI
                ivBg.setImageResource(R.mipmap.alarm_02);
                layout02.setVisibility(View.GONE);
                layout01.setVisibility(View.VISIBLE);
                //按钮设置为开始的状态
                ivBt02.setImageResource(R.mipmap.alarm_06);
                //改标志
                nowType = 2;
            }
        });

        ivBg = view.findViewById(R.id.ivBg);
        //关闭按钮
        ivClose = view.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isStopTimer = true;
                popupWindow.dismiss();

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }

            }
        });


        tvTimer01 = view.findViewById(R.id.tvTimer01);
        tvTimer02 = view.findViewById(R.id.tvTimer02);
        tvTimer03 = view.findViewById(R.id.tvTimer03);
        tvTimer04 = view.findViewById(R.id.tvTimer04);

        tvCountdown01 = view.findViewById(R.id.tvCountdown01);
        tvCountdown02 = view.findViewById(R.id.tvCountdown02);
        tvCountdown03 = view.findViewById(R.id.tvCountdown03);
        tvCountdown04 = view.findViewById(R.id.tvCountdown04);

        ivCountdownAdd01 = view.findViewById(R.id.ivCountdownAdd01);
        ivCountdownAdd02 = view.findViewById(R.id.ivCountdownAdd02);
        ivCountdownAdd03 = view.findViewById(R.id.ivCountdownAdd03);
        ivCountdownAdd04 = view.findViewById(R.id.ivCountdownAdd04);

        ivCountdownReduce01 = view.findViewById(R.id.ivCountdownReduce01);
        ivCountdownReduce02 = view.findViewById(R.id.ivCountdownReduce02);
        ivCountdownReduce03 = view.findViewById(R.id.ivCountdownReduce03);
        ivCountdownReduce04 = view.findViewById(R.id.ivCountdownReduce04);
        ivCountdownReduce01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先暂停

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                ivBt02.setImageResource(R.mipmap.alarm_06);

                int a = Integer.parseInt(tvCountdown01.getText().toString().trim());
                if (a != 0) {
                    a = a - 1;
                }
                tvCountdown01.setText(a + "");
            }
        });
        ivCountdownReduce02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先暂停

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                ivBt02.setImageResource(R.mipmap.alarm_06);

                int a = Integer.parseInt(tvCountdown02.getText().toString().trim());
                if (a != 0) {
                    a = a - 1;
                }
                tvCountdown02.setText(a + "");
            }
        });
        ivCountdownReduce03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先暂停

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                ivBt02.setImageResource(R.mipmap.alarm_06);

                int a = Integer.parseInt(tvCountdown03.getText().toString().trim());
                if (a != 0) {
                    a = a - 1;
                }
                tvCountdown03.setText(a + "");
            }
        });
        ivCountdownReduce04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先暂停

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                ivBt02.setImageResource(R.mipmap.alarm_06);

                int a = Integer.parseInt(tvCountdown04.getText().toString().trim());
                if (a != 0) {
                    a = a - 1;
                }
                tvCountdown04.setText(a + "");
            }
        });
        ivCountdownAdd01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先暂停

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                ivBt02.setImageResource(R.mipmap.alarm_06);

                int a = Integer.parseInt(tvCountdown01.getText().toString().trim());
                if (a != 9) {
                    a = a + 1;
                }
                tvCountdown01.setText(a + "");
            }
        });
        ivCountdownAdd02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先暂停

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                ivBt02.setImageResource(R.mipmap.alarm_06);

                int a = Integer.parseInt(tvCountdown02.getText().toString().trim());
                if (a != 9) {
                    a = a + 1;
                }
                tvCountdown02.setText(a + "");
            }
        });

        ivCountdownAdd03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先暂停

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                ivBt02.setImageResource(R.mipmap.alarm_06);

                int a = Integer.parseInt(tvCountdown03.getText().toString().trim());
                if (a != 9) {
                    a = a + 1;
                }
                tvCountdown03.setText(a + "");
            }
        });
        ivCountdownAdd04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先暂停

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                ivBt02.setImageResource(R.mipmap.alarm_06);

                int a = Integer.parseInt(tvCountdown04.getText().toString().trim());
                if (a != 9) {
                    a = a + 1;
                }
                tvCountdown04.setText(a + "");
            }
        });
        ivBt01 = view.findViewById(R.id.ivBt01);
        ivBt02 = view.findViewById(R.id.ivBt02);


        //重置
        ivBt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nowType == 1) {
                    tvTimer01.setText(0 + "");
                    tvTimer02.setText(0 + "");
                    tvTimer03.setText(0 + "");
                    tvTimer04.setText(0 + "");

                    recLen = 0;
                    isStopTimer = true;
                    ivBt02.setImageResource(R.mipmap.alarm_06);

                } else if (nowType == 2) {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                        countDownTimer = null;
                    }
                    tvCountdown01.setText("0");
                    tvCountdown02.setText("0");
                    tvCountdown03.setText("0");
                    tvCountdown04.setText("0");
                    ivBt02.setImageResource(R.mipmap.alarm_06);
                }


            }
        });
        ivBt02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nowType == 1) {

                    if (isStopTimer) {
                        //开始计时
                        isStopTimer = false;
                        ivBt02.setImageResource(R.mipmap.alarm_07);
                        handler.postDelayed(runnable, SECOND);
                    } else {
                        isStopTimer = true;
                        ivBt02.setImageResource(R.mipmap.alarm_06);
                    }
                } else if (nowType == 2) {

                    if (countDownTimer == null) {
                        int allTime;
                        int a1 = Integer.parseInt(tvCountdown01.getText().toString());
                        int a2 = Integer.parseInt(tvCountdown02.getText().toString());
                        int a3 = Integer.parseInt(tvCountdown03.getText().toString());
                        int a4 = Integer.parseInt(tvCountdown04.getText().toString());

                        allTime = (a1 * 10 + a2) * 60 + a3 * 10 + a4;


                        countDownTimer = new CountDownTimer(allTime * SECOND, SECOND) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                int all = (int) (millisUntilFinished / SECOND);//秒

                                //
                                int a = all / 60;//分
                                int c = a / 10;
                                int d = a % 10;
                                tvCountdown01.setText("" + c);
                                tvCountdown02.setText("" + d);
                                int b = all % 60;//秒
                                int e = b / 10;
                                int f = b % 10;
                                tvCountdown03.setText("" + e);
                                tvCountdown04.setText("" + f);
                            }

                            @Override
                            public void onFinish() {
                                tvCountdown01.setText("0");
                                tvCountdown02.setText("0");
                                tvCountdown03.setText("0");
                                tvCountdown04.setText("0");
                                countDownTimer = null;
                                ivBt02.setImageResource(R.mipmap.alarm_06);
                            }
                        };
                        ivBt02.setImageResource(R.mipmap.alarm_07);
                        countDownTimer.start();
                    } else {
                        //暂停
                        countDownTimer.cancel();
                        countDownTimer = null;
                        ivBt02.setImageResource(R.mipmap.alarm_06);
                    }
                }
            }
        });
        initUI();

    }

    private void initUI() {
        //计时器状态
        ivBg.setImageResource(R.mipmap.alarm_01);
        layout02.setVisibility(View.VISIBLE);
        layout01.setVisibility(View.GONE);

        nowType = 1;
        tvTimer01.setText(0 + "");
        tvTimer02.setText(0 + "");
        tvTimer03.setText(0 + "");
        tvTimer04.setText(0 + "");

        recLen = 0;
        isStopTimer = true;
        ivBt02.setImageResource(R.mipmap.alarm_06);
    }

    public void onDestroy() {
        isStopTimer=true;
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer=null;
        }
    }


    public void showAtLocation2(View view) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAtLocation(view, Gravity.TOP | Gravity.LEFT, SizeUtils.dp2px(14), SizeUtils.dp2px(106));
            popupWindow.update(offsetX, offsetY, -1, -1, true);
        }
    }
}
