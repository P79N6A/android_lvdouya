package com.hhkj.dyedu.ui.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hhkj.dyedu.R;
import com.hhkj.dyedu.ui.activity.base.BaseTitleActivity;
import com.jaeger.library.StatusBarUtil;
import com.sahooz.library.Country;
import com.sahooz.library.LetterHolder;
import com.sahooz.library.PyAdapter;
import com.sahooz.library.PyEntity;
import com.sahooz.library.SideBar;
import com.sahooz.library.VH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/7/6
 *
 * @author zangyi 767450430
 * 国家选择器
 */
public class CountryPickActivity extends BaseTitleActivity {
    
    /**
     * 选择国家
     */
    public static final int CHOOSE_COUNTRY = 1;
    
    /**
     * 选择区号
     */
    public static final int CHOOSE_AREA_CODE = 2;
    
    private ArrayList<Country> selectedCountries = new ArrayList<>();
    
    private ArrayList<Country> allCountries = new ArrayList<>();
    
    @Override
    public int setLayoutId() {
        return R.layout.activity_country_pick;
    }
    
    TextView tvLetter;
    LinearLayoutManager manager;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, layoutTop);
        setTitle(getString(R.string.title_choose));
        RecyclerView rvPick = (RecyclerView) findViewById(com.sahooz.library.R.id.rv_pick);
        SideBar side = (SideBar) findViewById(com.sahooz.library.R.id.side);
        EditText etSearch = (EditText) findViewById(com.sahooz.library.R.id.et_search);
        tvLetter = (TextView) findViewById(com.sahooz.library.R.id.tv_letter);
        allCountries.clear();
        allCountries.addAll(Country.getAll(this, null));
        selectedCountries.clear();
        selectedCountries.addAll(allCountries);
        final CAdapter adapter = new CAdapter(selectedCountries);
        rvPick.setAdapter(adapter);
        manager = new LinearLayoutManager(this);
        rvPick.setLayoutManager(manager);
        rvPick.setAdapter(adapter);
        rvPick.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                selectedCountries.clear();
                for (Country country : allCountries) {
                    if (country.name.toLowerCase().contains(string.toLowerCase()))
                        selectedCountries.add(country);
                }
                adapter.update(selectedCountries);
            }
        });
        
        side.addIndex("#", side.indexes.size());
        side.setOnLetterChangeListener(new SideBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                tvLetter.setVisibility(View.VISIBLE);
                tvLetter.setText(letter);
                int position = adapter.getLetterPosition(letter);
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
            
            @Override
            public void onReset() {
                tvLetter.setVisibility(View.GONE);
            }
        });
    }
    
    
    class CAdapter extends PyAdapter<RecyclerView.ViewHolder> {
        
        public CAdapter(List<? extends PyEntity> entities) {
            super(entities);
        }
        
        @Override
        public RecyclerView.ViewHolder onCreateLetterHolder(ViewGroup parent, int viewType) {
            return new LetterHolder(getLayoutInflater().inflate(com.sahooz.library.R.layout.item_letter, parent, false));
        }
        
        @Override
        public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
            return new VH(getLayoutInflater().inflate(com.sahooz.library.R.layout.item_country_large_padding, parent, false));
        }
        
        @Override
        public void onBindHolder(RecyclerView.ViewHolder holder, PyEntity entity, int position) {
            VH vh = (VH) holder;
            final Country country = (Country) entity;
            vh.ivFlag.setImageResource(country.flag);
            vh.tvName.setText(country.name);
            vh.tvCode.setText("+" + country.code);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent data = new Intent();
                    data.putExtra("country", country.toJson());
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }
            });
            
        }
        
        @Override
        public void onBindLetterHolder(RecyclerView.ViewHolder holder, LetterEntity entity, int position) {
            ((LetterHolder) holder).textView.setText(entity.letter.toUpperCase());
        }
    }
}
