package me.fourground.raisal.ui.mypage.review;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ReviewHolder> {

    /**
     * 리뷰 데이터들
     */
    private List<String> mReviewDatas;

    /**
     * 리뷰 Adapter
     */
    @Inject
    public MyReviewAdapter() {
        this.mReviewDatas = new ArrayList<>();
    }


    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        return new ReviewHolder(inflate.inflate(R.layout.item_my_review, parent, false));

    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        String s = mReviewDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return ListUtil.getListCount(mReviewDatas);
    }

    public String getItem(int position) {
        int itemCount = getItemCount();
        String item = null;

        if (itemCount > position && position >= 0) {
            item = mReviewDatas.get(position);
        }

        return item;
    }

    public void addReviewDatas(List<String> reviewDatas) {
        mReviewDatas.addAll(reviewDatas);
    }

    static class ReviewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_store)
        TextView mTvStore;
        @BindView(R.id.tv_state)
        TextView mTvState;
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.tv_review)
        TextView mTvReview;
        @BindView(R.id.tv_design)
        TextView mTvDesign;
        @BindView(R.id.tv_useful)
        TextView mTvUseful;
        @BindView(R.id.tv_contents)
        TextView mTvContents;
        @BindView(R.id.tv_satisfaction)
        TextView mTvSatisfaction;

        ReviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}