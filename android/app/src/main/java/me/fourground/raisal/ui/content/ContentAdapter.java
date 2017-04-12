package me.fourground.raisal.ui.content;

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
import me.fourground.raisal.common.Const;
import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.data.model.ContentData;
import me.fourground.raisal.data.model.PointData;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.util.DateUtil;
import me.fourground.raisal.util.ListUtil;
import me.fourground.raisal.util.Util;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 리뷰 Adapter
 */
public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_APP_INFO = 1;
    private static final int VIEW_TYPE_REVIEW = 2;

    /**
     * 리뷰 데이터들
     */
    private List<Object> mReviewDatas;

    /**
     * 리뷰 Adapter
     */
    @Inject
    public ContentAdapter() {
        this.mReviewDatas = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_APP_INFO) {
            viewHolder = new ContentViewHolder(
                    inflate.inflate(R.layout.item_content_app_info, parent, false));
        } else if (viewType == VIEW_TYPE_REVIEW) {
            viewHolder = new ReviewHolder(inflate.inflate(R.layout.item_review, parent, false));
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = getItem(position);
        int type = VIEW_TYPE_REVIEW;
        if (item != null && item instanceof ContentData) {
            type = VIEW_TYPE_APP_INFO;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Object data = mReviewDatas.get(position);

        if (data instanceof ContentData) {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            setAppInfoView(context, contentViewHolder, (ContentData) data);
        } else {
            ReviewHolder reviewHolder = (ReviewHolder) holder;
            setReviewView(context, reviewHolder, (ReviewData) data);
        }


    }

    private void setReviewView(Context context, ReviewHolder reviewHolder, ReviewData data) {
        reviewHolder.mTvNickname.setText(data.getUserName());
        reviewHolder.mTvReview.setText(data.getAppComment());

        PointData point = data.getAppElement();

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

        reviewHolder.mTvDesign.setText(design);
        reviewHolder.mTvContents.setText(contens);
        reviewHolder.mTvSatisfaction.setText(satisfaction);
        reviewHolder.mTvUseful.setText(useful);
    }

    private void setAppInfoView(Context context, ContentViewHolder contentViewHolder, ContentData data) {
        AppInfoData appInfo = data.getAppInfo();

        contentViewHolder.mTvName.setText(appInfo.getAppName());
        contentViewHolder.mTvStore.setText(Util.getStoreType(context, appInfo.getTargetOsCode()));

        boolean isEnd = Const.APPRAISAL_TYPE_FINISH.equals(appInfo.getAppStatus());
        if (isEnd) {
            contentViewHolder.mTvState.setText(context.getString(R.string.text_appraisal_end));
        } else {
            contentViewHolder.mTvState.setText(context.getString(R.string.text_appraisal_evaluating));
        }

        contentViewHolder.mTvState.setSelected(isEnd);

        contentViewHolder.mTvDate.setText(context.getString(R.string._text_date,
                DateUtil.convertDateFormat(appInfo.getStartDtm(), Const.DATE_FORMAT_SERVER, Const.DATE_FORMAT_VIEW),
                DateUtil.convertDateFormat(appInfo.getEndDtm(), Const.DATE_FORMAT_SERVER, Const.DATE_FORMAT_VIEW)
        ));

        if (appInfo.getNPartyUserCount() == 0) {
            contentViewHolder.mTvEmptyText.setVisibility(View.VISIBLE);
            contentViewHolder.mLlAverage.setVisibility(View.GONE);
        } else {
            contentViewHolder.mTvEmptyText.setVisibility(View.GONE);
            contentViewHolder.mLlAverage.setVisibility(View.VISIBLE);
        }

        contentViewHolder.mTvExplanation.setText(data.getAppDesc());

        contentViewHolder.mTvReviewCount.setText(context.getString(R.string._text_content_review_count, appInfo.getNPartyUserCount()));

        contentViewHolder.mTvAverage.setText(String.format("%.1f", appInfo.getAppraisalAvg()));
        contentViewHolder.mRbAverage.setRating(appInfo.getAppraisalAvg());

        PointData appElement = data.getAppElement();
        if (appElement != null) {
            contentViewHolder.mRbDesign.setRating(appElement.getDesign());
            contentViewHolder.mRbContents.setRating(appElement.getContents());
            contentViewHolder.mRbSatisfaction.setRating(appElement.getSatisfaction());
            contentViewHolder.mRbUseful.setRating(appElement.getUseful());
        }
    }

    @Override
    public int getItemCount() {
        return ListUtil.getListCount(mReviewDatas);
    }

    public Object getItem(int position) {
        int itemCount = getItemCount();
        Object item = null;

        if (itemCount > position && position >= 0) {
            item = mReviewDatas.get(position);
        }

        return item;
    }

    public void addReviewDatas(List<ReviewData> reviewDatas) {
        mReviewDatas.addAll(reviewDatas);
    }

    public void addReviewData(ReviewData reviewData) {
        mReviewDatas.add(1, reviewData);
    }

    public void addContentData(ContentData contentData) {
        mReviewDatas.add(0, contentData);
    }

    public ContentData getContentData() {
        return (ContentData) mReviewDatas.get(0);
    }

    /**
     * 리뷰 Holder
     */
    static class ReviewHolder extends RecyclerView.ViewHolder {

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

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_store)
        TextView mTvStore;
        @BindView(R.id.tv_state)
        TextView mTvState;
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.tv_explanation)
        TextView mTvExplanation;
        @BindView(R.id.tv_empty_text)
        TextView mTvEmptyText;
        @BindView(R.id.tv_review_count)
        TextView mTvReviewCount;
        @BindView(R.id.tv_average)
        TextView mTvAverage;
        @BindView(R.id.rb_average)
        RatingBar mRbAverage;
        @BindView(R.id.rb_design)
        RatingBar mRbDesign;
        @BindView(R.id.rb_useful)
        RatingBar mRbUseful;
        @BindView(R.id.rb_contents)
        RatingBar mRbContents;
        @BindView(R.id.rb_satisfaction)
        RatingBar mRbSatisfaction;
        @BindView(R.id.ll_average)
        LinearLayout mLlAverage;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}