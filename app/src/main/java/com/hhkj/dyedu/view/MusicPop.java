package com.hhkj.dyedu.view;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hhkj.dyedu.GlobalVariable;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.SongAdapter;
import com.hhkj.dyedu.bean.model.Song;
import com.hhkj.dyedu.utils.AudioUtils;
import com.hhkj.dyedu.utils.ImageLoaderUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/5/30
 * 表扬鼓励
 */

public class MusicPop extends ToolsPop {

    Handler handler = new Handler();
    View view;
    private MediaPlayer mMediaPlayer;
    private ImageView ivPlay;
    private SeekBar seekBar01, seekBar02;
    private int maxVolume = 50; // 最大音量值
    private int curVolume = 20; // 当前音量值
    private int stepVolume = 0; // 每次调整的音量幅度
    private AudioManager audioMgr = null; // Audio管理器，用了控制音
    private TextView time1, time2;
    private ImageView iv01, iv02;
    private boolean isNoV = false;
    private int lastV;
    private boolean isStopTimer = true;
    private boolean isSeek = false;
    private TextView tvInfo01, tvInfo02;
    private ImageView ivHead;
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private ImageView ivRight01, ivRight02;
    private ImageView ivLeft, ivRight;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isStopTimer) {


                int duration = mMediaPlayer.getDuration() / 1000;
                int currentPosition = mMediaPlayer.getCurrentPosition() / 1000;//秒


                int a = duration / 60;
                int b = duration % 60;

                if (b < 10) {
                    time2.setText(a + ":0" + b);
                } else {
                    time2.setText(a + ":" + b);
                }


                int c = currentPosition / 60;
                int d = currentPosition % 60;
                if (d < 10) {
                    time1.setText(c + ":0" + d);
                } else {
                    time1.setText(c + ":" + d);
                }
                if (!isSeek) {
                    seekBar02.setProgress(currentPosition);
                }
                handler.postDelayed(this, GlobalVariable.SECOND);
            }

        }
    };
    private boolean isShowList = false;
    private int nowPalyPosition = 0;
    private Context context;
    private int offsetX = 500;
    private int offsetY = 500;

    public MusicPop(final Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.pop_music, null);
        this.context = context;
        int width = 680;
        int height = 460;


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


        final List<Song> list = AudioUtils.getAllSongs(context);
