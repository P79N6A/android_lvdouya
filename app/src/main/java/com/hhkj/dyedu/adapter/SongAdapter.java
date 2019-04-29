package com.hhkj.dyedu.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hhkj.dyedu.R;
import com.hhkj.dyedu.adapter.base.MyBaseQuickAdapter;
import com.hhkj.dyedu.bean.model.Song;

/**
 * Created by zangyi_shuai_ge on 2018/5/3
 */

public class SongAdapter extends MyBaseQuickAdapter<Song, BaseViewHolder> {
    public SongAdapter() {
        super(R.layout.rv_song_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Song item) {
       helper.setText(R.id.tvInfo,item.getTitle()+" -- "+item.getSinger());
    }

}
