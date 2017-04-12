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
import me.fourground.raisal.common.Const;
import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.data.model.MyReviewData;
import me.fourground.raisal.data.model.PointData;
import me.fourground.raisal.util.DateUtil;
import me.fourground.raisal.util.ListUtil;
import me.fourground.raisal.util.Util;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 리뷰 Adapter
 */
public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.MyReviewHolder> {


    public interface OnReviewItemClickListener {
        void onReviewItemClick(MyReviewData reviewData);
    }

    /**
     * 앱 평가 데이터들
     */
    private List<MyReviewData> mMyReviewDatas;
    /**
     * 앱 평가 아이템 클릭 리스너
     */
    private OnReviewItemClickListener mOnReviewItemClickListener;

    /**
     * 앱 평가 Adapter
     */
    @Inject
    public MyReviewAdapter() {
        this.mMyReviewDatas = new ArrayList<>();
    }


    @Override
    public MyReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        return new MyReviewHolder(inflate.inflate(R.layout.item_my_review, parent, false));

    }

    @Override
    public void onBindViewHolder(MyReviewHolder myReviewHolder, int position) {
        Context context = myReviewHolder.itemView.getContext();
        MyReviewData data = mMyReviewDatas.get(position);

        AppInfoData appInfo = data.getAppInfo();


        myReviewHolder.mTvDate.setText(context.getString(R.string._text_date,
                DateUtil.convertDateFormat(appInfo.getStartDtm(), Const.DATE_FORMAT_SERVER, Const.DATE_FORMAT_VIEW),
                DateUtil.convertDateFormat(appInfo.getEndDtm(), Const.DATE_FORMAT_SERVER, Const.DATE_FORMAT_VIEW)
        ));
        myReviewHolder.mTvName.setText(appInfo.getAppName());
        myReviewHolder.mTvStore.setText(Util.getStoreType(context, appInfo.getTargetOsCode()));

        myReviewHolder.mTvReview.setText(data.getAppComment());

        boolean isEvaluating = Const.APPRAISAL_TYPE_ACTIVIE.equals(appInfo.getAppStatus());

        String state = isEvaluating ?
                context.getString(R.string.text_appraisal_evaluating)
                : context.getString(R.string.text_appraisal_end);

        myReviewHolder.mTvState.setText(state);
        myReviewHolder.mTvState.setSelected(isEvaluating);

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

        myReviewHolder.mTvDesign.setText(design);
        myReviewHolder.mTvContents.setText(contens);
        myReviewHolder.mTvSatisfaction.setText(satisfaction);
        myReviewHolder.mTvUseful.setText(useful);

        myReviewHolder.itemView.setOnClickListener(
                view -> {
                    if (mOnReviewItemClickListener != null) {
                        mOnReviewItemClickListener.onReviewItemClick(data);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return ListUtil.getListCount(mMyReviewDatas);
    }

    public MyReviewData getItem(int position) {
        int itemCount = getItemCount();
        MyReviewData item = null;

        if (itemCount > position && position >= 0) {
            item = mMyReviewDatas.get(position);
        }

        return item;
    }

    public void addReviewData(MyReviewData reviewData) {
        mMyReviewDatas.add(0, reviewData);
    }

    public void addReviewDatas(List<MyReviewData> reviewDatas) {
        mMyReviewDatas.addAll(reviewDatas);
    }


    public void setOnReviewItemClickListener(OnReviewItemClickListener onReviewItemClickListener) {
        mOnReviewItemClickListener = onReviewItemClickListener;
    }

    public int getAppInfoPosition(String appId) {
        int itemCount = getItemCount();
        int position = -1;

        for (int i = 0; i < itemCount; i++) {
            MyReviewData reviewData = getItem(i);
            AppInfoData appInfoData = reviewData.getAppInfo();
            if (appId.equals(appInfoData.getAppId())) {
                position = i;
                break;
            }
        }

        return position;
    }

    static class MyReviewHolder extends RecyclerView.ViewHolder {
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

        MyReviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}