//        for (int i = 0; i <list.size() ; i++) {
//            LogUtil.i("个"+list.get(i).toString());
//        }


        popupWindow = new MyPopupWindow(view, width, height, false);
        songAdapter = new SongAdapter();
        songAdapter.replaceData(list);
        songAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                nowPalyPosition = position;
                initSong(songAdapter.getData().get(position));

                //切换为播放状态
                isStopTimer = false;
                handler.postDelayed(runnable, GlobalVariable.SECOND);
                mMediaPlayer.start();
                ivPlay.setImageResource(R.mipmap.music_04);

                isShowList = false;
                recyclerView.setVisibility(View.GONE);
                ivRight02.setImageResource(R.mipmap.musix_03);

            }
        });

        mMediaPlayer = new MediaPlayer();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(songAdapter);
        ivRight = view.findViewById(R.id.ivRight);
        ivLeft = view.findViewById(R.id.ivLeft);

        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = nowPalyPosition + 1;
                if (a < songAdapter.getData().size()) {


                    nowPalyPosition = a;
                    initSong(songAdapter.getData().get(nowPalyPosition));
                    isStopTimer = false;
                    handler.postDelayed(runnable, GlobalVariable.SECOND);
                    mMediaPlayer.start();
                    ivPlay.setImageResource(R.mipmap.music_04);

                    isShowList = false;
                    recyclerView.setVisibility(View.GONE);
                    ivRight02.setImageResource(R.mipmap.musix_03);
                } else {
                    com.zuoni.common.utils.ToastUtils.showToast(context, "没有下一首了");
                }
            }
        });

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = nowPalyPosition - 1;
                if (a >= 0) {
                    nowPalyPosition = a;
                    initSong(songAdapter.getData().get(nowPalyPosition));
                    isStopTimer = false;
                    handler.postDelayed(runnable, GlobalVariable.SECOND);
                    mMediaPlayer.start();
                    ivPlay.setImageResource(R.mipmap.music_04);

                    isShowList = false;
                    recyclerView.setVisibility(View.GONE);
                    ivRight02.setImageResource(R.mipmap.musix_03);
                } else {
                    com.zuoni.common.utils.ToastUtils.showToast(context, "没有上一首了");
                }
            }
        });

        time1 = view.findViewById(R.id.time1);
        time2 = view.findViewById(R.id.time2);
        ivHead = view.findViewById(R.id.ivHead);
        ivPlay = view.findViewById(R.id.ivPlay);

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMediaPlayer.isPlaying()) {
                    //如果正在播放
                    isStopTimer = true;
                    mMediaPlayer.pause();
                    ivPlay.setImageResource(R.mipmap.music_08);
                } else {
                    isStopTimer = false;
                    handler.postDelayed(runnable, GlobalVariable.SECOND);
                    mMediaPlayer.start();
                    ivPlay.setImageResource(R.mipmap.music_04);
                }


            }
        });

        ivRight01 = view.findViewById(R.id.ivRight01);
        ivRight02 = view.findViewById(R.id.ivRight02);


        ivRight01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        ivRight02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowList) {
                    isShowList = false;
                    recyclerView.setVisibility(View.GONE);
                    ivRight02.setImageResource(R.mipmap.musix_03);

                } else {
                    isShowList = true;
                    recyclerView.setVisibility(View.VISIBLE);
                    ivRight02.setImageResource(R.mipmap.music);
                }
            }
        });
        seekBar01 = view.findViewById(R.id.seekBar01);
        seekBar02 = view.findViewById(R.id.seekBar02);
        tvInfo01 = view.findViewById(R.id.tvInfo01);
        tvInfo02 = view.findViewById(R.id.tvInfo02);

        seekBar02.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeek = false;
                mMediaPlayer.seekTo(seekBar.getProgress() * 1000);
            }
        });

        iv01 = view.findViewById(R.id.iv01);
        iv02 = view.findViewById(R.id.iv02);
//
//        iv01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                seekBar01.setProgress(0);
//            }
//        });
//

        //静音
        iv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNoV) {
                    //静音状态下
                    isNoV = false;
                    seekBar01.setProgress(lastV);
                    iv02.setImageResource(R.mipmap.volumeicon_max);
                } else {
                    //正常状态下
                    isNoV = true;
                    //保存上次音量
                    lastV = curVolume;
                    seekBar01.setProgress(0);
                    iv02.setImageResource(R.mipmap.silent);
                }
            }
        });


        seekBar01.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                isNoV = false;
                iv02.setImageResource(R.mipmap.volumeicon_max);

                curVolume = progress;
                audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_PLAY_SOUND);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        audioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        // 获取最大音乐音量
        maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 初始化音量大概为最大音量的1/2
        curVolume = maxVolume / 2;
        // 每次调整的音量大概为最大音量的1/6
        stepVolume = maxVolume / 6;


        seekBar01.setMax(maxVolume);
        seekBar01.setProgress(curVolume);

        if (list.size() > 0) {
            Song song = list.get(0);
            initSong(song);
        }

    }

    private void initSong(Song song) {
        try {

            mMediaPlayer.reset();

            mMediaPlayer.setDataSource(song.getFileUrl());

            mMediaPlayer.prepare();//初始化播放器MediaPlayer

            seekBar02.setProgress(0);

            seekBar02.setMax(mMediaPlayer.getDuration() / 1000);

            String a1 = song.getAlbumId();
            ImageLoaderUtils.setImage2(AudioUtils.getAlbumArt2(a1, context), ivHead);

            tvInfo01.setText(song.getTitle());

            tvInfo02.setText(song.getSinger());

            time1.setText("00:00");


            int duration = mMediaPlayer.getDuration() / 1000;

            int a = duration / 60;
            int b = duration % 60;


            if (b < 10) {
                time2.setText(a + ":0" + b);
            } else {
                time2.setText(a + ":" + b);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void showAtLocation(View view) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAtLocation(view, Gravity.TOP | Gravity.LEFT, SizeUtils.dp2px(14), SizeUtils.dp2px(106));
            popupWindow.update(offsetX, offsetY, -1, -1, true);
        }
    }
}
