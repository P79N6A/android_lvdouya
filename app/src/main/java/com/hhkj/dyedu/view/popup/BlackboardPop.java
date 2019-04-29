package com.hhkj.dyedu.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.callback.NarrowListener;
import com.hhkj.dyedu.view.MyPopupWindow;
import com.hhkj.dyedu.view.ToolsPop;
import com.hhkj.dyedu.view.scrawldemo.config.AnnotationConfig;
import com.hhkj.dyedu.view.scrawldemo.widget.ScrawlBoardView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by zangyi_shuai_ge on 2018/5/30
 * 黑板
 */

public class BlackboardPop extends ToolsPop {

    //画笔颜色
    private ImageView ivColor01, ivColor02, ivColor03, ivColor04, ivColor05, ivColor06;
    //画笔尺寸
    private ImageView ivPaintSize01,ivPaintSize02,ivPaintSize03;
    //画板
    private ScrawlBoardView scrawlBoardView;
    //底部4个菜单按钮
    private ImageView ivBottom01,ivBottom02,ivBottom03,ivBottom04,ivBottom05;
    //2个工具栏容器
    private LinearLayout layout01,layout02;
    //2个容器关闭按钮
    private ImageView ivClose02,ivClose01;
    //橡皮尺寸
    private ImageView ivEraserPaint01,ivEraserPaint02,ivEraserPaint03;



    private NarrowListener narrowListener;

    public void setNarrowListener(NarrowListener narrowListener) {
        this.narrowListener = narrowListener;
    }

    private boolean havePadding;

