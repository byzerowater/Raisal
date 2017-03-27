package me.fourground.raisal.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fourground.raisal.R;
import me.fourground.raisal.util.ListUtil;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 리뷰 Adapter
 */
public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_EVALUATING = 1;
    private static final int VIEW_TYPE_END = 2;

    interface OnReviewItemClickListener {
        void onReviewItemClick(String reviewItem);
    }

    /**
     * 리뷰 데이터들
     */
    private List<String> mReviewDatas;
    /**
     * 리뷰 아이템 클릭 리스너
     */
    private OnReviewItemClickListener mOnOrderItemClickListener;

    /**
     * 리뷰 Adapter
     */
    @Inject
    public ReviewAdapter() {
        this.mReviewDatas = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_EVALUATING) {
            viewHolder = new ReviewEvaluatingHolder(
                    inflate.inflate(R.layout.item_review_evaluating, parent, false));
        } else if (viewType == VIEW_TYPE_END) {
            viewHolder = new ReviewEndHolder(
                    inflate.inflate(R.layout.item_review_end, parent, false));
        }

        return viewHolder;

    }

    @Override
    public int getItemViewType(int position) {
        String item = getItem(position);
        int type = VIEW_TYPE_EVALUATING;
        return type;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        String s = mReviewDatas.get(position);
        holder.itemView.setOnClickListener(
                view -> {
                    if (mOnOrderItemClickListener != null) {
                        mOnOrderItemClickListener.onReviewItemClick(s);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return ListUtil.getListCount(mReviewDatas);
    }

    public String getItem(int position) {
        int itemCount = getItemCount();
        String item = null;

        if (itemCount > position && position < 0) {
            item = mReviewDatas.get(position);
        }

        return item;
    }

    public void setReviewDatas(List<String> reviewDatas) {
        mReviewDatas = reviewDatas;
    }

    public void setOnOrderItemClickListener(OnReviewItemClickListener onOrderItemClickListener) {
        mOnOrderItemClickListener = onOrderItemClickListener;
    }


    /**
     * 리뷰중 Holder
     */
    class ReviewEvaluatingHolder extends ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_store)
        TextView mTvStore;
        @BindView(R.id.tv_state)
        TextView mTvState;
        @BindView(R.id.tv_review_count)
        TextView mTvReviewCount;
        @BindView(R.id.btn_join_review)
        Button mBtnJoinReview;

        public ReviewEvaluatingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 리뷰 종료 Holder
     */
    class ReviewEndHolder extends ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_store)
        TextView mTvStore;
        @BindView(R.id.tv_state)
        TextView mTvState;
        @BindView(R.id.rb_average)
        RatingBar mRbAverage;
        @BindView(R.id.tv_average)
        TextView mTvAverage;
        @BindView(R.id.tv_entry)
        TextView mTvEntry;
        @BindView(R.id.tv_date)
        TextView mTvDate;

        public ReviewEndHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}