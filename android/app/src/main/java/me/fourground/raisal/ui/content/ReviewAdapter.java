package me.fourground.raisal.ui.content;

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
import me.fourground.raisal.data.model.PointData;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.util.ListUtil;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 리뷰 Adapter
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    /**
     * 리뷰 데이터들
     */
    private List<ReviewData> mReviewDatas;

    /**
     * 리뷰 Adapter
     */
    @Inject
    public ReviewAdapter() {
        this.mReviewDatas = new ArrayList<>();
    }


    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        return new ReviewHolder(inflate.inflate(R.layout.item_review, parent, false));

    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        ReviewData reviewData = mReviewDatas.get(position);

        holder.mTvNickname.setText(reviewData.getUserName());
        holder.mTvReview.setText(reviewData.getAppComment());

        PointData point = reviewData.getAppElement();

        String design = "0";
        String contens = "0";
        String satisfaction = "0";
        String useful = "0";

        if (point != null) {
            design = String.valueOf(point.getDesign());
            contens = String.valueOf(point.getContents());
            satisfaction = String.valueOf(point.getSatisfaction());
            useful = String.valueOf(point.getUseful());
        }

        holder.mTvDesign.setText(design);
        holder.mTvContents.setText(contens);
        holder.mTvSatisfaction.setText(satisfaction);
        holder.mTvUseful.setText(useful);
    }

    @Override
    public int getItemCount() {
        return ListUtil.getListCount(mReviewDatas);
    }

    public ReviewData getItem(int position) {
        int itemCount = getItemCount();
        ReviewData item = null;

        if (itemCount > position && position < 0) {
            item = mReviewDatas.get(position);
        }

        return item;
    }

    public void addReviewDatas(List<ReviewData> reviewDatas) {
        mReviewDatas.addAll(reviewDatas);
    }

    public void addReviewData(ReviewData reviewData) {
        mReviewDatas.add(reviewData);
    }

    /**
     * 리뷰 Holder
     */
    static class ReviewHolder extends ViewHolder {

        @BindView(R.id.tv_nickname)
        TextView mTvNickname;
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

        public ReviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}