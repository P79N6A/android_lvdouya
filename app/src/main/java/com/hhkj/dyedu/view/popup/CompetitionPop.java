package com.hhkj.dyedu.view.popup;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.CompetitionAdapter;
import com.hhkj.dyedu.bean.model.Competition;
import com.hhkj.dyedu.callback.NarrowListener;
import com.hhkj.dyedu.view.MyPopupWindow;
import com.hhkj.dyedu.view.ToolsPop;
import com.hhkj.dyedu.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.LinearLayout.HORIZONTAL;
import static com.hhkj.dyedu.GlobalVariable.SECOND;

/**
 * Created by zangyi_shuai_ge on 2018/7/26
 *
 * @author zangyi 767450430
 * 团队竞赛
 */
public class CompetitionPop extends ToolsPop {
    //1秒 1000

    Handler handler = new Handler();
    private CompetitionAdapter competitionAdapter;
    private RecyclerView recyclerView;
    private ImageView ivReduce, ivAdd;
    private ImageView ivLeft, ivRight, iv02;
    private LinearLayoutManager linearLayoutManager;
    private NarrowListener narrowListener;
    private boolean isCompetition = false;//是否在竞赛
    private int recLen = 0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isCompetition) {
                recLen++;//单位是秒
                LogUtil.i("定时器" + recLen);
                handler.postDelayed(runnable, SECOND);
                //
                int a = recLen / 60;//分
                int c = a / 10;
                int d = a % 10;


                int b = recLen % 60;//秒

                int e = b / 10;
                int f = b % 10;


                int size = competitionAdapter.getData().size();
                for (int i = 0; i < size; i++) {
                    Competition competition = competitionAdapter.getData().get(i);
                    if (!competition.isStop()) {
                        competition.setTime(String.valueOf(c), String.valueOf(d), String.valueOf(e), String.valueOf(f));
                    }
                }
                competitionAdapter.notifyDataSetChanged();
                if (recLen >= 60 * 100) {
                    isCompetition = true;
                    iv02.setImageResource(R.mipmap.bt_over);
                }
            }

        }
    };
    private ImageView ivBottom01, ivBottom02;

    private RoundTextView tv01, tv02, tv03;

    public CompetitionPop(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_competition, null);
        int width = MATCH_PARENT;
        int height = ScreenUtils.getScreenHeight() - 90;
        popupWindow = new MyPopupWindow(view, width, height, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        tv01 = view.findViewById(R.id.tvStart);
        tv02 = view.findViewById(R.id.tvFinish);
        tv03 = view.findViewById(R.id.tvReset);

        tv03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重置
                recLen = 0;
                int size = competitionAdapter.getData().size();
                for (int i = 0; i < size; i++) {
                    Competition competition = competitionAdapter.getData().get(i);
                    competition.setStop(false);
                    competition.setScore(0);
                    competition.setRank(-1);
                    competition.setTime(String.valueOf(0), String.valueOf(0), String.valueOf(0), String.valueOf(0));
                }

                competitionAdapter.setMaxPos(-1);
                competitionAdapter.setNowRank(-1);
                competitionAdapter.notifyDataSetChanged();

            }
        });


        tv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCompetition) {
                    isCompetition = false;
                    iv02.setImageResource(R.mipmap.bt_over);

                    competitionAdapter.setNowRank(-1);
                    //算分
                    int maxPos = 0;
                    int maxScore = 0;
                    for (int i = 0; i < competitionAdapter.getData().size(); i++) {
                        if (competitionAdapter.getData().get(i).getScore() > maxScore) {
                            maxPos = i;
                            maxScore = competitionAdapter.getData().get(i).getScore();
                        }
                    }

                    if (maxPos - 1 >= 0) {
                        recyclerView.smoothScrollToPosition(maxPos - 1);
                    } else {
                        recyclerView.smoothScrollToPosition(maxPos);
                    }


                    competitionAdapter.setMaxPos(maxPos);
                    competitionAdapter.notifyDataSetChanged();

                } else {
//                    isCompetition = true;
//                    //开始的时候重置所有参数
//                    recLen = 0;
//                    int size = competitionAdapter.getData().size();
//                    for (int i = 0; i < size; i++) {
//                        Competition competition = competitionAdapter.getData().get(i);
//                        competition.setStop(false);
//                        competition.setScore(0);
//                        competition.setRank(-1);
//                        competition.setTime(String.valueOf(0), String.valueOf(0), String.valueOf(0), String.valueOf(0));
//                    }
//
//                    competitionAdapter.setMaxPos(-1);
//                    competitionAdapter.setNowRank(-1);
//                    competitionAdapter.notifyDataSetChanged();
//                    iv02.setImageResource(R.mipmap.bt_begain);
//                    handler.postDelayed(runnable, SECOND);
                }
            }
        });

        tv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCompetition) {
//                    isCompetition = false;
//                    iv02.setImageResource(R.mipmap.bt_over);
//
//                    competitionAdapter.setNowRank(-1);
//                    //算分
//                    int maxPos = 0;
//                    int maxScore = 0;
//                    for (int i = 0; i < competitionAdapter.getData().size(); i++) {
//                        if (competitionAdapter.getData().get(i).getScore() > maxScore) {
//                            maxPos = i;
//                            maxScore = competitionAdapter.getData().get(i).getScore();
//                        }
//                    }
//
//                    if (maxPos - 1 >= 0) {
//                        recyclerView.smoothScrollToPosition(maxPos - 1);
//                    } else {
//                        recyclerView.smoothScrollToPosition(maxPos);
//                    }
//
//
//                    competitionAdapter.setMaxPos(maxPos);
//                    competitionAdapter.notifyDataSetChanged();

                } else {
                    isCompetition = true;
                    //开始的时候重置所有参数
                    recLen = 0;
                    int size = competitionAdapter.getData().size();
                    for (int i = 0; i < size; i++) {
                        Competition competition = competitionAdapter.getData().get(i);
                        competition.setStop(false);
                        competition.setScore(0);
                        competition.setRank(-1);
                        competition.setTime(String.valueOf(0), String.valueOf(0), String.valueOf(0), String.valueOf(0));
                    }

                    competitionAdapter.setMaxPos(-1);
                    competitionAdapter.setNowRank(-1);
                    competitionAdapter.notifyDataSetChanged();
                    iv02.setImageResource(R.mipmap.bt_begain);
                    handler.postDelayed(runnable, SECOND);
                }
            }
        });
        linearLayoutManager = new LinearLayoutManager(context, HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        competitionAdapter = new CompetitionAdapter((int) ((width - SizeUtils.dp2px(160)) / 3.00), height - SizeUtils.dp2px(170));
        recyclerView.getItemAnimator().setChangeDuration(0);

        List<Competition> list = new ArrayList<>();
        list.add(new Competition(0));
        competitionAdapter.replaceData(list);

        competitionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Competition competition = competitionAdapter.getData().get(position);
                int score = competition.getScore();
                switch (view.getId()) {
                    case R.id.iv01:
                        //加
                        score = score + 1;
                        if (score >= 99) {
                            score = 99;
                        }
                        competitionAdapter.getData().get(position).setScore(score);
                        competitionAdapter.notifyItemChanged(position);
                        break;
                    case R.id.iv02:
                        score = score - 1;
                        if (score <= 0) {
                            score = 0;
                        }
                        competitionAdapter.getData().get(position).setScore(score);
                        competitionAdapter.notifyItemChanged(position);
                        break;
                    case R.id.layoutTime:
                        if (!competition.isStop()) {
                            competition.setStop(true);
                            int rank1 = competitionAdapter.getNowRank();
                            competition.setRank(rank1 + 1);
                            competitionAdapter.setNowRank(competition.getRank());
                            competitionAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        });


        recyclerView.setAdapter(competitionAdapter);
        SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.START);
        snapHelperTop.attachToRecyclerView(recyclerView);
        iv02 = view.findViewById(R.id.iv02);

        //开始竞赛
        iv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCompetition) {
                    isCompetition = false;
                    iv02.setImageResource(R.mipmap.bt_over);

                    competitionAdapter.setNowRank(-1);
                    //算分
                    int maxPos = 0;
                    int maxScore = 0;
                    for (int i = 0; i < competitionAdapter.getData().size(); i++) {
                        if (competitionAdapter.getData().get(i).getScore() > maxScore) {
                            maxPos = i;
                            maxScore = competitionAdapter.getData().get(i).getScore();
                        }
                    }

                    if (maxPos - 1 >= 0) {
                        recyclerView.smoothScrollToPosition(maxPos - 1);
                    } else {
                        recyclerView.smoothScrollToPosition(maxPos);
                    }


                    competitionAdapter.setMaxPos(maxPos);
                    competitionAdapter.notifyDataSetChanged();

                } else {
                    isCompetition = true;
                    //开始的时候重置所有参数
                    recLen = 0;
                    int size = competitionAdapter.getData().size();
                    for (int i = 0; i < size; i++) {
                        Competition competition = competitionAdapter.getData().get(i);
                        competition.setStop(false);
                        competition.setScore(0);
                        competition.setRank(-1);
                        competition.setTime(String.valueOf(0), String.valueOf(0), String.valueOf(0), String.valueOf(0));
                    }

                    competitionAdapter.setMaxPos(-1);
                    competitionAdapter.setNowRank(-1);
                    competitionAdapter.notifyDataSetChanged();
                    iv02.setImageResource(R.mipmap.bt_begain);
                    handler.postDelayed(runnable, SECOND);
                }
            }
        });


        ivLeft = view.findViewById(R.id.ivLeft);
        ivRight = view.findViewById(R.id.ivRight);

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i("" + linearLayoutManager.findFirstVisibleItemPosition());
                int lastP = linearLayoutManager.findFirstVisibleItemPosition();
                lastP = lastP - 1;
                if (lastP >= 0) {
                    recyclerView.smoothScrollToPosition(lastP);
                }


            }
        });
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogUtil.i("" + linearLayoutManager.findLastVisibleItemPosition());
                int lastP = linearLayoutManager.findLastVisibleItemPosition();
                lastP = lastP + 1;

                if (lastP < competitionAdapter.getData().size()) {
                    recyclerView.smoothScrollToPosition(lastP);
                }

            }
        });

        ivReduce = view.findViewById(R.id.ivReduce);
        ivAdd = view.findViewById(R.id.ivAdd);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (competitionAdapter.getData().size() < 10) {
                    competitionAdapter.addData(new Competition(0));
                    recyclerView.smoothScrollToPosition(competitionAdapter.getData().size() - 1);
                } else {
                }

            }
        });

        ivReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (competitionAdapter.getData().size() > 1) {
                    competitionAdapter.remove(competitionAdapter.getData().size() - 1);
                    recyclerView.smoothScrollToPosition(competitionAdapter.getData().size() - 1);
                }

            }
        });
        ivBottom01 = view.findViewById(R.id.ivBottom01);
        ivBottom02 = view.findViewById(R.id.ivBottom02);

        ivBottom02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                isCompetition = false;
                popupWindow.dismiss();
            }
        });


        //缩小
        ivBottom01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                narrowListener.onNarrowListener(NarrowListener.NARROW_POP_TYPE_02);
                popupWindow.dismiss();
            }
        });


        isCompetition = false;
        iv02.setImageResource(R.mipmap.bt_over);


    }

    public void setNarrowListener(NarrowListener narrowListener) {
        this.narrowListener = narrowListener;
    }
    public void onDestroy() {
        isCompetition=false;
    }
    public void showAtLocation(View view) {

        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (location[1] < 0) {
            location[1] = 0;
        }

        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 60);

    }
}
