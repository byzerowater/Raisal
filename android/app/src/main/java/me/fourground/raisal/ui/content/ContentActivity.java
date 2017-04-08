package me.fourground.raisal.ui.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.raisal.R;
import me.fourground.raisal.common.Const;
import me.fourground.raisal.data.BusEvent;
import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.data.model.ContentData;
import me.fourground.raisal.data.model.PointData;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.dialog.LoadingDialog;
import me.fourground.raisal.ui.views.LinearRecyclerView;
import me.fourground.raisal.ui.write.review.WriteReviewActivity;
import me.fourground.raisal.util.DateUtil;
import me.fourground.raisal.util.Util;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class ContentActivity extends BaseActivity implements ContentMvpView {

    private static final String EXTRA_APP_ID = "extra_app_id";


    @Inject
    ContentPresenter mContentPresenter;
    @Inject
    LoadingDialog mLoadingDialog;
    @Inject
    ReviewAdapter mReviewAdapter;
    @Inject
    Bus mEventBus;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
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
    @BindView(R.id.rv_review)
    LinearRecyclerView mRvReview;
    @BindView(R.id.btn_write_review)
    Button mBtnWriteReview;
    @BindView(R.id.btn_back)
    Button mBtnBack;
    @BindView(R.id.tv_empty_text)
    TextView mTvEmptyText;

    private AppInfoData mAppInfoData;
    private String mDownUrl = null;


    /**
     * ContentActivity 가져오기
     *
     * @param context Context
     * @param appId   앱 아이디
     * @return ContentActivity Intent
     */
    public static Intent getStartIntent(Context context, String appId) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        mContentPresenter.attachView(this);
        mEventBus.register(this);

        mBtnBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(getString(R.string.text_title_content));

        mRvReview.setAdapter(mReviewAdapter);

        mContentPresenter.getContent(getIntent().getStringExtra(EXTRA_APP_ID));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContentPresenter.detachView();
        mEventBus.unregister(this);
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onContent(ContentData contentData) {
        AppInfoData appInfo = contentData.getAppInfo();

        mTvName.setText(appInfo.getAppName());
        mTvStore.setText(Util.getStoreType(ContentActivity.this, appInfo.getTargetOsCode()));

        boolean isEnd = Const.APPRAISAL_TYPE_FINISH.equals(appInfo.getAppStatus());
        if (isEnd) {
            mTvState.setText(getString(R.string.text_appraisal_end));
            mBtnWriteReview.setVisibility(View.GONE);
        } else {
            mTvState.setText(getString(R.string.text_appraisal_evaluating));
            mBtnWriteReview.setVisibility(View.VISIBLE);
        }

        mTvState.setSelected(isEnd);

        mTvDate.setText(getString(R.string._text_date,
                DateUtil.convertDateFormat(appInfo.getStartDtm(), Const.DATE_FORMAT_SERVER, Const.DATE_FORMAT_VIEW),
                DateUtil.convertDateFormat(appInfo.getEndDtm(), Const.DATE_FORMAT_SERVER, Const.DATE_FORMAT_VIEW)
        ));

        if (appInfo.getNPartyUserCount() == 0) {
            mTvEmptyText.setVisibility(View.VISIBLE);
            mLlAverage.setVisibility(View.GONE);
        } else {
            mTvEmptyText.setVisibility(View.GONE);
            mLlAverage.setVisibility(View.VISIBLE);
        }

        mTvExplanation.setText(contentData.getAppDesc());

        mTvReviewCount.setText(getString(R.string._text_content_review_count, appInfo.getNPartyUserCount()));

        mTvAverage.setText(String.valueOf(appInfo.getAppraisalAvg()));
        mRbAverage.setRating(appInfo.getAppraisalAvg());

        PointData appElement = contentData.getAppElement();
        mRbDesign.setRating(appElement.getDesign());
        mRbContents.setRating(appElement.getContents());
        mRbSatisfaction.setRating(appElement.getSatisfaction());
        mRbUseful.setRating(appElement.getUseful());

        mDownUrl = contentData.getAppDownloadUrl();
        mAppInfoData = appInfo;
    }

    @Override
    public void onReviewList(List<ReviewData> reviewDatas) {
        mReviewAdapter.addReviewDatas(reviewDatas);
        mReviewAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onRegisterCompleted(BusEvent.RegisterReviewCompleted event) {
        ReviewData reviewData = event.getReviewData();
        mReviewAdapter.addReviewData(reviewData);
        mReviewAdapter.notifyDataSetChanged();

        mTvEmptyText.setVisibility(View.GONE);
        mLlAverage.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.btn_back, R.id.btn_app_down, R.id.btn_write_review})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_app_down:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mDownUrl)));
                break;
            case R.id.btn_write_review:
                startActivity(WriteReviewActivity.getStartIntent(ContentActivity.this, mAppInfoData));
                break;
        }
    }
}
