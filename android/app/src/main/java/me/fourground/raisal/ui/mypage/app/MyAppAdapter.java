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
import me.fourground.raisal.common.Const;
import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.util.DateUtil;
import me.fourground.raisal.util.ListUtil;
import me.fourground.raisal.util.Util;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 리뷰 Adapter
 */
public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.MyAppHolder> {

    public interface OnAppItemClickListener {
        void onAppItemClick(AppInfoData appItem);
    }

    /**
     * 앱 평가 데이터들
     */
    private List<AppInfoData> mAppDatas;
    /**
     * 앱 평가 아이템 클릭 리스너
     */
    private MyAppAdapter.OnAppItemClickListener mOnAppItemClickListener;

    /**
     * 앱 평가 Adapter
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
    public void onBindViewHolder(MyAppHolder myAppHolder, int position) {
        Context context = myAppHolder.itemView.getContext();
        AppInfoData data = mAppDatas.get(position);

        myAppHolder.mRbAverage.setRating(data.getAppraisalAvg());
        myAppHolder.mTvAverage.setText(String.format("%.1f", data.getAppraisalAvg()));
        myAppHolder.mTvDate.setText(context.getString(R.string._text_date,
                DateUtil.convertDateFormat(data.getStartDtm(), Const.DATE_FORMAT_SERVER, Const.DATE_FORMAT_VIEW),
                DateUtil.convertDateFormat(data.getEndDtm(), Const.DATE_FORMAT_SERVER, Const.DATE_FORMAT_VIEW)
        ));
        myAppHolder.mTvName.setText(data.getAppName());
        myAppHolder.mTvStore.setText(Util.getStoreType(context, data.getTargetOsCode()));

        if (data.getNPartyUserCount() == 0) {
            myAppHolder.mTvEmptyText.setVisibility(View.VISIBLE);
            myAppHolder.mLlAverage.setVisibility(View.GONE);
        } else {
            myAppHolder.mTvEmptyText.setVisibility(View.GONE);
            myAppHolder.mLlAverage.setVisibility(View.VISIBLE);
            myAppHolder.mTvReviewCount.setText(context.getString(R.string._text_review_count, data.getNPartyUserCount()));
        }

        boolean isEvaluating = Const.APPRAISAL_TYPE_ACTIVIE.equals(data.getAppStatus());

        String state = isEvaluating ?
                context.getString(R.string.text_appraisal_evaluating)
                : context.getString(R.string.text_appraisal_end);

        myAppHolder.mTvState.setText(state);
        myAppHolder.mTvState.setSelected(isEvaluating);

        myAppHolder.itemView.setOnClickListener(
                view -> {
                    if (mOnAppItemClickListener != null) {
                        mOnAppItemClickListener.onAppItemClick(data);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return ListUtil.getListCount(mAppDatas);
    }

    public AppInfoData getItem(int position) {
        int itemCount = getItemCount();
        AppInfoData item = null;

        if (itemCount > position && position >= 0) {
            item = mAppDatas.get(position);
        }

        return item;
    }

    public void addAppData(AppInfoData appData) {
        mAppDatas.add(0, appData);
    }

    public void addAppDatas(List<AppInfoData> appDatas) {
        mAppDatas.addAll(appDatas);
    }


    public void setOnAppItemClickListener(MyAppAdapter.OnAppItemClickListener onAppItemClickListener) {
        mOnAppItemClickListener = onAppItemClickListener;
    }

    public int getAppInfoPosition(String appId) {
        int itemCount = getItemCount();
        int position = -1;

        for (int i = 0; i < itemCount; i++) {
            AppInfoData appData = getItem(i);
            if (appId.equals(appData.getAppId())) {
                position = i;
                break;
            }
        }

        return position;
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