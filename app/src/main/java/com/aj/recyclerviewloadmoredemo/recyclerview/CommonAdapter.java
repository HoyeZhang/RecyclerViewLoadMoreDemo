package com.aj.recyclerviewloadmoredemo.recyclerview;

/**
 * @author zhy
 * @time 2018/4/26.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder>
{

    protected Context mContext;
    protected int mLayoutId;
    protected int mFootLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int normalType = 1;
    private int footType = 2;
    protected boolean mIsLoadMore= false;


    public CommonAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    public CommonAdapter(Context context, int normalLayoutId,int footLayoutId,  List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = normalLayoutId;
        mFootLayoutId = footLayoutId;
        mDatas = datas;
        mIsLoadMore = true;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        if (viewType == normalType) {
            ViewHolder viewHolder = ViewHolder.createViewHolder(mContext, parent, mLayoutId);
            return viewHolder;
        }else {
            ViewHolder viewHolder = ViewHolder.createViewHolder(mContext, parent,mFootLayoutId);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
    //    holder.updatePosition(position);
        if (holder.getItemViewType() == normalType){
            convert(holder, mDatas.get(position));
        }else {
           footConvert(holder);
        }


    }

    public abstract void convert(ViewHolder holder, T t);

    public abstract void footConvert(ViewHolder holder);


    @Override
    public int getItemCount()
    {
        if (mIsLoadMore ==true){
            return mDatas.size()+1;
        }else {
            return mDatas.size();
        }

    }

    /*
    加载更多
     */
    public void addDataList(List<T>  addDatas) {

        if (addDatas != null) {
            mDatas.addAll(addDatas);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsLoadMore==true){
            if (position == getItemCount() - 1) {
                return footType;
            }else
            {
                return normalType;
            }
        }else {
            return normalType;
        }

    }

    public boolean getIsLoadMore(){
        return mIsLoadMore;
    }
}