package com.hhkj.dyedu.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.bean.model.Question;
import com.hhkj.dyedu.callback.NarrowListener;
import com.hhkj.dyedu.view.MyPopupWindow;
import com.hhkj.dyedu.view.ToolsPop;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by zangyi_shuai_ge on 2018/7/26
 *
 * @author zangyi 767450430
 * 提问环节
 */
public class QuestionPop extends ToolsPop {


    private NarrowListener narrowListener;
    private ImageView ivLeft, ivRight;
    private View.OnClickListener smallAppOnClickListener;
    private ImageView ivBottom01, ivBottom02;
    private List<Question> questions;
    private Context context;
    private TextView tv01, tv02;
    private int p = 0;

    public QuestionPop(Context context, final List<Question> questions) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_question, null);

        this.context = context;
        this.questions = questions;

        int width = MATCH_PARENT;
        int height = ScreenUtils.getScreenHeight()-90;
//        int width = 1610;
//        int height = 910;
        popupWindow = new MyPopupWindow(view, width, height, false);

        ivLeft = view.findViewById(R.id.ivLeft);
        ivRight = view.findViewById(R.id.ivRight);

        tv01 = view.findViewById(R.id.tv01);
        tv02 = view.findViewById(R.id.tv02);

        if (questions.size() > 0) {
            p = 0;
            tv01.setText(questions.get(0).getTitle());
            tv02.setText(questions.get(0).getContent());
        }

        havePage();


        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = p - 1;
                if (a >= 0 && QuestionPop.this.questions.size() > 0) {
                    p = a;
                    tv01.setText(QuestionPop.this.questions.get(p).getTitle());
                    tv02.setText(QuestionPop.this.questions.get(p).getContent());
                }

                havePage();
            }
        });
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = p + 1;
                if (a < QuestionPop.this.questions.size()) {
                    p = a;
                    tv01.setText(QuestionPop.this.questions.get(p).getTitle());
                    tv02.setText(QuestionPop.this.questions.get(p).getContent());
                }
                havePage();

            }
        });

        ivBottom01 = view.findViewById(R.id.ivBottom01);
        ivBottom02 = view.findViewById(R.id.ivBottom02);

        //关闭画板
        ivBottom02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //缩小
        ivBottom01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                narrowListener.onNarrowListener(NarrowListener.NARROW_POP_TYPE_01);
                popupWindow.dismiss();
            }
        });


    }

    private void havePage() {

        //判断有没有上一页
        if (p - 1 < 0) {
            //没有上一页
            ivLeft.setImageResource(R.mipmap.question_02);
        } else {
            ivLeft.setImageResource(R.mipmap.competition_06);
        }

        //判断有没有上一页
        if (p + 1 < QuestionPop.this.questions.size()) {
            //有下一页
            ivRight.setImageResource(R.mipmap.competition_07);
        } else {
            ivRight.setImageResource(R.mipmap.question_03);
        }
    }

    public void setNarrowListener(NarrowListener narrowListener) {
        this.narrowListener = narrowListener;
    }

    public void setSmallAppOnClickListener(View.OnClickListener smallAppOnClickListener) {
        this.smallAppOnClickListener = smallAppOnClickListener;
    }

    public void showAtLocation(View view) {

        if (questions.size() == 0) {
            com.zuoni.common.utils.ToastUtils.showToast(context, "该课程无提问环节");
            return;
        }

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
