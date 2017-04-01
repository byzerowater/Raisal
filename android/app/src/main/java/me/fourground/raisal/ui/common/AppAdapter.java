package me.fourground.raisal.ui.common;

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
 * 앱 평가 Adapter
 */
public class AppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_EVALUATING = 1;
    private static final int VIEW_TYPE_END = 2;

    interface OnAppItemClickListener {
        void onAppItemClick(String appItem);
    }

    /**
     * 앱 평가 데이터들
     */
    private List<String> mAppDatas;
    /**
     * 앱 평가 아이템 클릭 리스너
     */
    private OnAppItemClickListener mOnOrderItemClickListener;

    /**
     * 앱 평가 Adapter
     */
    @Inject
    public AppAdapter() {
        this.mAppDatas = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_EVALUATING) {
            viewHolder = new AppEvaluatingHolder(
                    inflate.inflate(R.layout.item_app_evaluating, parent, false));
        } else if (viewType == VIEW_TYPE_END) {
            viewHolder = new AppEndHolder(
                    inflate.inflate(R.layout.item_app_end, parent, false));
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
        String s = mAppDatas.get(position);
        holder.itemView.setOnClickListener(
                view -> {
                    if (mOnOrderItemClickListener != null) {
                        mOnOrderItemClickListener.onAppItemClick(s);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return ListUtil.getListCount(mAppDatas);
    }

    public String getItem(int position) {
        int itemCount = getItemCount();
        String item = null;

        if (itemCount > position && position < 0) {
            item = mAppDatas.get(position);
        }

        return item;
    }

    public void setAppDatas(List<String> appDatas) {
        mAppDatas = appDatas;
    }

    public void setOnOrderItemClickListener(OnAppItemClickListener onOrderItemClickListener) {
        mOnOrderItemClickListener = onOrderItemClickListener;
    }


    /**
     * 리뷰중 Holder
     */
    static class AppEvaluatingHolder extends ViewHolder {
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

        public AppEvaluatingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 리뷰 종료 Holder
     */
    static class AppEndHolder extends ViewHolder {
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

        public AppEndHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}