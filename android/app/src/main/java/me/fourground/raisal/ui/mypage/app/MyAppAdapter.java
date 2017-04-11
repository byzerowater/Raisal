package me.fourground.raisal.ui.mypage.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fourground.raisal.R;
import me.fourground.raisal.util.ListUtil;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 리뷰 Adapter
 */
public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.MyAppHolder> {

    /**
     * 리뷰 데이터들
     */
    private List<String> mAppDatas;

    /**
     * 리뷰 Adapter
     */
    @Inject
    public MyAppAdapter() {
        this.mAppDatas = new ArrayList<>();
    }


    @Override
    public MyAppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        return new MyAppHolder(inflate.inflate(R.layout.item_my_app, parent, false));

    }

    @Override
    public void onBindViewHolder(MyAppHolder holder, int position) {
        Context context = holder.itemView.getContext();
        String s = mAppDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return ListUtil.getListCount(mAppDatas);
    }

    public String getItem(int position) {
        int itemCount = getItemCount();
        String item = null;

        if (itemCount > position && position >= 0) {
            item = mAppDatas.get(position);
        }

        return item;
    }

    public void addReviewDatas(List<String> reviewDatas) {
        mAppDatas.addAll(reviewDatas);
    }

    static class MyAppHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_store)
        TextView mTvStore;
        @BindView(R.id.tv_state)
        TextView mTvState;
        @BindView(R.id.tv_empty_text)
        TextView mTvEmptyText;
        @BindView(R.id.rb_average)
        RatingBar mRbAverage;
        @BindView(R.id.tv_average)
        TextView mTvAverage;
        @BindView(R.id.tv_review_count)
        TextView mTvReviewCount;
        @BindView(R.id.ll_average)
        LinearLayout mLlAverage;
        @BindView(R.id.tv_date)
        TextView mTvDate;

        MyAppHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}