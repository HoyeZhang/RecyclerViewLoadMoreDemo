package com.aj.recyclerviewloadmoredemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aj.recyclerviewloadmoredemo.recyclerview.CommonAdapter;
import com.aj.recyclerviewloadmoredemo.recyclerview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int lastVisibleItem;
    private int number = 0;
    private List<String> mDatas;
    private List<String> mLordMoreDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_home);
        mDatas = new ArrayList<>();
        initData();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        final CommonAdapter<String> stringCommonAdapter = new CommonAdapter<String>(this, R.layout.item_list, R.layout.recycler_footview_layout,
                mDatas) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.id_item_list_title, s);
            }

            @Override
            public void footConvert(ViewHolder holder) {
            //对加载中view的操作 当不需要加载功能时无任何处理即可
            }
        };
        mRecyclerView.setAdapter(stringCommonAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //  判断是否需要加载更多  当需要时最后一个可视item + 1 等于 item的数量
                    if (stringCommonAdapter.getIsLoadMore() == true && lastVisibleItem + 1 == stringCommonAdapter.getItemCount()) {
                        //加载更多的获取数据具体操作
                        getData();
                        stringCommonAdapter.addDataList(mLordMoreDatas);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

            }
        });

    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            String a = new String(number++ + "");
            mDatas.add(a);
        }

    }

    public List<String> getData() {
        if (mLordMoreDatas != null) {

            mLordMoreDatas.clear();
        }
        for (int i = 0; i < 5; i++) {
            String a = new String(number++ + "");
            mLordMoreDatas.add(a);
        }

        return mLordMoreDatas;
    }
}