    public BlackboardPop(Context context, final boolean havePadding) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_blackboard, null);
        int width;
        int height;
        if(havePadding){
            width = MATCH_PARENT;
            height = ScreenUtils.getScreenHeight()-90;
        }else {
             width = MATCH_PARENT;
             height = MATCH_PARENT;
        }

        popupWindow = new MyPopupWindow(view, width, height, false);

        FrameLayout layoutMain=view.findViewById(R.id.layoutMain);

        this.havePadding=havePadding;
        //画板
        scrawlBoardView = view.findViewById(R.id.board_view);
        if(!havePadding){
            view.findViewById(R.id.ivbg).setVisibility(View.GONE);
            layoutMain.setPadding(0, 0, 0, 0);
        }else {
            layoutMain.setPadding(0,0, 0, 0);
            ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) scrawlBoardView.getLayoutParams();
            params.setMargins(70,70,70,70);
            scrawlBoardView.requestLayout();
        }

        ivColor01 = view.findViewById(R.id.ivColor01);
        ivColor02 = view.findViewById(R.id.ivColor02);
        ivColor03 = view.findViewById(R.id.ivColor03);
        ivColor04 = view.findViewById(R.id.ivColor04);
        ivColor05 = view.findViewById(R.id.ivColor05);
        ivColor06 = view.findViewById(R.id.ivColor06);



        //红色
        ivColor01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUI();
                ivColor01.setImageResource(R.mipmap.red_black);
                scrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Red);
            }
        });

        //橘色
        ivColor02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUI();
                ivColor02.setImageResource(R.mipmap.yellow_black);
                scrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Orange);
            }
        });

        //绿色
        ivColor03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUI();
                ivColor03.setImageResource(R.mipmap.green_black);
                scrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Green);
            }
        });
        //白色
        ivColor04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUI();
                ivColor04.setImageResource(R.mipmap.white_black);
                scrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_White);
            }
        });
        //紫色
        ivColor05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUI();
                ivColor05.setImageResource(R.mipmap.zise_black);
                scrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Purple);
            }
        });
        //蓝色
        ivColor06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUI();
                ivColor06.setImageResource(R.mipmap.purple_black);
                scrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Blue);
            }
        });
        //
        ivPaintSize01=view.findViewById(R.id.ivPaintSize01);
        ivPaintSize02=view.findViewById(R.id.ivPaintSize02);
        ivPaintSize03=view.findViewById(R.id.ivPaintSize03);

        ivPaintSize01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPaintSizeUi();
                ivPaintSize01.setImageResource(R.mipmap.l_black);
                scrawlBoardView.setPaintSize(SizeUtils.dp2px(4));

            }
        });

        ivPaintSize02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPaintSizeUi();
                ivPaintSize02.setImageResource(R.mipmap.l_black);
                scrawlBoardView.setPaintSize(SizeUtils.dp2px(8));
            }
        });
        ivPaintSize03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPaintSizeUi();
                ivPaintSize03.setImageResource(R.mipmap.l_black);
                scrawlBoardView.setPaintSize(SizeUtils.dp2px(12));
            }
        });

        layout01 = view.findViewById(R.id.layout01);
        layout02 = view.findViewById(R.id.layout02);

        ivBottom01 = view.findViewById(R.id.ivBottom01);
        ivBottom02 = view.findViewById(R.id.ivBottom02);
        ivBottom03 = view.findViewById(R.id.ivBottom03);
        ivBottom04 = view.findViewById(R.id.ivBottom04);
        ivBottom05 = view.findViewById(R.id.ivBottom05);
        //关闭画板
        ivBottom02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        //清屏
        ivBottom05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               scrawlBoardView.clearScrawlBoard();
            }
        });
        //缩小
        ivBottom01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BlackboardPop.this.havePadding){
                    narrowListener.onNarrowListener(NarrowListener.NARROW_POP_TYPE_03);
                }else {
                    narrowListener.onNarrowListener(NarrowListener.NARROW_POP_TYPE_04);
                }
                dismiss();
            }
        });

        //使用画笔
        ivBottom03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scrawlBoardView.setEraser(false);

                layout01.setVisibility(View.VISIBLE);
                layout02.setVisibility(View.GONE);

                ivBottom03.setVisibility(View.GONE);
                ivBottom04.setVisibility(View.VISIBLE);
            }
        });

        //使用橡皮
        ivBottom04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrawlBoardView.setEraser(true);

                layout01.setVisibility(View.GONE);
                layout02.setVisibility(View.VISIBLE);

                ivBottom04.setVisibility(View.GONE);
                ivBottom03.setVisibility(View.VISIBLE);
            }
        });

       ivClose01 = view.findViewById(R.id.ivClose01);
       ivClose02 = view.findViewById(R.id.ivClose02);

        ivClose01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout01.setVisibility(View.GONE);
                ivBottom03.setVisibility(View.VISIBLE);
            }
        });

        ivClose02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout02.setVisibility(View.GONE);
                ivBottom04.setVisibility(View.VISIBLE);
            }
        });

        ivEraserPaint01 = view.findViewById(R.id.ivEraserPaint01);
        ivEraserPaint02 = view.findViewById(R.id.ivEraserPaint02);
        ivEraserPaint03 = view.findViewById(R.id.ivEraserPaint03);


        ivEraserPaint01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initEraserPaintSizeUi();
                ivEraserPaint01.setImageResource(R.mipmap.l_black);
                scrawlBoardView.setEraserPaintSize(SizeUtils.dp2px(12));
            }
        });
        ivEraserPaint02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initEraserPaintSizeUi();
                ivEraserPaint02.setImageResource(R.mipmap.l_black);
                scrawlBoardView.setEraserPaintSize(SizeUtils.dp2px(16));
            }
        });
        ivEraserPaint03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initEraserPaintSizeUi();
                ivEraserPaint03.setImageResource(R.mipmap.l_black);
                scrawlBoardView.setEraserPaintSize(SizeUtils.dp2px(18));
            }
        });
        initSize();



    }

    /**
     * 初始化画板画笔颜色 以及 画笔大小
     */
    private void initSize() {

        //画笔颜色
        initUI();
        ivColor01.setImageResource(R.mipmap.red_black);
        scrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Red);


        //画笔大小
        initPaintSizeUi();
        ivPaintSize01.setImageResource(R.mipmap.l_black);
        scrawlBoardView.setPaintSize(SizeUtils.dp2px(4));

        //橡皮大小
        initEraserPaintSizeUi();
        ivEraserPaint01.setImageResource(R.mipmap.l_black);
        scrawlBoardView.setEraserPaintSize(SizeUtils.dp2px(12));


        //使用画笔
        scrawlBoardView.setEraser(false);
        layout01.setVisibility(View.VISIBLE);
        layout02.setVisibility(View.GONE);
        ivBottom03.setVisibility(View.GONE);
        ivBottom04.setVisibility(View.VISIBLE);


    }

    private void initPaintSizeUi() {

        ivPaintSize01.setImageResource(R.mipmap.l_white);
        ivPaintSize02.setImageResource(R.mipmap.l_white);
        ivPaintSize03.setImageResource(R.mipmap.l_white);
    }
    private void initEraserPaintSizeUi() {

        ivEraserPaint01.setImageResource(R.mipmap.l_white);
        ivEraserPaint02.setImageResource(R.mipmap.l_white);
        ivEraserPaint03.setImageResource(R.mipmap.l_white);
    }
    private void initUI() {

        ivColor01.setImageResource(R.mipmap.red_white);
        ivColor02.setImageResource(R.mipmap.yellow_white);
        ivColor03.setImageResource(R.mipmap.green_white);
        ivColor04.setImageResource(R.mipmap.white_white);
        ivColor05.setImageResource(R.mipmap.zise_white);
        ivColor06.setImageResource(R.mipmap.purple_white);


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
        if(havePadding){
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 60);
        }else {
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }

}
