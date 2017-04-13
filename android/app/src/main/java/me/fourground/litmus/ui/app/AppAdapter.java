package me.fourground.litmus.ui.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fourground.litmus.R;
import me.fourground.litmus.common.Const;
import me.fourground.litmus.data.model.AppInfoData;
import me.fourground.litmus.util.DateUtil;
import me.fourground.litmus.util.ListUtil;
import me.fourground.litmus.util.StringUtil;
import me.fourground.litmus.util.Util;
import timber.log.Timber;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 앱 평가 Adapter
 */
public class AppAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final int VIEW_TYPE_EVALUATING = 1;
    private static final int VIEW_TYPE_END = 2;

    public interface OnAppItemClickListener {
        void onAppItemClick(AppInfoData appItem);

        void onWriteItemClick(AppInfoData appItem);
    }

    /**
     * 앱 평가 데이터들
     */
    private List<AppInfoData> mAppDatas;
    /**
     * 앱 평가 아이템 클릭 리스너
     */
    private OnAppItemClickListener mOnAppItemClickListener;

    private String mUid;

    /**
     * 앱 평가 Adapter
     */
    @Inject
    public AppAdapter() {
        this.mAppDatas = new ArrayList<>();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            this.mUid = currentUser.getUid();
            Timber.i("uid %s", mUid);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        AppInfoData item = getItem(position);
        Timber.i(item.toString());
        int type = VIEW_TYPE_EVALUATING;
        if (item != null && Const.APPRAISAL_TYPE_FINISH.equals(item.getAppStatus())) {
            type = VIEW_TYPE_END;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        AppInfoData data = mAppDatas.get(position);

        if (Const.APPRAISAL_TYPE_FINISH.equals(data.getAppStatus())) {
            AppEndHolder endHolder = (AppEndHolder) holder;
            setFinishItemView(context, endHolder, data);
        } else {
            AppEvaluatingHolder evaluatingHolder = (AppEvaluatingHolder) holder;
            setEvaluatingView(context, evaluatingHolder, data);
        }

        holder.itemView.setOnClickListener(
                view -> {
                    if (mOnAppItemClickListener != null) {
                        mOnAppItemClickListener.onAppItemClick(data);
                    }
                });
    }

    private void setFinishItemView(Context context, AppEndHolder endHolder, AppInfoData data) {
        endHolder.mRbAverage.setRating(data.getAppraisalAvg());
        endHolder.mTvAverage.setText(String.valueOf(data.getAppraisalAvg()));
        endHolder.mTvDate.setText(context.getString(R.string._text_date,
                DateUtil.convertDateFormat(data.getStartDtm(), Const.DATE_FORMAT_SERVER, Const.DATE_FORMAT_VIEW),
                DateUtil.convertDateFormat(data.getEndDtm(), Const.DATE_FORMAT_SERVER, Const.DATE_FORMAT_VIEW)
        ));
        endHolder.mTvName.setText(data.getAppName());
        endHolder.mTvStore.setText(Util.getStoreType(context, data.getTargetOsCode()));

        if (data.getNPartyUserCount() == 0) {
            endHolder.mTvEmptyText.setVisibility(View.VISIBLE);
            endHolder.mLlAverage.setVisibility(View.GONE);
        } else {
            endHolder.mTvEmptyText.setVisibility(View.GONE);
            endHolder.mLlAverage.setVisibility(View.VISIBLE);
            endHolder.mTvReviewCount.setText(context.getString(R.string._text_review_count, data.getNPartyUserCount()));
        }
        endHolder.mTvState.setText(context.getString(R.string.text_appraisal_end));
        endHolder.mTvState.setSelected(false);
    }

    private void setEvaluatingView(Context context, AppEvaluatingHolder evaluatingHolder, AppInfoData data) {
        evaluatingHolder.mTvName.setText(data.getAppName());
        evaluatingHolder.mTvStore.setText(Util.getStoreType(context, data.getTargetOsCode()));

        if (data.getNPartyUserCount() == 0) {
            evaluatingHolder.mTvEmptyText.setVisibility(View.VISIBLE);
            evaluatingHolder.mTvReviewCount.setVisibility(View.GONE);
        } else {
            evaluatingHolder.mTvEmptyText.setVisibility(View.GONE);
            evaluatingHolder.mTvReviewCount.setVisibility(View.VISIBLE);
            evaluatingHolder.mTvReviewCount.setText(context.getString(R.string._text_review_count, data.getNPartyUserCount()));
        }

        evaluatingHolder.mTvState.setText(context.getString(R.string.text_appraisal_evaluating));
        evaluatingHolder.mTvState.setSelected(true);

        if (!StringUtil.isEmpty(mUid) && mUid.equals(data.getRegId())) {
            evaluatingHolder.mBtnJoinReview.setVisibility(View.GONE);
        } else {
            evaluatingHolder.mBtnJoinReview.setVisibility(View.VISIBLE);
        }

        evaluatingHolder.mBtnJoinReview.setOnClickListener(v -> {
            if (mOnAppItemClickListener != null) {
                mOnAppItemClickListener.onWriteItemClick(data);
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

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getUid() {
        return mUid;
    }

    public void addAppDatas(List<AppInfoData> appDatas) {
        mAppDatas.addAll(appDatas);
    }

    public void addAppData(AppInfoData appData) {
        mAppDatas.add(0, appData);
    }

    public void setOnAppItemClickListener(OnAppItemClickListener onAppItemClickListener) {
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
        @BindView(R.id.tv_empty_text)
        TextView mTvEmptyText;

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
        @BindView(R.id.tv_review_count)
        TextView mTvReviewCount;
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.tv_empty_text)
        TextView mTvEmptyText;
        @BindView(R.id.ll_average)
        LinearLayout mLlAverage;

        public AppEndHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}